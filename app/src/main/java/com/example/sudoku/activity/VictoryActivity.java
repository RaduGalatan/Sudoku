package com.example.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sudoku.DifficultyLevel;
import com.example.sudoku.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class VictoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        long limit=getIntent().getLongExtra("timeLimit",0);
        TextView timeView = findViewById(R.id.time);

        if(limit!=0) {
            NumberFormat f = new DecimalFormat("00");

            long limitMin = (limit / 60000) % 60;
            long limitSec = (limit / 1000) % 60;
            //time limit in mm:ss format
            String arg1 = f.format(limitMin) + ":" + f.format(limitSec);

            long elapsedTime = getIntent().getLongExtra("time", 0);
            long elapsedMin = (elapsedTime / 60000) % 60;
            long elapsedSec = (elapsedTime / 1000) % 60;
            //elapsed time in mm:ss format
            String arg2 = f.format(elapsedMin) + ":" + f.format(elapsedSec);

            String msg = getResources().getString(R.string.time, arg2, arg1);

            timeView.setText(msg);
        }

        else{
            timeView.setText("");
        }

        Button btn=(Button) findViewById(R.id.restartButton);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(VictoryActivity.this, MainMenuActivity.class);
            startActivity(i);
            VictoryActivity.this.finish();
        });
    }
}
