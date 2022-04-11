package com.example.sudoku.game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Cell> cells;
    int size;

    public Board(int size, List<Cell> cells) {
        this.size = size;
        this.cells = cells;
    }

    public Cell getCell(int row, int col) {
        return cells.get(row * size + col);
    }
}
