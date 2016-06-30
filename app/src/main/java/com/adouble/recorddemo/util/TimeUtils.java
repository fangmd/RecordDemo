package com.adouble.recorddemo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    /**
     * 把毫秒，转换成 ：。如：03:10
     *
     * @param millisencond
     * @return
     */
    public static String convertMilliSecondToMinute2(int millisencond) {
        int oneHour = 1000 * 60 * 60;
        int oneMinute = 1000 * 60;

        int hours = millisencond / oneHour;
        int minutes = (millisencond - hours * oneHour) / oneMinute;
        int second = (millisencond - minutes * oneMinute) / 1000;

        return getNum(hours) + ":" + getNum(minutes) + ":" + getNum(second);
    }

    /**
     * 把秒，转换成 ：。如：03:10
     *
     * @param seconds
     * @return
     */
    public static String convertSecondToMinute2(int seconds) {
        int oneMinute = 60;
        int minutes = seconds / oneMinute;
        int mSecond = seconds - minutes * oneMinute;
        return getNum(minutes) + ":" + getNum(mSecond);
    }

    public static String getNum(int num) {
        if (num >= 10) {
            return "" + num;
        } else {
            return "0" + num;
        }
    }

    public static String getTimestamp() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return String.valueOf(ts.getTime());
    }

    public static String getTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String time = sdf.format(new Date(l));
        return time;
    }
}
