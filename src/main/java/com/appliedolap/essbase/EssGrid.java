package com.appliedolap.essbase;

public interface EssGrid {

    int getRows();

    int getColumns();

    String getCell(int row, int col);

}