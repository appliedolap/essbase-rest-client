package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.Grid;
import com.appliedolap.essbase.client.model.GridOperation;
import com.appliedolap.essbase.client.model.GridRange;
import com.appliedolap.essbase.client.model.Slice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EssCubeViewImpl implements EssCubeView {

    private final ApiContext api;

    private final String applicationName;

    private final String databaseName;

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
        GridOperation operation = new GridOperation().grid(grid).action(GridOperation.ActionEnum.ZOOMIN);
        operation.setRanges(Arrays.asList(Arrays.asList(row, col, row, col)));
        try {
            execute(operation);
        } catch (EssApiException e) {
            execute(GridOperation.ActionEnum.ZOOMIN, row, col);
        }
    }

    @Override
    public void zoomOut(int row, int col) {
        executeRange(GridOperation.ActionEnum.ZOOMOUT, row, col);
    }

    @Override
    public void keepOnly(int row, int col) {
        executeRange(GridOperation.ActionEnum.KEEPONLY, row, col);
    }

    @Override
    public void removeOnly(int row, int col) {
        execute(GridOperation.ActionEnum.REMOVEONLY, row, col);
    }

    @Override
    public void refresh() {
        execute(new GridOperation().grid(grid).action(GridOperation.ActionEnum.REFRESH));
    }

    @Override
    public void pivot(int fromRow, int fromCol, int toRow, int toCol) {
        GridOperation operation = new GridOperation().grid(grid).action(GridOperation.ActionEnum.PIVOT);
        operation.setCoordinates(Arrays.asList(fromRow, fromCol, toRow, toCol));
        execute(operation);
    }

    @Override
    public void pivotToPov(int row, int col) {
        execute(GridOperation.ActionEnum.PIVOT_TO_POV, row, col);
    }

    private void execute(GridOperation.ActionEnum action, int row, int col) {
        GridOperation operation = new GridOperation().grid(grid).action(action);
        operation.setCoordinates(Arrays.asList(row, col));
        execute(operation);
    }

    // zoomOut/keepOnly are silently ignored by the server when sent via "coordinates" (200 OK, grid
    // unchanged) - that field is simply wired to other actions, not a matter of how we phrase the
    // request, so "ranges" is the only way to invoke these two at all. Beyond that one substitution,
    // this describes the click as literally as the wire format allows - a single-cell range - and
    // does not try to compute, reverse, or otherwise infer "the range that will produce some intended
    // result". Whatever grid the server returns for that description is authoritative; this library
    // doesn't model or second-guess what a given action *should* do to the grid.
    private void executeRange(GridOperation.ActionEnum action, int row, int col) {
        GridOperation operation = new GridOperation().grid(grid).action(action);
        operation.setRanges(Arrays.asList(Arrays.asList(row, col, row, col)));
        execute(operation);
    }

    private void execute(GridOperation operation) {
        try {
            this.grid = api.getGridApi().gridExecute(applicationName, databaseName, operation);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

}
