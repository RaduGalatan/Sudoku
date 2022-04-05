package com.example.sudoku.difficulty;

import android.os.Parcel;
import android.os.Parcelable;

public class Difficulty implements Parcelable {

    private final DifficultyLevel difficultyLevel;

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public Difficulty(DifficultyLevel difficultyLevel){
        this.difficultyLevel=difficultyLevel;

    }

    protected Difficulty(Parcel in) {
        difficultyLevel = DifficultyLevel.valueOf(in.readString());
    }

    public static final Creator<Difficulty> CREATOR = new Creator<Difficulty>() {
        @Override
        public Difficulty createFromParcel(Parcel in) {
            return new Difficulty(in);
        }

        @Override
        public Difficulty[] newArray(int size) {
            return new Difficulty[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(difficultyLevel.name());
    }

}



