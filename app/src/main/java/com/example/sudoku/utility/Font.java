package com.example.sudoku.utility;


public enum Font {
    SMALL(20),
    MEDIUM(24),
    LARGE(32);

    private final int size;

    Font(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
