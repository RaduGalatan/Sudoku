package com.example.sudoku.utility;

import com.example.sudoku.database.Score;
import com.example.sudoku.difficulty.DifficultyLevel;

import java.util.Comparator;

public class Comparators {

    private final MovesComparator movesComparator;
    private final IdComparator idComparator;
    private final DifficultyComparator difficultyComparator;
    private final TimeComparator timeComparator;

    private int nextTimeComparator;
    private int nextMovesComparator;
    private int nextDifficultyComparator;

    public Comparators() {
        movesComparator = new MovesComparator();
        idComparator = new IdComparator();
        difficultyComparator = new DifficultyComparator();
        timeComparator = new TimeComparator();

        nextTimeComparator = 1;
        nextMovesComparator = 1;
        nextDifficultyComparator = 1;
    }

    public MovesComparator getMovesComparator() {
        return movesComparator;
    }

    public TimeComparator getTimeComparator() {
        return timeComparator;
    }

    public DifficultyComparator getDifficultyComparator() {
        return difficultyComparator;
    }

    public Comparator<Score> getDescTimeComparator() {
        return timeComparator.reversed();
    }

    public Comparator<Score> getDescDifficultyComparator() {
        return difficultyComparator.reversed();
    }

    public Comparator<Score> getDescMovesComparator() {
        return movesComparator.reversed();
    }


    public IdComparator getIdMovesComparator() {
        return idComparator;
    }

    public int getNextMovesComparator() {
        return nextMovesComparator;
    }

    public void setNextMovesComparator() {
        nextMovesComparator++;
    }

    public int getNextDifficultyComparator() {
        return nextDifficultyComparator;
    }

    public void setNextDifficultyComparator() {
        nextDifficultyComparator++;
    }

    public int getNextTimeComparator() {
        return nextTimeComparator;
    }

    public void setNextTimeComparator() {
        nextTimeComparator++;
    }
}

class MovesComparator implements Comparator<Score> {
    @Override
    public int compare(Score score1, Score score2) {
        return score1.getNumberOfMoves() - score2.getNumberOfMoves();
    }

}

class TimeComparator implements Comparator<Score> {

    @Override
    public int compare(Score score, Score score2) {

        String[] timeString = score.getElapsedTime().split(":");
        String[] timeString2 = score2.getElapsedTime().split(":");

        long time1 = (TimeFunctions.TimeConvert.timeToMilli(timeString[0], timeString[1]));
        long time2 = (TimeFunctions.TimeConvert.timeToMilli(timeString2[0], timeString2[1]));

        return (int) (time1 - time2);
    }
}

class DifficultyComparator implements Comparator<Score> {

    @Override
    public int compare(Score score, Score score2) {
        if (score.getDifficulty().equals(score2.getDifficulty()))
            return 0;

        if (score.getDifficulty().equals(DifficultyLevel.MEDIUM.toString()) &&
                score2.getDifficulty().equals(DifficultyLevel.HARD.toString())) return -1;

        return 1;
    }
}

class IdComparator implements Comparator<Score> {
    @Override
    public int compare(Score score1, Score score2) {
        return score1.getId() - score2.getId();
    }
}