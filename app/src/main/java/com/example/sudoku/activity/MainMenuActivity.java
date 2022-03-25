package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sudoku.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button easyBtn=(Button) findViewById(R.id.easyButton);
        Button mediumBtn=(Button) findViewById(R.id.mediumButton);
        Button hardBtn=(Button) findViewById(R.id.hardButton);

        easyBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            i.putExtra("difficulty",easyBtn.getText().toString());
            startActivity(i);
           // MainMenuActivity.this.finish();
        });

        mediumBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            i.putExtra("difficulty",mediumBtn.getText().toString());
            startActivity(i);
            MainMenuActivity.this.finish();
        });

        hardBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
            i.putExtra("difficulty",hardBtn.getText().toString());
            startActivity(i);
            MainMenuActivity.this.finish();
        });


    }
}