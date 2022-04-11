package com.example.sudoku.outcome;

import android.os.Parcel;
import android.os.Parcelable;

public class GameOutcome implements Parcelable {

    private final Outcome outcome;
    private final long timeElapsed;
    private final long timeLimit;
    private final int moves;

    public Outcome getOutcome() {
        return outcome;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public int getMoves() {
        return moves;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public GameOutcome(Outcome outcome, long timeElapsed, long timeLimit, int moves) {
        this.outcome = outcome;
        this.timeElapsed = timeElapsed;
        this.timeLimit = timeLimit;
        this.moves = moves;
    }

    protected GameOutcome(Parcel in) {
        outcome = Outcome.valueOf(in.readString());
        timeElapsed = in.readLong();
        timeLimit = in.readLong();
        moves = in.readInt();
    }

    public static final Creator<GameOutcome> CREATOR = new Creator<GameOutcome>() {
        @Override
        public GameOutcome createFromParcel(Parcel in) {
            return new GameOutcome(in);
        }

        @Override
        public GameOutcome[] newArray(int size) {
            return new GameOutcome[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(outcome.name());
        parcel.writeLong(timeElapsed);
        parcel.writeLong(timeLimit);
        parcel.writeInt(moves);
    }
}
