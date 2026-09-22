package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.EssCubeView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * One grid operation as Essbase actually answered it: the grid that went in, the operation, and the
 * grid that came out.
 *
 * <p>This exists so that a client-side implementation of the ad hoc operations - Jaygrid, which has to
 * do zoom, keep only, remove only and pivot locally because the Planning REST API offers nothing but
 * "retrieve" - can be diffed against the engine rather than against someone's recollection of what the
 * engine does. A fixture is written once from a live server and then read offline forever.
 *
 * <p>Three things travel with the grids because the answers are meaningless without them. The
 * {@linkplain #aliasTable() alias table} is the first and the easiest to be caught out by: a grid
 * accepts member names on the way in and answers in aliases, so a fixture whose basis says {@code ALL}
 * has a result that says {@code All Accounts}, and a client diffing names against labels finds nothing
 * but differences. The
 * {@linkplain #preferences() preferences} in force decide much of the result on their own: whether the
 * selected cell is kept, whether a zoom stays inside the selected group, whether missing rows survive.
 * And the {@linkplain Sheet#placements() placements} say which of the three regions each dimension was
 * in, as the server reports it - a layout the server quietly reinterpreted looks entirely reasonable
 * in the labels and is only visible there.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GridFixture(
        String name,
        String application,
        String cube,
        List<String> dimensions,
        String aliasTable,
        EssCubeView.GridPreferences preferences,
        Operation operation,
        Sheet basis,
        Sheet result) {

    /**
     * A grid: its labels, and the structure the server says those labels have.
     *
     * @param headerRows  rows before the data region - the POV row, if any, plus one per top axis
     * @param leftColumns columns the left/row axes occupy
     * @param cells       row-major labels, with empty strings for blanks; data cells hold their value
     * @param placements  where each dimension sits, from the server rather than inferred
     */
    public record Sheet(
            int rows,
            int columns,
            int headerRows,
            int leftColumns,
            List<List<String>> cells,
            List<Placement> placements) {

        /** Reads a view's current state, deriving the region boundaries from the placements. */
        public static Sheet of(EssCubeView view) {
            List<Placement> placements = new ArrayList<>();
            int leftColumns = 0;
            int topRows = 0;
            boolean anyPov = false;
            for (EssCubeView.DimensionPlacement placement : view.getPlacements()) {
                placements.add(new Placement(placement.name(), placement.region().name(), placement.index()));
                switch (placement.region()) {
                    case LEFT -> leftColumns++;
                    case TOP -> topRows++;
                    case POV -> anyPov = true;
                }
            }

            List<List<String>> cells = new ArrayList<>();
            for (int row = 0; row < view.getRows(); row++) {
                List<String> line = new ArrayList<>();
                for (int column = 0; column < view.getColumns(); column++) {
                    String cell = view.getCell(row, column);
                    line.add(cell == null ? "" : cell);
                }
                cells.add(line);
            }

            // One POV row if anything is in the POV at all, and none if the grid has no POV - a grid
            // with every dimension on an axis simply has no such row.
            return new Sheet(view.getRows(), view.getColumns(), topRows + (anyPov ? 1 : 0), leftColumns,
                    cells, placements);
        }

        public String cell(int row, int column) {
            return cells.get(row).get(column);
        }

        /** The grid as it looks on screen, for a failure message worth reading. */
        public String render() {
            StringBuilder out = new StringBuilder();
            for (List<String> line : cells) {
                for (String cell : line) {
                    out.append(String.format("%-16s", cell));
                }
                out.append('\n');
            }
            return out.toString();
        }

    }

    /** @param index the grid row for {@code TOP}, the grid column for {@code LEFT}, -1 for {@code POV} */
    public record Placement(String name, String region, int index) {
    }

    /**
     * What was asked for.
     *
     * @param action   the grid action, named as {@link EssCubeView} names it
     * @param ranges   the selection as the wire carries it: {@code [startRow, startColumn, rowCount,
     *                 columnCount]} per range, which is start plus counts and not a pair of corners
     * @param selected the labels those ranges land on in the basis, which is what a client-side
     *                 implementation is actually given and is far easier to read than coordinates
     */
    public record Operation(String action, List<List<Integer>> ranges, List<String> selected) {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void writeTo(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.isDirectory() && !parent.mkdirs()) {
            throw new IOException("Could not create " + parent);
        }
        MAPPER.writeValue(file, this);
    }

    public static GridFixture read(File file) throws IOException {
        return MAPPER.readValue(file, GridFixture.class);
    }

}
