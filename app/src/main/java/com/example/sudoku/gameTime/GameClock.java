package com.example.sudoku.gameTime;

public interface GameClock {
    void onUpdate(int sec, int min, long elapsedTime);

    void onStop(long elapsedTime);
}
