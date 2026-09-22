package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.EssMember;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Asks a live Essbase server what an ad hoc operation does, and writes the answer down.
 *
 * <p>Every capture is the same three steps: install a basis grid, apply one operation, record what
 * came back. The point is the first step - {@link EssCubeView#setLayout(String[][], int, int)} can
 * install a grid of any shape and region split, so the oracle is not limited to whatever the server
 * happens to have lying around, and a fixture can be written for a grid chosen to probe one rule.
 *
 * <p>The basis recorded in the fixture is the grid the server <em>answered with</em>, not the sheet it
 * was handed. Those differ whenever the server reinterprets a layout, and it is the server's reading
 * that the operation was applied to - so recording the request would make the fixture a quiet lie.
 * {@link #capture} says so out loud when they differ in shape.
 */
public class GridOracle {

    private final EssCube cube;

    private final List<String> dimensions;

    public GridOracle(EssCube cube) {
        this.cube = cube;
        this.dimensions = new ArrayList<>();
        for (EssMember dimension : cube.getOutline().getDimensions()) {
            dimensions.add(dimension.getName());
        }
    }

    /** The operations worth an oracle - the ones a client has to implement itself. */
    public enum Action {
        KEEP_ONLY, REMOVE_ONLY, ZOOM_IN, ZOOM_OUT;

        /** The name {@link EssCubeView} uses, which is the name that goes in the fixture. */
        String wireName() {
            StringBuilder out = new StringBuilder();
            for (String word : name().toLowerCase().split("_")) {
                out.append(out.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1));
            }
            return out.toString();
        }
    }

    /**
     * Captures one operation.
     *
     * @param name        what the fixture is called, and its file name
     * @param sheet       the basis grid to install
     * @param headerRows  how many of its rows precede the data region
     * @param leftColumns how many of its columns the left axes occupy
     * @param action      the operation to apply
     * @param ranges      what it is applied to
     */
    public GridFixture capture(String name, String[][] sheet, int headerRows, int leftColumns,
            Action action, List<EssCubeView.Range> ranges) {
        return capture(name, sheet, headerRows, leftColumns, action, ranges, DEFAULTS);
    }

    /**
     * The preference set every capture starts from, and the one the ad hoc options are varied against.
     *
     * <p>It is Essbase's own defaults as a fresh session reports them, written out rather than read,
     * because the preferences resource is per session and not per grid: a capture that did not state
     * them would inherit whatever the capture before it left behind, and the corpus would depend on the
     * order the tests happened to run in.
     */
    public static final EssCubeView.GridPreferences DEFAULTS = new EssCubeView.GridPreferences(
            EssCubeView.Indentation.SUBITEMS, false, false, false, true,
            EssCubeView.ZoomInPreference.NEXT_LEVEL, true, false, false);

    /** The same preferences with one of the ad hoc options changed. */
    public static EssCubeView.GridPreferences zoomingBy(EssCubeView.ZoomInPreference zoomIn) {
        return new EssCubeView.GridPreferences(DEFAULTS.indentation(), DEFAULTS.suppressMissingRows(),
                DEFAULTS.suppressZeroRows(), DEFAULTS.suppressUnderscoreRows(),
                DEFAULTS.repeatMemberLabels(), zoomIn, DEFAULTS.includeSelection(),
                DEFAULTS.withinSelectedGroup(), DEFAULTS.removeUnselectedGroup());
    }

    public static EssCubeView.GridPreferences with(boolean includeSelection, boolean withinSelectedGroup,
            boolean removeUnselectedGroup) {
        return new EssCubeView.GridPreferences(DEFAULTS.indentation(), DEFAULTS.suppressMissingRows(),
                DEFAULTS.suppressZeroRows(), DEFAULTS.suppressUnderscoreRows(),
                DEFAULTS.repeatMemberLabels(), DEFAULTS.zoomInPreference(), includeSelection,
                withinSelectedGroup, removeUnselectedGroup);
    }

    public static EssCubeView.GridPreferences repeatingMemberLabels(boolean repeat) {
        return new EssCubeView.GridPreferences(DEFAULTS.indentation(), DEFAULTS.suppressMissingRows(),
                DEFAULTS.suppressZeroRows(), DEFAULTS.suppressUnderscoreRows(), repeat,
                DEFAULTS.zoomInPreference(), DEFAULTS.includeSelection(),
                DEFAULTS.withinSelectedGroup(), DEFAULTS.removeUnselectedGroup());
    }

    /**
     * Captures one operation under a stated set of ad hoc preferences.
     *
     * @param preferences the options in force, set before the basis is installed so that they are in
     *                    force for the retrieve as well as for the operation
     */
    public GridFixture capture(String name, String[][] sheet, int headerRows, int leftColumns,
            Action action, List<EssCubeView.Range> ranges, EssCubeView.GridPreferences preferences) {
        EssCubeView view = cube.openCubeView();
        view.setPreferences(preferences);
        view = cube.openCubeView();
        view.setLayout(sheet, headerRows, leftColumns);

        GridFixture.Sheet basis = GridFixture.Sheet.of(view);
        if (basis.rows() != sheet.length || basis.columns() != sheet[0].length) {
            System.out.println("NOTE: " + name + " asked for a " + sheet.length + "x" + sheet[0].length
                    + " basis and the server answered with " + basis.rows() + "x" + basis.columns()
                    + ". The fixture records what it answered with.");
        }

        switch (action) {
            case KEEP_ONLY -> view.keepOnly(ranges);
            case REMOVE_ONLY -> view.removeOnly(ranges);
            case ZOOM_IN -> view.zoomIn(ranges);
            case ZOOM_OUT -> view.zoomOut(ranges);
        }

        return new GridFixture(name, cube.getApplication().getName(), cube.getName(), dimensions,
                view.getAliasTable(), view.getPreferences(), operation(action, ranges, basis), basis,
                GridFixture.Sheet.of(view));
    }

    private static GridFixture.Operation operation(Action action, List<EssCubeView.Range> ranges,
            GridFixture.Sheet basis) {
        List<List<Integer>> wire = new ArrayList<>();
        List<String> selected = new ArrayList<>();
        for (EssCubeView.Range range : ranges) {
            wire.add(List.of(range.getStartRow(), range.getStartColumn(),
                    range.getRowCount(), range.getColumnCount()));
            for (int row = range.getStartRow(); row < range.getStartRow() + range.getRowCount(); row++) {
                for (int column = range.getStartColumn();
                        column < range.getStartColumn() + range.getColumnCount(); column++) {
                    selected.add(basis.cell(row, column));
                }
            }
        }
        return new GridFixture.Operation(action.wireName(), wire, selected);
    }

    /**
     * Captures a pivot, which is not a selection.
     *
     * <p>The grid actions this oracle covers otherwise all take a set of cells. Pivot does not: the
     * REST action carries two <em>coordinates</em>, a source and a destination, and a client that thinks
     * in clicked cells has to decide for itself what pair a click means. That mismatch is the whole
     * difficulty with pivot, so the fixture records the coordinates it sent rather than pretending they
     * were a selection.
     */
    public GridFixture capturePivot(String name, String[][] sheet, int headerRows, int leftColumns,
            int from, int to, EssCubeView.GridPreferences preferences) {
        EssCubeView view = cube.openCubeView();
        view.setPreferences(preferences);
        view = cube.openCubeView();
        view.setLayout(sheet, headerRows, leftColumns);

        GridFixture.Sheet basis = GridFixture.Sheet.of(view);
        view.pivot(from, to);

        GridFixture.Operation operation = new GridFixture.Operation("pivot",
                List.of(List.of(from, to)), List.of());
        return new GridFixture(name, cube.getApplication().getName(), cube.getName(), dimensions,
                view.getAliasTable(), view.getPreferences(), operation, basis,
                GridFixture.Sheet.of(view));
    }

    /** The same for {@code pivotToPov}, which takes a cell rather than a pair of columns. */
    public GridFixture capturePivotToPov(String name, String[][] sheet, int headerRows, int leftColumns,
            int row, int column, EssCubeView.GridPreferences preferences) {
        EssCubeView view = cube.openCubeView();
        view.setPreferences(preferences);
        view = cube.openCubeView();
        view.setLayout(sheet, headerRows, leftColumns);

        GridFixture.Sheet basis = GridFixture.Sheet.of(view);
        view.pivotToPov(row, column);

        GridFixture.Operation operation = new GridFixture.Operation("pivotToPov",
                List.of(List.of(row, column, 1, 1)), List.of(basis.cell(row, column)));
        return new GridFixture(name, cube.getApplication().getName(), cube.getName(), dimensions,
                view.getAliasTable(), view.getPreferences(), operation, basis,
                GridFixture.Sheet.of(view));
    }

    /** Captures and writes in one go, so a corpus reads as one line per case. */
    public GridFixture captureTo(File directory, String name, String[][] sheet, int headerRows,
            int leftColumns, Action action, List<EssCubeView.Range> ranges) throws IOException {
        GridFixture fixture = capture(name, sheet, headerRows, leftColumns, action, ranges);
        fixture.writeTo(new File(directory, name + ".json"));
        return fixture;
    }

}
