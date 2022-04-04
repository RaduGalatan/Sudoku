package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sudoku.R;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        Button btn=(Button) findViewById(R.id.restartButton2);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(LoseActivity.this, MainMenuActivity.class);
            startActivity(i);
            LoseActivity.this.finish();
        });
    }
}