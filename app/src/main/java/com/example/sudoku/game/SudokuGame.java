package com.example.sudoku.game;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class SudokuGame {

    public MutableLiveData<Pair<Integer,Integer>> selectedCellLiveData=new MutableLiveData<>();
    public MutableLiveData<List<Cell>> cellsLiveData =new MutableLiveData<>();

    private int selectedRow=-1;
    private int selectedCol=-1;

    final Board board;

    private final int sizeRoot;

    public SudokuGame(String difficulty) { int size=9;
       ArrayList<Cell> cells = new ArrayList<>();

        for(int i=0;i<size*size;i++){
            Cell cell=new Cell(i/9,i%9,0);
            cells.add(cell);
        }

        board=new Board(size, cells);
        sizeRoot=(int)Math.sqrt(board.size);
        fillTable(difficulty);

        selectedCellLiveData.postValue(new Pair<>(selectedRow, selectedCol));
        cellsLiveData.postValue(board.cells);
    }

    public void updateSelectedCell(int row,int col)
    {
        if(!board.getCell(row,col).isStartingCell) {
            selectedRow = row;
            selectedCol = col;
            selectedCellLiveData.postValue(new Pair<>(selectedRow, selectedCol));
        }
    }

    public void handleInput(int number){
        if(selectedCol==-1 || selectedRow==-1) return;
        if(board.getCell(selectedRow,selectedCol).isStartingCell) return;

        board.getCell(selectedRow,selectedCol).value=number;
        cellsLiveData.postValue(board.cells);

    }

    public void fillTable(String difficulty){
        fillDiagonal();
        fillTheRest(0,sizeRoot);


        switch(difficulty)
        {
            case "Easy":
                removeDigits(5);
                break;

            case "Medium":
                removeDigits(10);
                break;

            case "Hard":
                removeDigits(30);
                break;

            default:
                removeDigits(2);
                break;

        }
    }


    void fillDiagonal()
    {

        for (int i = 0; i< board.size; i=i+sizeRoot)
            fillBox(i, i);
    }

    boolean checkIfSafe(int i, int j, int num)
    {
        return (unusedInRow(i, num) &&
                unusedInCol(j, num) &&
                unusedInBox(i-i%sizeRoot, j-j%sizeRoot, num));
    }


    boolean fillTheRest(int r, int c)
    {
        if ( r< board.size-1 && c>= board.size)
        {
            r = r + 1;
            c = 0;
        }
        if (r>= board.size && c>= board.size)
            return true;

        if (r < sizeRoot)
        {
            if (c < sizeRoot)
                c = sizeRoot;
        }
        else if (r <  board.size-sizeRoot)
        {
            if (c==(int)(r/sizeRoot)*sizeRoot)
                c =  c + sizeRoot;
        }
        else
        {
            if (c ==  board.size-sizeRoot)
            {
                r = r + 1;
                c = 0;
                if (r>= board.size)
                    return true;
            }
        }

        for (int num = 1; num<= board.size; num++)
        {
            if (checkIfSafe(r, c, num))
            {
                board.getCell(r,c).value= num;
                board.getCell(r,c).isStartingCell=true;
                if (fillTheRest(r, c+1))
                    return true;

                board.getCell(r,c).value = 0;
            }
        }
        return false;
    }

    public void removeDigits(int nr)
    {
        int count =nr;
        while (count != 0)
        {
            int cellId=(int) Math.floor((Math.random()* (board.size* board.size)));

            int i = (cellId/ board.size);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            if (board.getCell(i,j).value != 0)
            {
                count--;
                board.getCell(i,j).value = 0;
                board.getCell(i,j).isStartingCell=false;
            }
        }
    }

    boolean unusedInRow(int r,int num)
    {
        for (int c = 0; c< board.size; c++)
            if (board.getCell(r,c).value == num)
                return false;
        return true;
    }

    boolean unusedInCol(int c,int num)
    {
        for (int r = 0; r< board.size; r++)
            if (board.getCell(r,c).value == num)
                return false;
        return true;
    }

    boolean unusedInBox(int row, int col, int num)
    {
        for (int r = 0; r<sizeRoot; r++)
            for (int c = 0; c<sizeRoot; c++)
                if (board.getCell(row+r,col+c).value==num)
                    return false;

        return true;
    }

    void fillBox(int row,int col)
    {
        int num;

        for (int i=0; i< sizeRoot; i++)
        {
            for (int j=0; j< sizeRoot; j++)
            {
                do
                {
                    num = (int) Math.floor((Math.random()*board.size+1));
                }
                while (!unusedInBox(row, col, num));

                board.getCell(row+i,col+j).value= num;
                board.getCell(row+i,col+j).isStartingCell=true;
            }
        }
    }

    boolean rowCondition(int row){

        List<Integer> uniqueValues=new ArrayList<>();
        for(int col = 0; col < board.size; col++)
        {

            int nr = board.getCell(row,col).value;
            if(nr==0) return false;
            if (uniqueValues.contains(nr))
            {
                return false;
            }
                uniqueValues.add(nr);
        }
        return true;
    }

    boolean columnCondition(int col){

        List<Integer> uniqueValues=new ArrayList<>();
        for(int row = 0; row < board.size; row++)
        {

            int nr = board.getCell(row,col).value;
            if(nr<=0) return false;

            if (uniqueValues.contains(nr))
            {
                return false;
            }
            uniqueValues.add(nr);
        }
        return true;
    }

    boolean allRowsCondition(){
        for(int r=0;r< board.size;r++)
        {
            if(!rowCondition(r)) return false;
        }
        return true;
    }

    boolean allColumnsCondition(){
        for(int c=0;c< board.size;c++)
        {
            if(!columnCondition(c)) return false;
        }

        return true;
    }

    public boolean winCondition(){

        return allBoxesCondition() && allRowsCondition() && allColumnsCondition();
    }


    boolean allBoxesCondition() {

        for (int row = 0; row < board.size - 2; row += 3) {

            for (int col = 0; col < board.size - 2; col += 3) {

                List<Integer> uniqueValues=new ArrayList<>();

                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {

                        int X = row + r;

                        int Y = col + c;

                        int Z = board.getCell(X,Y).value;

                        if(Z==0) return false;

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
