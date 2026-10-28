package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssGrid;
import com.appliedolap.essbase.misc.MdxJson;

import java.io.PrintStream;
import java.util.List;

public class EssGridImpl implements EssGrid {

    private final String[][] cells;

    private final int columns;

    /**
     * Copies the result's cells. The grid is as wide as its widest row, and a shorter row reads null
     * past its end: the server shortens the header row of a query whose rows all dropped out.
     */
    EssGridImpl(MdxJson json) {
        List<List<String>> data = json.getData() == null ? List.of() : json.getData();
        int cols = 0;
        for (List<String> row : data) {
            cols = Math.max(cols, row.size());
        }
        columns = cols;
        cells = new String[data.size()][cols];

        int rowIndex = 0;
        for (List<String> row : data) {
            int col = 0;
            for (String cell : row) {
                cells[rowIndex][col++] = cell;
            }
            rowIndex++;
        }
    }

    @Override
    public int getRows() {
        return cells.length;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public String getCell(int row, int col) {
        return cells[row][col];
    }

    public void print() {
       print(System.out);
    }

    public void print(PrintStream printStream) {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                String cell = getCell(row, col);
                printStream.printf("%20s", cell);
            }
            printStream.println();
        }
    }

}