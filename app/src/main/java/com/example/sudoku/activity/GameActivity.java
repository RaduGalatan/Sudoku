package com.example.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;

import com.example.sudoku.Difficulty;
import com.example.sudoku.R;
import com.example.sudoku.game.Cell;
import com.example.sudoku.game.SudokuGame;
import com.example.sudoku.view.BoardView;
import com.example.sudoku.viewmodel.SudokuViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements BoardView.OnTouchListener {

    private SudokuViewModel viewModel;
    private BoardView view;
    private final List<Button> buttons=new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

            Difficulty difficulty = getIntent().getParcelableExtra("difficulty");

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
                Intent i = new Intent(GameActivity.this,VictoryActivity.class);
                startActivity(i);
                this.finish();
            }
        }
    }

    private void updatedSelectedCellUI(Pair<Integer,Integer> cell) {
        if (cell != null) {
            BoardView.updateSelectedCellUI(cell.first,cell.second);
            view.invalidate();
        }

    }

    @Override
    public void onCellTouched(int row, int col) {
        viewModel.game.updateSelectedCell(row,col);
    }
}