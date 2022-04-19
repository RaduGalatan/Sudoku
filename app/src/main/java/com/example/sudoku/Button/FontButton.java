package com.example.sudoku.Button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.sudoku.R;
import com.example.sudoku.utility.Font;
import com.example.sudoku.view.BoardView;

import java.util.List;

public class FontButton extends androidx.appcompat.widget.AppCompatButton {

    private int index = 0;

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateTextSize(BoardView view, List<Font> fonts) {
        Button fontButton = findViewById(R.id.buttonFont);

        index = fonts.indexOf(view.getTextFont()) + 1;

        fontButton.setOnClickListener(v -> {
            view.updateTextSize(fonts.get(index % fonts.size()));
            index++;
        });
    }
}
