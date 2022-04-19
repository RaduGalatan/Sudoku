package com.example.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sudoku.R;
import com.example.sudoku.database.Score;
import com.example.sudoku.database.ScoreRepository;
import com.example.sudoku.utility.CustomAdapter;
import com.example.sudoku.utility.Listeners;

import java.util.List;


public class ScoreActivity extends AppCompatActivity {

    private ScoreRepository repo;
    private Listeners listeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        listeners=new Listeners();
        repo = new ScoreRepository(this);

        List<Score> scoreList = repo.getScores();

        ViewGroup movesView = findViewById(R.id.moves_header);
        ViewGroup difficultyView = findViewById(R.id.difficulty_header);
        ViewGroup timeView = findViewById(R.id.time_header);

        RecyclerView recyclerView = findViewById(R.id.scoreList);
        recyclerView.setAdapter(new CustomAdapter(scoreList));
        recyclerView.setLayoutManager(new LinearLayoutManager(ScoreActivity.this));

        Button btn = (Button) findViewById(R.id.backButton);

        btn.setOnClickListener(view -> {
            Intent i = new Intent(ScoreActivity.this, MainMenuActivity.class);
            startActivity(i);
            ScoreActivity.this.finish();
        });

        ImageView difficultyArrow = findViewById(R.id.difficulty_arrow);
        ImageView movesArrow = findViewById(R.id.moves_arrow);
        ImageView timeArrow = findViewById(R.id.time_arrow);

        movesView.setOnClickListener(view -> {
            listeners.onMovesHeaderClick(scoreList, movesArrow);
            recyclerView.setAdapter(new CustomAdapter(scoreList));
        });

        difficultyView.setOnClickListener(view -> {
            listeners.onDifficultyHeaderClick(scoreList, difficultyArrow);
            recyclerView.setAdapter(new CustomAdapter(scoreList));
        });

        timeView.setOnClickListener(view -> {
            listeners.onTimeHeaderClick(scoreList, timeArrow);
            recyclerView.setAdapter(new CustomAdapter(scoreList));
        });

    }

}