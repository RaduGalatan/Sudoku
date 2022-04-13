package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import com.example.sudoku.TimeFunctions;
import com.example.sudoku.database.Score;
import com.example.sudoku.database.ScoreRepository;
import com.example.sudoku.difficulty.Difficulty;
import com.example.sudoku.difficulty.DifficultyLevel;
import com.example.sudoku.R;
import com.example.sudoku.game.Cell;
import com.example.sudoku.game.SudokuGame;
import com.example.sudoku.gameTime.GameClock;
import com.example.sudoku.gameTime.GameTimeout;
import com.example.sudoku.outcome.GameOutcome;
import com.example.sudoku.outcome.Outcome;
import com.example.sudoku.view.BoardView;
import com.example.sudoku.viewmodel.SudokuViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements BoardView.OnTouchListener {

    private SudokuViewModel viewModel;
    private BoardView view;
    private final List<Button> buttons = new ArrayList<>();
    private ScoreRepository repo;

    private long timeLimit;
    Difficulty difficulty;

    private GameTimeout counter;
    private GameOutcome outcome;
    private GameClock gameClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        repo = new ScoreRepository(this);
        difficulty = getIntent().getParcelableExtra("difficulty");

        TextView difficultyName = findViewById(R.id.difficulty);
        difficultyName.setText(difficulty.getDifficultyLevel().toString());

        view = (BoardView) findViewById(R.id.boardView);
        view.registerListener(this);

        viewModel = new ViewModelProvider(this).get(SudokuViewModel.class);

        viewModel.game = new SudokuGame(difficulty);
        viewModel.game.selectedCellLiveData.observe(this, this::updatedSelectedCellUI
        );

        viewModel.game.cellsLiveData.observe(this, this::updateCells
        );

        initialiseButtons();
        setButtonsListeners();

        if (difficulty.getDifficultyLevel() != DifficultyLevel.EASY) {
            timeLimit = difficulty.getDifficultyLevel().getTime();
            gameClock = new GameClock() {
                @Override
                public void onUpdate(int sec, int min, long elapsedTime) {
                    TextView view = findViewById(R.id.timer);

                    NumberFormat f = new DecimalFormat("00");
                    String formattedSec = f.format(sec);
                    String formattedMin = f.format(min);
                    String timeLeftString = getResources().getString(R.string.time_left, formattedMin, formattedSec);
                    view.setText(timeLeftString);
                }

                @Override
                public void onStop(long elapsedTime) {
                    outcome = new GameOutcome(Outcome.LOSE, elapsedTime, 0, 0);
                    Intent i = new Intent(GameActivity.this, GameResultActivity.class);
                    i.putExtra("outcome", outcome);
                    startActivity(i);
                    finish();
                }

            };

            counter = new GameTimeout(timeLimit, 0, gameClock);

        }
    }

    private void initialiseButtons() {
        buttons.add((Button) findViewById(R.id.buttonOne));
        buttons.add((Button) findViewById(R.id.buttonTwo));
        buttons.add((Button) findViewById(R.id.buttonThree));
        buttons.add((Button) findViewById(R.id.buttonFour));
        buttons.add((Button) findViewById(R.id.buttonFive));
        buttons.add((Button) findViewById(R.id.buttonSix));
        buttons.add((Button) findViewById(R.id.buttonSeven));
        buttons.add((Button) findViewById(R.id.buttonEight));
        buttons.add((Button) findViewById(R.id.buttonNine));
    }

    private void setButtonsListeners() {
        for (int i = 0; i < buttons.size(); i++) {
            int i2 = i;
            buttons.get(i).setOnClickListener(view -> viewModel.game.handleInput(i2 + 1));
        }
    }

    private void updateCells(List<Cell> cells) {
        if (cells != null) {
            BoardView.updateCells(cells);
            view.invalidate();
            if (viewModel.game.winCondition()) {
                if (counter != null) {
                    outcome = new GameOutcome(Outcome.WIN, counter.getElapsedTime(),
                            timeLimit, viewModel.game.getMoves());

                    int min = TimeFunctions.TimeConvert.millisToMin(counter.getElapsedTime());
                    int sec = TimeFunctions.TimeConvert.millisToSec(counter.getElapsedTime());
                    Score score = new Score(
                            difficulty.getDifficultyLevel().toString(),
                            TimeFunctions.TimeConvert.timeToString(min, sec),
                            viewModel.game.getMoves());

                    repo.insert(score);

                } else {
                    outcome = new GameOutcome(Outcome.WIN, 0,
                            timeLimit, viewModel.game.getMoves());
                }
                Intent i = new Intent(GameActivity.this, GameResultActivity.class);
                i.putExtra("outcome", outcome);
                startActivity(i);
                this.finish();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (counter != null) {
            counter.pauseTimer();
        }
    }


    private void updatedSelectedCellUI(Pair<Integer, Integer> cell) {
        if (cell != null) {
            BoardView.updateSelectedCellUI(cell.first, cell.second);
            view.invalidate();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (counter != null) {
            counter.pauseTimer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (counter != null) {
            counter = new GameTimeout(counter.getTimeLeft(), counter.getElapsedTime(), gameClock);
            counter.start();
        }
    }

    @Override
    public void onCellTouched(int row, int col) {
        viewModel.game.updateSelectedCell(row, col);
    }


}