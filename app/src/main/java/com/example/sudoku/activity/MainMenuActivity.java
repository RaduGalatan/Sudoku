package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.sudoku.difficulty.Difficulty;
import com.example.sudoku.difficulty.DifficultyLevel;
import com.example.sudoku.R;

import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Difficulty difficulty = new Difficulty(DifficultyLevel.EASY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //set the content of the dropdown
        Spinner spinner = (Spinner) findViewById(R.id.difficultyModes);
        ArrayAdapter<DifficultyLevel> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, DifficultyLevel.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button startButton = (Button) findViewById(R.id.startButton);


        startButton.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            i.putExtra("difficulty", difficulty);

            startActivity(i);
        });

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
            long minutes = (difficulty.getDifficultyLevel().getTime() / 60000) % 60;
            data = String.format(Locale.UK, "Time limit: %d minutes", minutes);
        } else {
            data = "No time limit";
        }
        difficultyInfo.setText(data);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.getItemAtPosition(1);
    }
}