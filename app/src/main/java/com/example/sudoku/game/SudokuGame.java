package com.example.sudoku.game;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.sudoku.difficulty.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class SudokuGame {

    public final MutableLiveData<Pair<Integer, Integer>> selectedCellLiveData = new MutableLiveData<>();
    public final MutableLiveData<List<Cell>> cellsLiveData = new MutableLiveData<>();

    private int selectedRow = -1;
    private int selectedCol = -1;
    private int moves = 0;
    private static int startingEmptyCells;

    public static int getStartingEmptyCells() {
        return startingEmptyCells;
    }

    public int getMoves() {
        return moves;
    }

    final Board board;

    private final int sizeRoot;

    public SudokuGame(Difficulty difficulty) {
        int size = 9;
        ArrayList<Cell> cells = new ArrayList<>();

        for (int i = 0; i < size * size; i++) {
            Cell cell = new Cell(i / 9, i % 9, "");
            cells.add(cell);
        }

        board = new Board(size, cells);
        sizeRoot = (int) Math.sqrt(board.size);
        fillTable(difficulty);

        selectedCellLiveData.postValue(new Pair<>(selectedRow, selectedCol));
        cellsLiveData.postValue(board.cells);
    }

    public void updateSelectedCell(int row, int col) {
        if (!board.getCell(row, col).isStartingCell) {
            selectedRow = row;
            selectedCol = col;
            selectedCellLiveData.postValue(new Pair<>(selectedRow, selectedCol));
        }
    }

    public void handleInput(String number) {
        if (selectedCol == -1 || selectedRow == -1) return;
        if (board.getCell(selectedRow, selectedCol).isStartingCell) return;

        moves++;
        board.getCell(selectedRow, selectedCol).value = number;
        cellsLiveData.postValue(board.cells);

    }

    public void fillTable(Difficulty difficulty) {
        fillDiagonal();
        fillTheRest(0, sizeRoot);

        startingEmptyCells =difficulty.getDifficultyLevel().getEmptyCells();
        removeDigits(startingEmptyCells);

    }


    void fillDiagonal() {

        for (int i = 0; i < board.size; i = i + sizeRoot)
            fillBox(i, i);
    }

    boolean checkIfSafe(int i, int j, String num) {
        return (unusedInRow(i, num) &&
                unusedInCol(j, num) &&
                unusedInBox(i - i % sizeRoot, j - j % sizeRoot, num));
    }


    boolean fillTheRest(int r, int c) {
        if (r < board.size - 1 && c >= board.size) {
            r = r + 1;
            c = 0;
        }
        if (r >= board.size && c >= board.size)
            return true;

        if (r < sizeRoot) {
            if (c < sizeRoot)
                c = sizeRoot;
        } else if (r < board.size - sizeRoot) {
            if (c == (int) (r / sizeRoot) * sizeRoot)
                c = c + sizeRoot;
        } else {
            if (c == board.size - sizeRoot) {
                r = r + 1;
                c = 0;
                if (r >= board.size)
                    return true;
            }
        }

        for (int num = 1; num <= board.size; num++) {
            if (checkIfSafe(r, c, String.valueOf(num))) {
                board.getCell(r, c).value =String.valueOf(num);
                board.getCell(r, c).isStartingCell = true;
                if (fillTheRest(r, c + 1))
                    return true;

                board.getCell(r, c).value = "";
            }
        }
        return false;
    }

    public void removeDigits(int nr) {
        int count = nr;
        while (count != 0) {
            int cellId = (int) Math.floor((Math.random() * (board.size * board.size)));

            int i = (cellId / board.size);
            int j = cellId % 9;
            if (j != 0)
                j = j - 1;

            if (!board.getCell(i, j).value.isEmpty()) {
                count--;
                board.getCell(i, j).value = "";
                board.getCell(i, j).isStartingCell = false;
            }
        }
    }

    boolean unusedInRow(int r, String num) {
        for (int c = 0; c < board.size; c++)
            if (board.getCell(r, c).value.equals(num))
                return false;
        return true;
    }

    boolean unusedInCol(int c, String num) {
        for (int r = 0; r < board.size; r++)
            if (board.getCell(r, c).value.equals(num))
                return false;
        return true;
    }

    boolean unusedInBox(int row, int col, String num) {
        for (int r = 0; r < sizeRoot; r++)
            for (int c = 0; c < sizeRoot; c++)
                if (board.getCell(row + r, col + c).value.equals(num))
                    return false;

        return true;
    }

    void fillBox(int row, int col) {
        String num;

        for (int i = 0; i < sizeRoot; i++) {
            for (int j = 0; j < sizeRoot; j++) {
                do {
                    num =String.valueOf( (int) Math.floor((Math.random() * board.size + 1)));
                }
                while (!unusedInBox(row, col, num));

                board.getCell(row + i, col + j).value = num;
                board.getCell(row + i, col + j).isStartingCell = true;
            }
        }
    }

    boolean rowCondition(int row) {

        List<String> uniqueValues = new ArrayList<>();
        for (int col = 0; col < board.size; col++) {

            String nr = board.getCell(row, col).value;
            if (nr.isEmpty()) return false;
            if (uniqueValues.contains(nr)) {
                return false;
            }
            uniqueValues.add(nr);
        }
        return true;
    }

    boolean columnCondition(int col) {

        List<String> uniqueValues = new ArrayList<>();
        for (int row = 0; row < board.size; row++) {

            String nr = board.getCell(row, col).value;
            if (nr.isEmpty()) return false;

            if (uniqueValues.contains(nr)) {
                return false;
            }
            uniqueValues.add(nr);
        }
        return true;
    }

    boolean allRowsCondition() {
        for (int r = 0; r < board.size; r++) {
            if (!rowCondition(r)) return false;
        }
        return true;
    }

    boolean allColumnsCondition() {
        for (int c = 0; c < board.size; c++) {
            if (!columnCondition(c)) return false;
        }

        return true;
    }


    public boolean winCondition() {

        return allBoxesCondition() && allRowsCondition() && allColumnsCondition();
    }


    boolean allBoxesCondition() {

        for (int row = 0; row < board.size - 2; row += 3) {

            for (int col = 0; col < board.size - 2; col += 3) {

                List<String> uniqueValues = new ArrayList<>();

                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {

                        int X = row + r;

                        int Y = col + c;

                        String Z = board.getCell(X, Y).value;

                        if (Z.isEmpty()) return false;

                        if (uniqueValues.contains(Z)) {
                            return false;
                        }
                        uniqueValues.add(Z);
                    }
                }
            }
        }
        return true;
    }
}
