package com.example.sudoku.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score {

    public Score() {

    }

    public Score(String difficulty, String elapsedTime, int moves) {
        this.difficulty = difficulty;
        this.elapsedTime = elapsedTime;
        this.numberOfMoves = moves;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    @ColumnInfo(name = "difficulty")
    private String difficulty;

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    @ColumnInfo(name = "elapsed_time")
    private String elapsedTime;

    @ColumnInfo(name = "number_of_moves")
    private int numberOfMoves;
}
