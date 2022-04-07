package com.example.sudoku.gameTime;

import android.os.CountDownTimer;

public class GameTimeout {
    private long elapsedTime;
    private long timeLimit;
    private static long timeLeft;
    private CountDownTimer timer;

    public GameTimeout(long time_limit, long time_elapsed, GameClock clock) {
        this.timeLimit = time_limit;
        this.elapsedTime = time_elapsed;
        timeLeft = timeLimit;
        timer = new CountDownTimer(timeLimit, 1000) {

            public void onTick(long millisUntilFinished) {
                elapsedTime += timeLeft - millisUntilFinished + 1;
                timeLeft = millisUntilFinished;

                int min = (int) (millisUntilFinished / 60000) % 60;
                int sec = (int) (millisUntilFinished / 1000) % 60;
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

    public long getTimeLimit() {
        return timeLimit;
    }

    public void pauseTimer() {
        timer.cancel();
    }

    public void start() {
        timer.start();
    }

    public GameTimeout resume(long elapsed_time, GameClock clock) {
        return new GameTimeout(timeLeft, elapsed_time, clock);
    }

}
