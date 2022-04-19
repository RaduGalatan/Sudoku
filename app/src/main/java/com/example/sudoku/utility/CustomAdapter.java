package com.example.sudoku.utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sudoku.R;
import com.example.sudoku.database.Score;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<Score> scoreList;

    public CustomAdapter(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Score score = scoreList.get(position);

        holder.getDifficultyView().setText(score.getDifficulty());
        holder.getTimeView().setText(score.getElapsedTime());
        holder.getMovesView().setText(String.valueOf(score.getNumberOfMoves()));

    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView difficulty;
        private final TextView timeElapsed;
        private final TextView moves;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            difficulty = itemView.findViewById(R.id.difficulty_level);
            timeElapsed = itemView.findViewById(R.id.elapsed_time);
            moves = itemView.findViewById(R.id.number_of_move);
        }

        public TextView getDifficultyView() {
            return difficulty;
        }

        public TextView getTimeView() {
            return timeElapsed;
        }

        public TextView getMovesView() {
            return moves;
        }
    }
}
