package com.example.sudoku.outcome;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.sudoku.R;

public enum Outcome {
    WIN(R.string.victory),
    LOSE(R.string.defeat);

    private final int outcome;

    Outcome(int outcome) {
        this.outcome = outcome;
    }


    @NonNull
    public String toString(Resources r) {
        return r.getString(outcome);
    }
}
