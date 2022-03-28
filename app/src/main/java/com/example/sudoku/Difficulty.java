package com.example.sudoku;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Difficulty implements Parcelable {

    private String difficultyLevel;
    private int emptyCells;

    public Difficulty(String difficultyLevel){
        this.difficultyLevel=difficultyLevel;

        switch (difficultyLevel){
            case "Easy":
                emptyCells=5;
                break;

            case "Medium":
                emptyCells=10;
                break;

            case "Hard":
                emptyCells=30;
                break;

            default:
                emptyCells=2;
                break;
        }
    }

    protected Difficulty(Parcel in) {
        difficultyLevel = in.readString();
        emptyCells=in.readInt();
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
            parcel.writeString(difficultyLevel);
            parcel.writeInt(emptyCells);
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel=difficultyLevel;
    }

    public int getEmptyCells(){
        return emptyCells;
    }

    public void setEmptyCells(int emptyCells){
        this.emptyCells=emptyCells;
    }
}
