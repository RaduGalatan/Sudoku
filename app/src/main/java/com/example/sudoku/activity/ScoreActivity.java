package com.example.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sudoku.CustomAdapter;
import com.example.sudoku.R;
import com.example.sudoku.database.Score;
import com.example.sudoku.database.ScoreRepository;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    private ScoreRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        repo = new ScoreRepository(this);

        List<Score> scoreList = repo.getScores();

        RecyclerView recyclerView = findViewById(R.id.scoreList);
        recyclerView.setAdapter(new CustomAdapter(scoreList));
        recyclerView.setLayoutManager(new LinearLayoutManager(ScoreActivity.this));

        Button btn = (Button) findViewById(R.id.backButton);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(ScoreActivity.this, MainMenuActivity.class);
            startActivity(i);
            ScoreActivity.this.finish();
        });
    }
}