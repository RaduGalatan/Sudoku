package com.example.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sudoku.R;
import com.example.sudoku.TimeFunctions.TimeConvert;
import com.example.sudoku.game.SudokuGame;
import com.example.sudoku.outcome.GameOutcome;

public class GameResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        GameOutcome outcome = getIntent().getParcelableExtra("outcome");

        long limit = outcome.getTimeLimit();

        TextView timeView = findViewById(R.id.time);
        TextView outcomeView = findViewById(R.id.outcomeText);
        TextView movesView = findViewById(R.id.moves);

        outcomeView.setText(outcome.getOutcome().toString(getResources()));


        if (limit != 0) {

            int limitMin = TimeConvert.millisToMin(limit);
            int limitSec = TimeConvert.millisToSec(limit);
            //time limit in mm:ss format
            String arg1 = TimeConvert.timeToString(limitMin, limitSec);

            long elapsedTime = outcome.getTimeElapsed();
            int elapsedMin = TimeConvert.millisToMin(elapsedTime);
            int elapsedSec = TimeConvert.millisToSec(elapsedTime);
            //elapsed time in mm:ss format
            String arg2 = TimeConvert.timeToString(elapsedMin, elapsedSec);

            String time = getResources().getString(R.string.time, arg2, arg1);

            timeView.setText(time);

        } else {
            timeView.setText("");
        }

        int extraMoves = outcome.getMoves() - SudokuGame.getStartingEmptyCells();
        if (extraMoves > 0) {

            String moves = getResources().getString(R.string.moves, extraMoves);
            movesView.setText(moves);
        } else {
            movesView.setText("");
        }

        Button btn = (Button) findViewById(R.id.restartButton);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(GameResultActivity.this, MainMenuActivity.class);
            startActivity(i);
            GameResultActivity.this.finish();
        });
    }
}