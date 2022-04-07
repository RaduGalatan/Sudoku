package com.example.sudoku.gameTime;

import android.os.CountDownTimer;

public interface GameClock {
    void onUpdate(int sec, int min, long elapsedTime);

    void onStop(long elapsedTime);
}
