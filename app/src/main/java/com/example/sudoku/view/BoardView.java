package com.example.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.sudoku.Font;
import com.example.sudoku.game.Cell;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends View {

    private Font textFont = Font.MEDIUM;

    private Paint thickLinePaint=new Paint(){{
        setStyle(Paint.Style.STROKE);
        setColor(Color.BLACK);
        setStrokeWidth(4F);
    }};
    private Paint thinLinePaint=new Paint(){{
        setStyle(Paint.Style.STROKE);
        setColor(Color.BLACK);
        setStrokeWidth(2F);
    }};

    private int size=9;
    private final int sqrtSize=(int)Math.sqrt(size);
    private float cellSizePixels=0F;

    private static int selectedRow=-1;
    private static int selectedCol=-1;

    private OnTouchListener listener;

    private static List<Cell> cells=new ArrayList<Cell>() ;


    private Paint selectedCellPaint=new Paint(){{
        setStyle(Style.FILL_AND_STROKE);
        setColor(Color.GREEN);
    }};

    private final Paint textPaint=new Paint(){{
        setStyle(Style.FILL_AND_STROKE);
        setColor(Color.BLACK);
        setTextSize(textFont.getSize());
    }};

    private final Paint conflictingCellPaint=new Paint(){{
        setStyle(Style.FILL_AND_STROKE);
        setColor(Color.LTGRAY);
    }};

    private final Paint startingCellTextPaint=new Paint()
    {{
        setStyle(Style.FILL_AND_STROKE);
        setColor(Color.WHITE);
        setTextSize(textFont.getSize());
        setTypeface(Typeface.DEFAULT_BOLD);
    }};

    private final Paint startingCellPaint=new Paint()
    {{
        setStyle(Style.FILL_AND_STROKE);
        setColor(Color.DKGRAY);
    }};

    public BoardView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public void onDraw(Canvas canvas){
        cellSizePixels=(float)(getWidth()/size);
        fillCells(canvas);
        drawLines(canvas);
        drawNumber(canvas, textFont.getSize());
    }

    @Override
    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int sizePixels=Math.min(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(sizePixels,sizePixels);
    }

    private void drawLines(Canvas canvas){
        canvas.drawRect(0F,0F,(float)getWidth(),(float)getHeight(),thickLinePaint);

        for(int i=0;i<size;i++){
            Paint paintToUse;

            if((i+1)%sqrtSize==0){
                paintToUse=thickLinePaint;
            }
            else{
                paintToUse=thinLinePaint;
            }

            canvas.drawLine(
                    (i+1)*cellSizePixels,
                    0F,
                    (i+1)*cellSizePixels,
                    (float)getHeight(),
                    paintToUse
            );

            canvas.drawLine(
                    0F,
                    (i+1)*cellSizePixels,
                    (float)getWidth(),
                    (i+1)*cellSizePixels,
                    paintToUse
            );
        }
    }

    private void fillCells(Canvas canvas){

        for(Cell cell: cells)
        {
            int r=cell.row;
            int c=cell.col;

            if(cell.isStartingCell)
            {
                fillCell(canvas,r,c,startingCellPaint);
            }
            else if (r == selectedRow && c == selectedCol) {
                fillCell(canvas, r, c, selectedCellPaint);
            } else if (r == selectedRow || c == selectedCol) {
                fillCell(canvas, r, c, conflictingCellPaint);
            } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize)
                fillCell(canvas, r, c, conflictingCellPaint);

        }
    }

    private void drawNumber(Canvas canvas,float textSize){
        for(Cell cell: cells) {
            int row = cell.row;
            int col = cell.col;
            String number=Integer.toString(cell.value);

            Paint paintToUse;

            if(cell.isStartingCell)
            {
                paintToUse=startingCellTextPaint;
            }
            else{
                paintToUse=textPaint;
            }

            Rect bounds=new Rect();
            textPaint.getTextBounds(number,0,number.length(),bounds);

         //   float textWidth=textPaint.measureText(number);
          //  float textHeight=bounds.height();

           float desiredSize=textSize/ getResources().getDisplayMetrics().scaledDensity;

          canvas.drawText(number,(col*cellSizePixels)+ cellSizePixels/2-desiredSize/2,
                    (row*cellSizePixels)+ cellSizePixels/2-desiredSize/2,
                    paintToUse);
        }
    }

    public void updateTextSize(Font font){
        this.textFont =font;
        startingCellTextPaint.setTextSize(font.getSize());
        textPaint.setTextSize(font.getSize());
        invalidate();
    }

    public Font getTextFont(){
        return textFont;
    }

    private void fillCell(Canvas canvas,int row,int col,Paint paint){
        canvas.drawRect(col*cellSizePixels,row*cellSizePixels,
                (col+1)*cellSizePixels,(row+1)*cellSizePixels,paint);
    }


    public static void updateSelectedCellUI(int row,int col){

        selectedRow=row;
        selectedCol=col;
    }

    public static void updateCells(List<Cell> cellList){
        cells=cellList;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            handleTouchEvent(event.getX(),event.getY());
            return true;
        }

        return false;
    }

    private void handleTouchEvent(float x,float y){
        int possibleSelectedRow=(int) (y/cellSizePixels);
        int possibleSelectedCol=(int) (x/cellSizePixels);

        listener.onCellTouched(possibleSelectedRow,possibleSelectedCol);
    }

    public void registerListener(OnTouchListener listener){
        this.listener=listener;
    }

    public interface OnTouchListener {
        void onCellTouched(int row, int col);
    }
}
