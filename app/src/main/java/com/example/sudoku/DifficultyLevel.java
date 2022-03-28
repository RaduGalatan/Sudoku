package com.example.sudoku;

import androidx.annotation.NonNull;

public enum DifficultyLevel {
    Easy("Easy", 5),
    Medium("Medium", 10),
    Hard("Hard", 30);

    private final String stringValue;
    private final int intValue;

    public int getEmptyCells() {
        return intValue;
    }


    DifficultyLevel(String difficulty, int emptyCells) {
        stringValue = difficulty;
        intValue = emptyCells;
    }

    @NonNull
    @Override
    public String toString() {
        return stringValue;
    }
}
