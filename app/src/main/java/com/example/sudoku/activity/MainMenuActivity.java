package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sudoku.Difficulty;
import com.example.sudoku.R;

public class MainMenuActivity extends AppCompatActivity {

    Difficulty difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button easyBtn=(Button) findViewById(R.id.easyButton);
        Button mediumBtn=(Button) findViewById(R.id.mediumButton);
        Button hardBtn=(Button) findViewById(R.id.hardButton);

        easyBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            difficulty=new Difficulty(easyBtn.getText().toString());
            i.putExtra("difficulty",difficulty);
            startActivity(i);
        });

        mediumBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            difficulty=new Difficulty(mediumBtn.getText().toString());
            i.putExtra("difficulty",difficulty);
            startActivity(i);
        });

        hardBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            difficulty=new Difficulty(hardBtn.getText().toString());
            i.putExtra("difficulty",difficulty);
            startActivity(i);
        });


    }
}