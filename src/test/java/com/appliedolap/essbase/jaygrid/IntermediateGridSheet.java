package com.appliedolap.essbase.jaygrid;

import com.appliedolap.jaygrid.Grid;
import com.appliedolap.jaygrid.GridMember;
import com.appliedolap.jaygrid.intermediate.IntermediateGrid;

/**
 * An intermediate grid written back out as a sheet of labels, the way Essbase wants one.
 *
 * <p>Jaygrid holds a grid as three regions; Essbase takes a rectangle of names and reads the regions
 * back out of where they sit. The one rule that is easy to get wrong is where the POV row starts - at
 * the column the left axis ends on, not at column 1 - which is the same off-by-one that makes a pivot
 * on a two-column left axis move the wrong dimension.
 */
final class IntermediateGridSheet {

    private IntermediateGridSheet() {
    }

    static String[][] of(IntermediateGrid intermediateGrid) {
        Grid<GridMember> pov = intermediateGrid.getPov();
        Grid<GridMember> top = intermediateGrid.getTop();
        Grid<GridMember> left = intermediateGrid.getLeft();

        int povHeight = intermediateGrid.povHeight();
        int leftColumns = left.getColumns();
        int povMembers = pov == null ? 0 : pov.getColumns();

        int rows = povHeight + top.getRows() + left.getRows();
        // Wide enough for the data region or the POV, whichever needs more room to its right.
        int columns = leftColumns + Math.max(top.getColumns(), povMembers);

        String[][] sheet = new String[rows][columns];
        for (String[] row : sheet) {
            java.util.Arrays.fill(row, "");
        }

        for (int member = 0; member < povMembers; member++) {
            sheet[0][leftColumns + member] = name(pov.getCell(0, member));
        }
        for (int row = 0; row < top.getRows(); row++) {
            for (int column = 0; column < top.getColumns(); column++) {
                sheet[povHeight + row][leftColumns + column] = name(top.getCell(row, column));
            }
        }
        int firstDataRow = povHeight + top.getRows();
        for (int row = 0; row < left.getRows(); row++) {
            for (int column = 0; column < leftColumns; column++) {
                sheet[firstDataRow + row][column] = name(left.getCell(row, column));
            }
        }
        return sheet;
    }

    private static String name(GridMember member) {
        return member == null ? "" : member.getName();
    }

}
