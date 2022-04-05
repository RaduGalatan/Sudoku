package com.example.sudoku.outcome;

import androidx.annotation.NonNull;

public enum Outcome {
    WIN("YOU WIN!"),
    LOSE("YOU LOSE!");

    private final String outcome;

    public String getOutcome(){
        return outcome;
    }

    Outcome(String outcome){
        this.outcome=outcome;
    }



    @NonNull
    @Override
    public String toString() {
        return outcome;
    }
}
