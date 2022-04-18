package com.example.sudoku.database;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScoreRepository {
    private final ScoreDao dao;

    public ScoreRepository(Context context) {
        SudokuDatabase db = SudokuDatabase.getAppDatabase(context);
        dao = db.scoreDao();

    }


    public List<Score> getScores() {
        try {
            return new ReadScoreAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void insert(Score score) {
        new CreateAsyncTask(dao).execute(score);
    }


    private static class CreateAsyncTask extends AsyncTask<Score, Void, Void> {
        private final ScoreDao mAsyncTaskDao;

        CreateAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Score... score) {
            mAsyncTaskDao.insert(score[0]);
            return null;

        }
    }

    private static class ReadScoreAsyncTask extends AsyncTask<Void, Void, List<Score>> {
        private final ScoreDao mAsyncTaskDao;

        ReadScoreAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Score> doInBackground(Void... list) {
            return mAsyncTaskDao.getScores();
        }
    }

}
