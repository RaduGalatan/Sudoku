package com.example.sudoku.utility;

import com.example.sudoku.difficulty.Difficulty;
import com.example.sudoku.gameTime.GameTimeout;
import com.example.sudoku.view.BoardView;
import com.example.sudoku.viewmodel.SudokuViewModel;

public class GameState {
    private static GameState gameState;
    private Difficulty difficulty;
    private SudokuViewModel viewModel;
    private GameTimeout counter;
    private BoardView view;


    public Difficulty getDifficulty() {
        return difficulty;
    }

    public SudokuViewModel getViewModel() {
        return viewModel;
    }

    private GameState() {
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void newGameState() {
        gameState = null;
    }

    public static void createGameState() {
        gameState = new GameState();
    }

    public GameTimeout getCounter() {
        return counter;
    }

    public BoardView getView() {
        return view;
    }

    public void setGameData(Difficulty difficulty, GameTimeout counter, BoardView view, SudokuViewModel viewModel) {
        this.difficulty = difficulty;
        this.counter = counter;
        this.view = view;
        this.viewModel = viewModel;
    }

}
