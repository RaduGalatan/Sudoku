package com.example.sudoku.game;

public class Cell {

    public final int row;
    public final int col;
    public int value;
    public boolean isStartingCell;

    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        isStartingCell = false;
    }
}
