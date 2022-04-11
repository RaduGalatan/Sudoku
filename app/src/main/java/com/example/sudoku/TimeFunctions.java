package com.example.sudoku;

public class TimeFunctions {

    public static class TimeConvert {

        public static int millisToMin(long milli) {
            return (int) (milli / 60000) % 60;
        }


        public static int millisToSec(long milli) {
            return (int) (milli / 1000) % 60;
        }
    }
}
