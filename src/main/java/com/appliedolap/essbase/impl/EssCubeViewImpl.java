package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.ColumnSuppression;
import com.appliedolap.essbase.client.model.Grid;
import com.appliedolap.essbase.client.model.GridDimension;
import com.appliedolap.essbase.client.model.GridOperation;
import com.appliedolap.essbase.client.model.GridRange;
import com.appliedolap.essbase.client.model.Preferences;
import com.appliedolap.essbase.client.model.RowSuppression;
import com.appliedolap.essbase.client.model.Slice;
import com.appliedolap.essbase.client.model.ZoomIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EssCubeViewImpl implements EssCubeView {

    private final ApiContext api;

    private final String applicationName;

    private final String databaseName;

    /** The (undocumented) cell-type codes: a data position, a label, and a blank filler cell. */
    private static final String DATA_CELL = "2";

    private static final String MEMBER_CELL = "0";

    private static final String BLANK_CELL = "7";

    private Grid grid;

    public EssCubeViewImpl(ApiContext api, String applicationName, String databaseName, Grid grid) {
        this.api = api;
        this.applicationName = applicationName;
        this.databaseName = databaseName;
        this.grid = grid;
    }

    @Override
    public int getRows() {
        return grid.getSlice().getRows();
    }

    @Override
    public int getColumns() {
        return grid.getSlice().getColumns();
    }

    @Override
    public String getCell(int row, int col) {
        CellLocation location = locate(row, col);
        if (location == null) return null;
        // "texts" is the formatted-for-display override (null unless a number format applies);
        // "values" always carries the raw member name or data value.
        String text = location.range().getTexts().get(location.offset());
        return text != null ? text : location.range().getValues().get(location.offset());
    }

    @Override
    public CellType getCellType(int row, int col) {
        CellLocation location = locate(row, col);
        if (location == null) return CellType.MEMBER;
        // type "2" is the data-cell position; despite superficially looking like a bitmask, type "7"
        // (blank filler cells) is NOT "2 | something" in any meaningful sense - it just also happens
        // to satisfy a bit-2 check, which is why this compares by equality rather than by bitwise AND.
        return "2".equals(location.range().getTypes().get(location.offset())) ? CellType.DATA : CellType.MEMBER;
    }

    /**
     * Finds a cell in the response.
     *
     * <p>The {@code GridRange} here is unrelated to the ranges a request carries, despite the name: on
     * the way back a range is a flat run of cell values with a start and an end index, and on the way
     * out it is a rectangle as start plus counts. Nothing converts between them and nothing should.
     */
    private CellLocation locate(int row, int col) {
        Slice slice = grid.getSlice();
        int flatIndex = row * slice.getColumns() + col;
        int consumed = 0;
        for (GridRange range : slice.getData().getRanges()) {
            int rangeSize = range.getEnd() - range.getStart() + 1;
            if (flatIndex < consumed + rangeSize) {
                return new CellLocation(range, flatIndex - consumed, flatIndex);
            }
            consumed += rangeSize;
        }
        return null;
    }

    private record CellLocation(GridRange range, int offset, int flatIndex) {
    }

    @Override
    public void setMembers(List<MemberPlacement> placements) {
        List<Integer> dirty = new ArrayList<>();
        for (MemberPlacement placement : placements) {
            CellLocation location = locate(placement.row(), placement.col());
            if (location == null) {
                throw new IllegalArgumentException(
                        "No such grid position: (" + placement.row() + ", " + placement.col() + ")");
            }
            location.range().getValues().set(location.offset(), placement.memberName());
            dirty.add(location.flatIndex());
        }
        grid.getSlice().setDirtyCells(dirty);
        grid.getSlice().setDirtyTexts(dirty);
        execute(new GridOperation().grid(grid).action(GridOperation.ActionEnum.SUBMIT));
    }

    @Override
    public void zoomIn(int row, int col) {
        // "coordinates" silently mistargets a *different* dimension - not an error, just wrong - when
        // the clicked cell belongs to a dimension already genuinely on an axis, as opposed to a POV
        // placeholder dimension shown as a header but not yet on any axis (confirmed live: zooming in
        // on "Year", already on the row axis, expanded "Market" - a POV dimension - instead).
        // "ranges" addresses the on-axis case correctly, but the server rejects it outright (400) for
        // a POV placeholder cell. Rather than inspect the cell or the grid's dimension metadata to
        // predict which field applies, just try "ranges" first - the more literal, direct
        // description of a single clicked cell - and fall back to "coordinates" only if the server
        // itself says that shape doesn't apply here.
        zoomIn(java.util.List.of(Range.cell(row, col)));
    }

    @Override
    public void zoomIn(java.util.List<Range> ranges) {
        try {
            executeRanges(GridOperation.ActionEnum.ZOOMIN, ranges);
        } catch (EssApiException e) {
            // The fallback only exists for a POV placeholder cell, which is a single cell by nature -
            // "coordinates" holds one pair and cannot describe a selection. A rejected multi-range zoom
            // is the server's answer, not something to retry in a shape that cannot carry the question.
            if (ranges.size() != 1 || ranges.get(0).size() != 1) {
                throw e;
            }
            execute(GridOperation.ActionEnum.ZOOMIN,
                    ranges.get(0).getStartRow(), ranges.get(0).getStartColumn());
        }
    }

    @Override
    public void zoomOut(int row, int col) {
        executeRange(GridOperation.ActionEnum.ZOOMOUT, row, col);
    }

    @Override
    public void zoomOut(java.util.List<Range> ranges) {
        executeRanges(GridOperation.ActionEnum.ZOOMOUT, ranges);
    }

    @Override
    public void keepOnly(int row, int col) {
        executeRange(GridOperation.ActionEnum.KEEPONLY, row, col);
    }

    @Override
    public void keepOnly(int fromRow, int fromCol, int toRow, int toCol) {
        executeRange(GridOperation.ActionEnum.KEEPONLY, fromRow, fromCol, toRow, toCol);
    }

    @Override
    public void keepOnly(java.util.List<Range> ranges) {
        executeRanges(GridOperation.ActionEnum.KEEPONLY, ranges);
    }

    @Override
    public void removeOnly(java.util.List<Range> ranges) {
        executeRanges(GridOperation.ActionEnum.REMOVEONLY, ranges);
    }

    @Override
    public void removeOnly(int fromRow, int fromCol, int toRow, int toCol) {
        executeRange(GridOperation.ActionEnum.REMOVEONLY, fromRow, fromCol, toRow, toCol);
    }

    // Ranges, like keepOnly. This used to go through "coordinates" and never worked - every attempt
    // came back "This operation would generate a nonsensical report", which read as the action being
    // unusable. It was the range format: once a single cell is [row, col, 1, 1] rather than
    // [row, col, row, col], removing one member is ordinary and works.
    @Override
    public void removeOnly(int row, int col) {
        executeRange(GridOperation.ActionEnum.REMOVEONLY, row, col);
    }

    /**
     * Sends the sheet as given and lets the engine read it.
     *
     * <p>Deliberately no validation beyond the length: what layouts Essbase accepts is the engine's
     * business and richer than anything worth reimplementing here, and its refusals say more than a
     * guess would.
     */
    @Override
    public void setLayout(java.util.List<String> cells) {
        int expected = getRows() * getColumns();
        if (cells == null || cells.size() != expected) {
            throw new IllegalArgumentException("A layout needs exactly " + expected + " cells for this "
                    + getRows() + " by " + getColumns() + " grid, but got "
                    + (cells == null ? 0 : cells.size()));
        }
        grid.getSlice().getData().getRanges().get(0).setValues(new java.util.ArrayList<>(cells));
        execute(new GridOperation().grid(grid).action(GridOperation.ActionEnum.REFRESH));
    }

    // Every cell the server sends back carries five parallel arrays, and a sheet of a different shape
    // has to restate all of them - not just the labels. Leaving a stale "types" array in place is how
    // the single-argument setLayout limits itself to rearranging what is already on the axes: a label
    // written into a position the server still believes is a data cell is not read as a heading.
    @Override
    public void setLayout(String[][] sheet, int headerRows, int leftColumns) {
        if (sheet == null || sheet.length == 0 || sheet[0].length == 0) {
            throw new IllegalArgumentException("A layout needs at least one cell");
        }
        int rows = sheet.length;
        int columns = sheet[0].length;
        for (String[] row : sheet) {
            if (row.length != columns) {
                throw new IllegalArgumentException("A layout has to be rectangular, but its rows are "
                        + columns + " and " + row.length + " cells wide");
            }
        }
        if (headerRows < 1 || headerRows >= rows) {
            throw new IllegalArgumentException("headerRows has to leave at least one data row, but was "
                    + headerRows + " of " + rows + " rows");
        }
        if (leftColumns < 1 || leftColumns >= columns) {
            throw new IllegalArgumentException("leftColumns has to leave at least one data column, but was "
                    + leftColumns + " of " + columns + " columns");
        }

        // Which columns and rows carry data, rather than a rectangle of them. A column carries data if
        // the last header row - the innermost top axis, which names one member per data column - names
        // something there, and a row carries data if the left axis names something on it. That is the
        // same answer as a rectangle for an ordinary grid, and the right one for a grid with a blank
        // row or column ruled through it, which is a thing people really do put in a template.
        boolean[] dataColumn = new boolean[columns];
        int dataColumns = 0;
        for (int column = leftColumns; column < columns; column++) {
            dataColumn[column] = !sheet[headerRows - 1][column].isEmpty();
            if (dataColumn[column]) {
                dataColumns++;
            }
        }
        if (dataColumns == 0) {
            throw new IllegalArgumentException("The last header row (row " + (headerRows - 1)
                    + ") names no columns, so the layout has no data region");
        }

        boolean[] dataRow = new boolean[rows];
        for (int row = headerRows; row < rows; row++) {
            for (int column = 0; column < leftColumns; column++) {
                dataRow[row] |= !sheet[row][column].isEmpty();
            }
        }

        List<String> values = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> statuses = new ArrayList<>();
        List<String> enumIds = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                boolean data = dataRow[row] && dataColumn[column];
                values.add(data ? "" : sheet[row][column]);
                types.add(data ? DATA_CELL : sheet[row][column].isEmpty() ? BLANK_CELL : MEMBER_CELL);
                statuses.add("0");
                enumIds.add("");
                texts.add(null);
            }
        }

        Slice slice = grid.getSlice();
        slice.setRows(rows);
        slice.setColumns(columns);
        GridRange range = slice.getData().getRanges().get(0);
        range.setStart(0);
        range.setEnd(values.size() - 1);
        range.setValues(values);
        range.setTypes(types);
        range.setStatuses(statuses);
        range.setEnumIds(enumIds);
        range.setTexts(texts);
        // A sheet of a different shape leaves the old ranges describing a grid that no longer exists.
        while (slice.getData().getRanges().size() > 1) {
            slice.getData().getRanges().remove(1);
        }

        execute(new GridOperation().grid(grid).action(GridOperation.ActionEnum.REFRESH));
    }

    @Override
    public String getAliasTable() {
        return grid.getAlias();
    }

    @Override
    public void setAliasTable(String aliasTable) {
        this.aliasTable = aliasTable;
    }

    /**
     * The alias table asked for, which is not necessarily the one in use.
     *
     * <p>Held rather than read back off the grid because the grid reports what the server labelled the
     * last response from: before the first action after a change there is nothing to read, and if the
     * server does not recognise the name there never will be.
     */
    private String aliasTable;

    @Override
    public List<DimensionPlacement> getPlacements() {
        List<DimensionPlacement> placements = new ArrayList<>();
        for (GridDimension dimension : grid.getDimensions()) {
            // A dimension on an axis has an empty "pov" and exactly one of row/column set to a real
            // index; one in the POV names itself there and has -1 for both.
            String pov = dimension.getPov();
            DimensionPlacement.Region region;
            int index;
            if (pov != null && !pov.isEmpty()) {
                region = DimensionPlacement.Region.POV;
                index = -1;
            } else if (dimension.getColumn() != null && dimension.getColumn() >= 0) {
                region = DimensionPlacement.Region.LEFT;
                index = dimension.getColumn();
            } else {
                region = DimensionPlacement.Region.TOP;
                index = dimension.getRow() == null ? -1 : dimension.getRow();
            }
            placements.add(new DimensionPlacement(dimension.getName(), region, index));
        }
        return placements;
    }

    @Override
    public void refresh() {
        execute(new GridOperation().grid(grid).action(GridOperation.ActionEnum.REFRESH));
    }

    // "coordinates", not "ranges", and each one is a flat cell index rather than a row, a column or a
    // pair - see the interface. Anything beyond the second is ignored (verified: [3, 0, 9, 9] answers
    // identically to [3, 0]).
    @Override
    public void pivot(int fromCell, int toCell) {
        GridOperation operation = new GridOperation().grid(grid).action(GridOperation.ActionEnum.PIVOT);
        operation.setCoordinates(Arrays.asList(fromCell, toCell));
        execute(operation);
    }

    // One coordinate is a legitimate request and means the front of the row axis - the server supplies
    // the destination rather than rejecting the call, and [3] answers identically to [3, 0].
    @Override
    public void pivot(int fromCell) {
        GridOperation operation = new GridOperation().grid(grid).action(GridOperation.ActionEnum.PIVOT);
        operation.setCoordinates(Arrays.asList(fromCell));
        execute(operation);
    }

    @Override
    public void pivotToPov(int fromCell, int toCell) {
        GridOperation operation = new GridOperation().grid(grid)
                .action(GridOperation.ActionEnum.PIVOT_TO_POV);
        operation.setCoordinates(Arrays.asList(fromCell, toCell));
        execute(operation);
    }

    private void execute(GridOperation.ActionEnum action, int row, int col) {
        GridOperation operation = new GridOperation().grid(grid).action(action);
        operation.setCoordinates(Arrays.asList(row, col));
        execute(operation);
    }

    // zoomOut/keepOnly are silently ignored by the server when sent via "coordinates" (200 OK, grid
    // unchanged) - that field is simply wired to other actions, not a matter of how we phrase the
    // request, so "ranges" is the only way to invoke these two at all.
    //
    // A range is [startRow, startColumn, rowCount, columnCount], not two corners. Established live
    // rather than from the documentation, which says nothing: on a grid of Qtr1..Qtr4, Year, a keepOnly
    // at Qtr2 (row 3) sent as [3, 0, 3, 0] keeps Qtr2, Qtr3 and Qtr4 - three rows starting at row 3 -
    // while [3, 0, 1, 1] keeps Qtr2 alone. Read as two corners the first is a single cell, so the
    // reading is not in doubt.
    //
    // This was a real defect: sending [row, col, row, col] for a single cell happens to be correct only
    // at cell (1, 1), and everywhere else silently keeps or collapses a block whose size is whatever the
    // coordinates happened to be. It did not fail, it did the wrong thing quietly, which is why the
    // single-cell case had passed its live test - the test looked at (1, 1).
    private void executeRange(GridOperation.ActionEnum action, int row, int col) {
        executeRange(action, row, col, row, col);
    }

    /** A rectangle given as two corners, in either order, sent as the start-plus-counts the server wants. */
    private void executeRange(GridOperation.ActionEnum action, int fromRow, int fromCol,
            int toRow, int toCol) {
        int startRow = Math.min(fromRow, toRow);
        int startCol = Math.min(fromCol, toCol);
        int rows = Math.abs(toRow - fromRow) + 1;
        int columns = Math.abs(toCol - fromCol) + 1;
        GridOperation operation = new GridOperation().grid(grid).action(action);
        operation.setRanges(Arrays.asList(Arrays.asList(startRow, startCol, rows, columns)));
        execute(operation);
    }

    /**
     * Several rectangles in one request, which the server honours as one action over all of them.
     *
     * <p>Not a loop of single-range calls: each one re-executes the grid, so keeping the first and third
     * of four quarters would drop the third before it was asked for.
     */
    private void executeRanges(GridOperation.ActionEnum action, java.util.List<Range> ranges) {
        if (ranges == null || ranges.isEmpty()) {
            throw new IllegalArgumentException("A grid operation needs at least one range");
        }
        java.util.List<java.util.List<Integer>> wire = new java.util.ArrayList<>(ranges.size());
        for (Range range : ranges) {
            wire.add(Arrays.asList(range.getStartRow(), range.getStartColumn(),
                    range.getRowCount(), range.getColumnCount()));
        }
        GridOperation operation = new GridOperation().grid(grid).action(action);
        operation.setRanges(wire);
        execute(operation);
    }

    private void execute(GridOperation operation) {
        // On the operation, not on the grid inside it. Both carry an "alias" field and only this one is
        // read: setting the grid's alone leaves the response labelled from Default, established live
        // against a cube with six alias tables. The grid's is what the server fills in to say which
        // table it used, which is why getAliasTable() reads it from there.
        //
        // Sent on every action because the API takes it per request rather than storing it, which is
        // what lets two grids on one connection read different alias tables - unlike the rest of the
        // grid preferences, which Essbase keeps per session.
        operation.setAlias(aliasTable);
        try {
            this.grid = api.getGridApi().gridExecute(applicationName, databaseName, null, operation);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public GridPreferences getPreferences() {
        Preferences wire;
        try {
            wire = api.getGridPreferencesApi().gridPreferencesGet();
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
        RowSuppression rowSuppression = wire.getRowSupression();
        return new GridPreferences(
                toIndentation(wire.getIndentation()),
                rowSuppression != null && Boolean.TRUE.equals(rowSuppression.getMissing()),
                rowSuppression != null && Boolean.TRUE.equals(rowSuppression.getZero()),
                rowSuppression != null && Boolean.TRUE.equals(rowSuppression.getUnderScore()),
                Boolean.TRUE.equals(wire.getRepeatMemberLabels()),
                toZoomInPreference(wire.getZoomIn()),
                Boolean.TRUE.equals(wire.getIncludeSelection()),
                Boolean.TRUE.equals(wire.getWithinSelectedGroup()),
                Boolean.TRUE.equals(wire.getRemoveUnSelectedGroup()),
                Boolean.TRUE.equals(wire.getIncludeDescriptionLabel()));
    }

    @Override
    public void setPreferences(GridPreferences preferences) {
        Preferences wire;
        try {
            // The set endpoint replaces the whole preferences resource, so start from the current
            // values rather than a blank one - fields this type doesn't model (missingText,
            // formulaRetention, maxRows, ...) would otherwise get silently reset to defaults.
            wire = api.getGridPreferencesApi().gridPreferencesGet();
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
        wire.setIndentation(fromIndentation(preferences.indentation()));
        wire.setRowSupression(new RowSuppression()
                .missing(preferences.suppressMissingRows())
                .zero(preferences.suppressZeroRows())
                .underScore(preferences.suppressUnderscoreRows()));
        wire.setColumnSupression(new ColumnSuppression()
                .missing(preferences.suppressMissingRows())
                .zero(preferences.suppressZeroRows())
                .underScore(preferences.suppressUnderscoreRows()));
        wire.setRepeatMemberLabels(preferences.repeatMemberLabels());
        wire.setIncludeDescriptionLabel(preferences.useBothNamesAndAliases());
        wire.setZoomIn(fromZoomInPreference(preferences.zoomInPreference()));
        wire.setIncludeSelection(preferences.includeSelection());
        wire.setWithinSelectedGroup(preferences.withinSelectedGroup());
        wire.setRemoveUnSelectedGroup(preferences.removeUnselectedGroup());
        try {
            api.getGridPreferencesApi().gridPreferencesSet(wire);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    private static Indentation toIndentation(Preferences.IndentationEnum wire) {
        if (wire == null) {
            return Indentation.SUBITEMS;
        }
        return switch (wire) {
            case NONE -> Indentation.NONE;
            case TOTALS -> Indentation.TOTALS;
            default -> Indentation.SUBITEMS;
        };
    }

    private static Preferences.IndentationEnum fromIndentation(Indentation indentation) {
        return switch (indentation) {
            case NONE -> Preferences.IndentationEnum.NONE;
            case TOTALS -> Preferences.IndentationEnum.TOTALS;
            case SUBITEMS -> Preferences.IndentationEnum.SUBITEMS;
        };
    }

    // Only "mode" distinguishes NEXT_LEVEL/ALL_LEVELS/BOTTOM_LEVEL; "ancestor" (top/bottom) has no
    // confirmed effect and is always sent as TOP. See EssCubeView.ZoomInPreference.
    private static ZoomInPreference toZoomInPreference(ZoomIn wire) {
        if (wire == null || wire.getMode() == null) {
            return ZoomInPreference.NEXT_LEVEL;
        }
        return switch (wire.getMode()) {
            case DESCENDENTS -> ZoomInPreference.ALL_LEVELS;
            case BASE -> ZoomInPreference.BOTTOM_LEVEL;
            default -> ZoomInPreference.NEXT_LEVEL;
        };
    }

    private static ZoomIn fromZoomInPreference(ZoomInPreference preference) {
        ZoomIn.ModeEnum mode = switch (preference) {
            case NEXT_LEVEL -> ZoomIn.ModeEnum.CHILDREN;
            case ALL_LEVELS -> ZoomIn.ModeEnum.DESCENDENTS;
            case BOTTOM_LEVEL -> ZoomIn.ModeEnum.BASE;
        };
        return new ZoomIn().ancestor(ZoomIn.AncestorEnum.TOP).mode(mode);
    }

}
