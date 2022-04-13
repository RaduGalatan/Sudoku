package com.example.sudoku;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TimeFunctions {

    public static class TimeConvert {

        public static int millisToMin(long milli) {
            return (int) (milli / 60000) % 60;
        }


        public static int millisToSec(long milli) {
            return (int) (milli / 1000) % 60;
        }

        public static String timeToString(int min, int sec) {
            NumberFormat f = new DecimalFormat("00");
            return String.format("%s:%s", f.format(min), f.format(sec));
        }
    }
}
