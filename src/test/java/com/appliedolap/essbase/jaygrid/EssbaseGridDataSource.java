package com.appliedolap.essbase.jaygrid;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.EssMember;
import com.appliedolap.jaygrid.GridDataCell;
import com.appliedolap.jaygrid.GridDataSource;
import com.appliedolap.jaygrid.GridDimension;
import com.appliedolap.jaygrid.GridMember;
import com.appliedolap.jaygrid.exceptions.GridOperationException;
import com.appliedolap.jaygrid.exceptions.InvalidMemberNameException;
import com.appliedolap.jaygrid.impl.DataRowImpl;
import com.appliedolap.jaygrid.impl.DataRowsImpl;
import com.appliedolap.jaygrid.impl.DoubleGridCell;
import com.appliedolap.jaygrid.impl.EmptyGridCell;
import com.appliedolap.jaygrid.impl.RowKeyImpl;
import com.appliedolap.jaygrid.intermediate.IntermediateGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Jaygrid, reading a real Essbase cube.
 *
 * <p>Jaygrid works out an ad hoc operation itself and then asks its data source to retrieve the grid it
 * arrived at. Point that at Essbase and the two can be compared in one process, on the same cube, with
 * the same data - rather than through a corpus of fixtures copied between two projects by hand. It also
 * makes the suppression options testable for the first time, because those decide which rows survive by
 * their data, and until now there was no data in the comparison at all.
 *
 * <p>The retrieve works the way any client rearranging a grid has to: render what Jaygrid asked for as a
 * sheet of labels, hand it over with {@link EssCubeView#setLayout}, and read the answer back. There is no
 * "fetch these intersections" call to use instead - the grid <em>is</em> the request.
 *
 * <p>Lives in test scope, behind a profile, so that neither library depends on the other to build. See
 * the {@code jaygrid-differential} profile.
 */
public class EssbaseGridDataSource implements GridDataSource {

    private static final Logger logger = LoggerFactory.getLogger(EssbaseGridDataSource.class);

    private final EssCube cube;

    private final EssCubeView view;

    private final List<GridDimension> dimensions = new ArrayList<>();

    /** Every member by name and by alias. Filled on first use; the outline has no lookup of its own. */
    private final Map<String, GridMember> members = new HashMap<>();

    private boolean outlineRead;

    public EssbaseGridDataSource(EssCube cube) {
        this.cube = cube;
        this.view = cube.openCubeView();
    }

    /** The view the retrieves go through, for a test that wants to set preferences on it. */
    public EssCubeView getView() {
        return view;
    }

    @Override
    public Collection<GridDimension> getDimensions() {
        readOutline();
        return dimensions;
    }

    @Override
    public GridMember getMember(String memberName) {
        readOutline();
        GridMember member = members.get(memberName);
        if (member == null) {
            throw new InvalidMemberNameException(memberName);
        }
        return member;
    }

    @Override
    public Collection<String> getAliasTables() {
        return List.of(view.getAliasTable());
    }

    /**
     * Walks the whole outline once and remembers it.
     *
     * <p>Not lazy per member, because there is nothing to be lazy with: {@code EssOutline.getMember} is
     * an unfinished stub that returns void, so the only way to find a member by name is to have walked
     * to it. One walk of a few hundred members is a second or two, and every retrieve after it is free.
     */
    private synchronized void readOutline() {
        if (outlineRead) {
            return;
        }
        long started = System.currentTimeMillis();
        int number = 0;
        for (EssMember dimension : cube.getOutline().getDimensions()) {
            EssbaseGridDimension wrapped = new EssbaseGridDimension(dimension, number++);
            dimensions.add(wrapped);
            remember(dimension, wrapped, null);
        }
        outlineRead = true;
        logger.info("Read {} dimensions and {} member names from {}.{} in {}ms", dimensions.size(),
                members.size(), cube.getApplication().getName(), cube.getName(),
                System.currentTimeMillis() - started);
    }

    private void remember(EssMember member, GridDimension dimension, GridMember parent) {
        EssbaseGridMember wrapped = new EssbaseGridMember(member, dimension, parent);
        // First one wins: a shared member appears again under another parent, and the prototype is the
        // one a name should resolve to.
        members.putIfAbsent(member.getName(), wrapped);
        String alias = wrapped.getAlias(null);
        if (alias != null && !alias.isEmpty()) {
            members.putIfAbsent(alias, wrapped);
        }
        for (EssMember child : member.getChildren()) {
            remember(child, dimension, wrapped);
        }
    }

    /**
     * Retrieves the grid Jaygrid has arrived at.
     *
     * <p>The rows come back from what the server answered rather than from what was asked for, because
     * those differ whenever suppression is on: a suppressed row is simply not in the answer, and pairing
     * the answer up with the request by position would put every value in the wrong row.
     */
    @Override
    public DataRows retrieve(IntermediateGrid intermediateGrid, RetrieveOptions retrieveOptions)
            throws GridOperationException {
        readOutline();
        applySuppression(retrieveOptions.isSuppressMissingRows());

        String[][] sheet = IntermediateGridSheet.of(intermediateGrid);
        int headerRows = intermediateGrid.povHeight() + intermediateGrid.getTop().getRows();
        int leftColumns = intermediateGrid.getLeft().getColumns();
        try {
            view.setLayout(sheet, headerRows, leftColumns);
        } catch (RuntimeException failure) {
            throw new GridOperationException("Essbase would not read the grid: " + failure.getMessage(),
                    intermediateGrid);
        }

        return read(intermediateGrid);
    }

    /** Reads the data region out of whatever the server answered with. */
    private DataRows read(IntermediateGrid asked) {
        int leftColumns = 0;
        int topRows = 0;
        boolean pov = false;
        for (EssCubeView.DimensionPlacement placement : view.getPlacements()) {
            switch (placement.region()) {
                case LEFT -> leftColumns++;
                case TOP -> topRows++;
                case POV -> pov = true;
            }
        }
        int headerRows = topRows + (pov ? 1 : 0);

        int povRows = pov ? 1 : 0;
        List<Integer> dataColumns = new ArrayList<>();
        List<RowKey> columnKeys = new ArrayList<>();
        for (int column = leftColumns; column < view.getColumns(); column++) {
            if (view.getCellType(headerRows, column) != EssCubeView.CellType.DATA) {
                continue;
            }
            dataColumns.add(column);
            // One key per data column, naming the top-axis members above it. Without these the values
            // are retrieved and then thrown away: the view places each one by looking its column's key
            // up in this list, and an empty list means every lookup misses and every cell renders as
            // #Missing.
            List<GridMember> tuple = new ArrayList<>();
            for (int row = povRows; row < headerRows; row++) {
                String label = view.getCell(row, column);
                tuple.add(label == null || label.trim().isEmpty() ? null : resolve(label.trim()));
            }
            columnKeys.add(new RowKeyImpl(tuple));
        }

        List<DataRow> rows = new ArrayList<>();
        for (int row = headerRows; row < view.getRows(); row++) {
            List<GridMember> tuple = new ArrayList<>();
            for (int column = 0; column < leftColumns; column++) {
                String label = view.getCell(row, column);
                tuple.add(label == null || label.trim().isEmpty() ? null : resolve(label.trim()));
            }
            if (tuple.stream().allMatch(member -> member == null)) {
                continue;   // a blank row the server kept; it carries no tuple to key on
            }
            List<GridDataCell> cells = new ArrayList<>();
            for (int column : dataColumns) {
                cells.add(cell(view.getCell(row, column)));
            }
            rows.add(new DataRowImpl(new RowKeyImpl(tuple), cells));
        }
        logger.debug("Retrieved {} rows of {} columns for a grid asked for as {}x{}", rows.size(),
                dataColumns.size(), asked.getLeft().getRows(), asked.getTop().getColumns());
        return new DataRowsImpl(columnKeys, rows);
    }

    private GridMember resolve(String label) {
        GridMember member = members.get(label);
        if (member == null) {
            throw new InvalidMemberNameException(label);
        }
        return member;
    }

    private static GridDataCell cell(String value) {
        if (value == null || value.trim().isEmpty()) {
            return EmptyGridCell.EMPTY_CELL;
        }
        try {
            return new DoubleGridCell(Double.parseDouble(value.trim()));
        } catch (NumberFormatException notANumber) {
            // A formatted or text cell. Nothing here reads those yet, and an empty cell is a truer
            // answer than a zero would be.
            return EmptyGridCell.EMPTY_CELL;
        }
    }

    private void applySuppression(boolean suppressMissingRows) {
        EssCubeView.GridPreferences current = view.getPreferences();
        if (current.suppressMissingRows() == suppressMissingRows) {
            return;
        }
        view.setPreferences(new EssCubeView.GridPreferences(current.indentation(), suppressMissingRows,
                current.suppressZeroRows(), current.suppressUnderscoreRows(), current.repeatMemberLabels(),
                current.zoomInPreference(), current.includeSelection(), current.withinSelectedGroup(),
                current.removeUnselectedGroup()));
    }

    @Override
    public void send(IntermediateGrid intermediateGrid, UpdateOptions updateOptions)
            throws GridOperationException {
        throw new UnsupportedOperationException("This data source reads grids; it does not submit them. "
                + "Nothing comparing Jaygrid's navigation against Essbase's needs to write.");
    }

    /** A dimension, as Jaygrid wants to see one. */
    private final class EssbaseGridDimension implements GridDimension {

        private final EssMember dimension;

        private final int number;

        EssbaseGridDimension(EssMember dimension, int number) {
            this.dimension = dimension;
            this.number = number;
        }

        @Override
        public String getName() {
            return dimension.getName();
        }

        @Override
        public int getNumber() {
            return number;
        }

        @Override
        public Type getType() {
            return Type.STANDARD;
        }

        @Override
        public GridMember getRootMember() {
            return getMember(dimension.getName());
        }

        @Override
        public String toString() {
            return getName();
        }

    }

    /** A member, likewise. Children are wrapped on demand and kept, because zoom asks repeatedly. */
    private final class EssbaseGridMember implements GridMember {

        private final EssMember member;

        private final GridDimension dimension;

        private final GridMember parent;

        private List<GridMember> children;

        EssbaseGridMember(EssMember member, GridDimension dimension, GridMember parent) {
            this.member = member;
            this.dimension = dimension;
            this.parent = parent;
        }

        @Override
        public String getName() {
            return member.getName();
        }

        @Override
        public String getAlias(String aliasTable) {
            Object aliases = member.getProperties().get("aliases");
            if (!(aliases instanceof Map<?, ?> map)) {
                return null;
            }
            Object alias = map.get(aliasTable == null ? "Default" : aliasTable);
            return alias == null ? null : alias.toString();
        }

        @Override
        public GridDimension getDimension() {
            return dimension;
        }

        @Override
        public GridMember getParent() {
            return parent;
        }

        @Override
        public int getGeneration() {
            Object generation = member.getProperties().get("generationNumber");
            return generation == null ? 1 : Integer.parseInt(generation.toString());
        }

        @Override
        public int getLevel() {
            return member.getLevel();
        }

        @Override
        public synchronized List<GridMember> getChildren() {
            if (children == null) {
                children = new ArrayList<>();
                for (EssMember child : member.getChildren()) {
                    // Through the cache, so that the same member is the same object wherever it is
                    // reached from - Jaygrid compares members by equality in places.
                    GridMember wrapped = members.get(child.getName());
                    children.add(wrapped != null ? wrapped
                            : new EssbaseGridMember(child, dimension, this));
                }
            }
            return children;
        }

        @Override
        public boolean isShared() {
            return member.getDataStorage() == EssMember.DataStorage.SHARED;
        }

        @Override
        public String toString() {
            return getName();
        }

    }

    /** Keeps the member map's iteration order stable for anything that prints it. */
    static Map<String, GridMember> orderedCopy(Map<String, GridMember> source) {
        return new LinkedHashMap<>(source);
    }

}
