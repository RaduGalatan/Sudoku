package com.example.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sudoku.R;

public class VictoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);
        Toast.makeText(VictoryActivity.this, "You win! <3", Toast.LENGTH_SHORT).show();

        Button btn = (Button) findViewById(R.id.restartButton);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(VictoryActivity.this, MainMenuActivity.class);
            startActivity(i);
            VictoryActivity.this.finish();
        });
    }
}
