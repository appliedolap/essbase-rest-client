package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssGrid;
import com.appliedolap.essbase.misc.MdxJson;

import java.io.PrintStream;
import java.util.List;

public class EssGridImpl implements EssGrid {

    private final String[][] cells;

    EssGridImpl(MdxJson json) {
        int rows = json.getData().size();
        int cols = json.getData().get(0).size();

        cells = new String[rows][cols];

        int rowIndex = 0;
        for (List<String> row : json.getData()) {
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
        return cells[0].length;
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