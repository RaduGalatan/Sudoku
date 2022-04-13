package com.example.sudoku.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1)
public abstract class SudokuDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();

    private static SudokuDatabase INSTANCE;

    public static SudokuDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), SudokuDatabase.class,
                            "SudokuDatabase").fallbackToDestructiveMigration().build();


            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
