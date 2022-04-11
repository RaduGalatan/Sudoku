package com.example.sudoku.gameTime;

import android.os.CountDownTimer;

import com.example.sudoku.TimeFunctions.TimeConvert;

public class GameTimeout {
    private long elapsedTime;
    private final long timeLimit;
    private long timeLeft;
    private final CountDownTimer timer;

    public GameTimeout(long time_limit, long time_elapsed, GameClock clock) {
        this.timeLimit = time_limit;
        this.elapsedTime = time_elapsed;
        timeLeft = timeLimit;
        timer = new CountDownTimer(timeLimit, 1000) {

            public void onTick(long millisUntilFinished) {
                elapsedTime += timeLeft - millisUntilFinished + 1;
                timeLeft = millisUntilFinished;

                int min = TimeConvert.millisToMin(millisUntilFinished);
                int sec = TimeConvert.millisToSec(millisUntilFinished);
                clock.onUpdate(sec, min, elapsedTime);
            }

            public void onFinish() {
                timer.cancel();
                clock.onStop(0);
            }
        };
    }


    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void pauseTimer() {
        timer.cancel();
    }

    public void start() {
        timer.start();
    }

}
