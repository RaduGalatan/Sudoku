package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.example.sudoku.Difficulty;
import com.example.sudoku.DifficultyLevel;
import com.example.sudoku.R;

import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //set the content of the dropdown
        Spinner spinner = (Spinner) findViewById(R.id.difficultyModes);
        ArrayAdapter<DifficultyLevel>adapter=new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, DifficultyLevel.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button startButton=(Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);

            Difficulty difficulty=new Difficulty
                    (DifficultyLevel.valueOf(spinner.getSelectedItem().toString().toUpperCase()));

            i.putExtra("difficulty",difficulty);
            startActivity(i);
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
         adapterView.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.getItemAtPosition(1);
    }
}