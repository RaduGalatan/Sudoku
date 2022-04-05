package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import com.example.sudoku.difficulty.Difficulty;
import com.example.sudoku.difficulty.DifficultyLevel;
import com.example.sudoku.R;
import com.example.sudoku.game.Cell;
import com.example.sudoku.game.SudokuGame;
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
    private final List<Button> buttons= new ArrayList<>();

    private CountDownTimer timer=null;
    private long elapsed_time;
    private long totalTimeLimit;
    private long timeLeft; //how much time is left till the game's over; used for preserving the counter

    private GameOutcome outcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

            Difficulty difficulty = getIntent().getParcelableExtra("difficulty");

            TextView difficultyName=findViewById(R.id.difficulty);
            difficultyName.setText(difficulty.getDifficultyLevel().toString());

            view = (BoardView) findViewById(R.id.boardView);
            view.registerListener(this);

            viewModel = new ViewModelProvider(this).get(SudokuViewModel.class);

            viewModel.game=new SudokuGame(difficulty);
            viewModel.game.selectedCellLiveData.observe(this, this::updatedSelectedCellUI
            );

            viewModel.game.cellsLiveData.observe(this, this::updateCells
            );

            initialiseButtons();
            setButtonsListeners();

            if(difficulty.getDifficultyLevel()!= DifficultyLevel.EASY) {
                totalTimeLimit =difficulty.getDifficultyLevel().getTime();
                timeLeft=totalTimeLimit;
                setTimer();
            }
    }

    private void initialiseButtons(){
        buttons.add((Button)findViewById(R.id.buttonOne));
        buttons.add((Button)findViewById(R.id.buttonTwo));
        buttons.add((Button)findViewById(R.id.buttonThree));
        buttons.add((Button)findViewById(R.id.buttonFour));
        buttons.add((Button)findViewById(R.id.buttonFive));
        buttons.add((Button)findViewById(R.id.buttonSix));
        buttons.add((Button)findViewById(R.id.buttonSeven));
        buttons.add((Button)findViewById(R.id.buttonEight));
        buttons.add((Button)findViewById(R.id.buttonNine));
    }

    private void setButtonsListeners(){
        for(int i=0;i<buttons.size();i++){
            int i2 = i;
            buttons.get(i).setOnClickListener(view -> viewModel.game.handleInput(i2 +1));
        }
    }

    private void updateCells(List<Cell> cells){
        if(cells!=null){
            BoardView.updateCells(cells);
            view.invalidate();
            if(viewModel.game.winCondition()){

                outcome=new GameOutcome(Outcome.WIN,elapsed_time,totalTimeLimit);

                Intent i = new Intent(GameActivity.this,GameResultActivity.class);
                i.putExtra("outcome",outcome);
                startActivity(i);
                this.finish();
            }
        }
    }

    void setTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {

            public void onTick(long millisUntilFinished) {
                TextView view=findViewById(R.id.timer);

                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                view.setText("Time left: "+f.format(min) + ":" + f.format(sec));

                elapsed_time+= timeLeft-millisUntilFinished+1;
                timeLeft=millisUntilFinished;
            }

            public void onFinish() {
                outcome=new GameOutcome(Outcome.LOSE,0,0);
                Intent i = new Intent(GameActivity.this,GameResultActivity.class);
                i.putExtra("outcome",outcome);
                startActivity(i);
                finish();
            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    void cancelTimer() {
        if(timer!=null)
            timer.cancel();
    }

    private void updatedSelectedCellUI(Pair<Integer,Integer> cell) {
        if (cell != null) {
            BoardView.updateSelectedCellUI(cell.first,cell.second);
            view.invalidate();
        }

    }

    @Override
    public void onPause(){
        cancelTimer();
        super.onPause();
    }


    @Override
    public void onResume(){
        super.onResume();

        if(totalTimeLimit!=0) {
            setTimer();
            timer.start();
        }
    }

    @Override
    public void onCellTouched(int row, int col) {
        viewModel.game.updateSelectedCell(row,col);
    }
}