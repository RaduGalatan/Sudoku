package com.example.sudoku.utility;

import android.widget.ImageView;

import com.example.sudoku.R;
import com.example.sudoku.database.Score;

import java.util.List;

public class Listeners {

    private final Comparators comparators;

    public Listeners() {
        comparators = new Comparators();
    }

    public void onMovesHeaderClick(List<Score> scoreList, ImageView arrow) {
        int i = comparators.getNextMovesComparator();
        switch (i % 3) {
            case (1):
                scoreList.sort(comparators.getMovesComparator());
                arrow.setImageResource(R.drawable.asc);
                break;

            case (2):
                scoreList.sort(comparators.getDescMovesComparator());
                arrow.setImageResource(R.drawable.desc);
                break;

            default:
                scoreList.sort(comparators.getIdMovesComparator());
                arrow.setImageResource(0);
                break;
        }
        comparators.setNextMovesComparator();
    }

    public void onDifficultyHeaderClick(List<Score> scoreList, ImageView arrow) {
        int i = comparators.getNextDifficultyComparator();
        switch (i % 3) {
            case (1):
                scoreList.sort(comparators.getDifficultyComparator());
                arrow.setImageResource(R.drawable.asc);
                break;

            case (2):
                scoreList.sort(comparators.getDescDifficultyComparator());
                arrow.setImageResource(R.drawable.desc);
                break;

            default:
                scoreList.sort(comparators.getIdMovesComparator());
                arrow.setImageResource(0);
                break;
        }
        comparators.setNextDifficultyComparator();
    }

    public void onTimeHeaderClick(List<Score> scoreList, ImageView arrow) {
        int i = comparators.getNextTimeComparator();
        switch (i % 3) {
            case (1):
                scoreList.sort(comparators.getTimeComparator());
                arrow.setImageResource(R.drawable.asc);
                break;

            case (2):
                scoreList.sort(comparators.getDescTimeComparator());
                arrow.setImageResource(R.drawable.desc);
                break;

            default:
                scoreList.sort(comparators.getIdMovesComparator());
                arrow.setImageResource(0);
                break;
        }
        comparators.setNextTimeComparator();
    }
}
