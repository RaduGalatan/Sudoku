package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sudoku.R;
import com.example.sudoku.outcome.GameOutcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GameResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        GameOutcome outcome = getIntent().getParcelableExtra("outcome");

        long limit = outcome.getTimeLimit();

        TextView timeView = findViewById(R.id.time);
        TextView outcomeView = findViewById(R.id.outcomeText);

        outcomeView.setText(outcome.getOutcome().toString(getResources()));

        if (limit != 0) {
            NumberFormat f = new DecimalFormat("00");

            long limitMin = (limit / 60000) % 60;
            long limitSec = (limit / 1000) % 60;
            //time limit in mm:ss format
            String arg1 = String.format("%s:%s", f.format(limitMin), f.format(limitSec));

            long elapsedTime = outcome.getTimeElapsed();
            long elapsedMin = (elapsedTime / 60000) % 60;
            long elapsedSec = (elapsedTime / 1000) % 60;
            //elapsed time in mm:ss format
            String arg2 = String.format("%s:%s", f.format(elapsedMin), f.format(elapsedSec));

            String msg = getResources().getString(R.string.time, arg2, arg1);

            timeView.setText(msg);
        } else {
            timeView.setText("");
        }

        Button btn = (Button) findViewById(R.id.restartButton);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(GameResultActivity.this, MainMenuActivity.class);
            startActivity(i);
            GameResultActivity.this.finish();
        });
    }
}