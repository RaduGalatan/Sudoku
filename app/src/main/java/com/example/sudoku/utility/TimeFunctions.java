package com.example.sudoku.utility;

import android.text.TextUtils;

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

        private static Long minToMilli(String time) {
            if (!TextUtils.isDigitsOnly(time)) return 0L;
            return Long.parseLong(time) * 60000;
        }

        private static Long secToMilli(String time) {
            if (!TextUtils.isDigitsOnly(time)) return 0L;
            return Long.parseLong(time) * 1000;
        }

        public static Long timeToMilli(String min, String sec) {

            return minToMilli(min) + secToMilli(sec);
        }
    }
}
