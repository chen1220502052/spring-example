package org.example.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        }
    };

    public static String getTimeStr(long time){
        return dateFormat.get().format(new Date(time));
    }

    public static String getCurrentTimeStr(){
        return dateFormat.get().format(new Date(System.currentTimeMillis()));
    }
}
