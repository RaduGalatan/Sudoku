package com.example.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sudoku.R;
import com.example.sudoku.database.SudokuDatabase;
import com.example.sudoku.difficulty.Difficulty;
import com.example.sudoku.difficulty.DifficultyLevel;
import com.example.sudoku.utility.GameState;
import com.example.sudoku.utility.TimeFunctions.TimeConvert;


public class MainMenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Difficulty difficulty = new Difficulty(DifficultyLevel.EASY);
    GameState state = GameState.getGameState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SudokuDatabase db = Room.databaseBuilder(getApplicationContext(),
                SudokuDatabase.class, "sudoku-database").build();

        //set the content of the dropdown
        Spinner spinner = (Spinner) findViewById(R.id.difficultyModes);
        ArrayAdapter<DifficultyLevel> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, DifficultyLevel.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button startButton = (Button) findViewById(R.id.startButton);
        Button scoresButton = (Button) findViewById(R.id.scoresButton);


        startButton.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            i.putExtra("difficulty", difficulty);
            GameState.newGameState();
            startActivity(i);
        });

        scoresButton.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, ScoreActivity.class);
            startActivity(i);
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();

        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            i.putExtra("difficulty", difficulty);
            startActivity(i);
        });

        state = GameState.getGameState();

        if (state == null) {
            continueButton.setClickable(false);
            continueButton.setAlpha(0.5f);
        } else {
            continueButton.setClickable(true);
            continueButton.setAlpha(1f);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        adapterView.getItemAtPosition(pos);

        difficulty = new Difficulty
                (DifficultyLevel.valueOf(adapterView.getSelectedItem().toString().toUpperCase()));

        setDifficultyData();

    }

    private void setDifficultyData() {
        TextView difficultyInfo = findViewById(R.id.difficultyData);
        String data;

        if (difficulty.getDifficultyLevel() != DifficultyLevel.EASY) {
            long minutes = TimeConvert.millisToMin(difficulty.getDifficultyLevel().getTime());
            data = getResources().getString(R.string.time_limit, minutes);
        } else {
            data = getString(R.string.no_limit);
        }
        difficultyInfo.setText(data);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.getItemAtPosition(1);
    }
}