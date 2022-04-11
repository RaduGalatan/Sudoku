package com.example.sudoku.difficulty;

import androidx.annotation.NonNull;

public enum DifficultyLevel {
    EASY("Easy", 5, 0), //time in milliseconds
    MEDIUM("Medium", 1, 9000),
    HARD("Hard", 30, 600000);

    private final String stringValue;
    private final int intValue;
    private final long longValue;

    public int getEmptyCells() {
        return intValue;
    }


    public long getTime() {
        return longValue;
    }

    DifficultyLevel(String difficulty, int emptyCells, long time) {
        stringValue = difficulty;
        intValue = emptyCells;
        this.longValue = time;
    }

    @NonNull
    @Override
    public String toString() {
        return stringValue;
    }
}
