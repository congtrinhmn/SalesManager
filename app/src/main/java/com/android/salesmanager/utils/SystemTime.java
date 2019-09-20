package com.android.salesmanager.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SystemTime {
    private static SystemTime systemTime;

    private SystemTime() {
    }

    public static SystemTime getInstance() {
        if (systemTime == null) {
            systemTime = new SystemTime();
        }
        return systemTime;
    }

    public long getTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public long getTime(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(dateTime));
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String parseTime(long l) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(l));
    }

    public long[] getLimDayNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long f = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long l = calendar.getTimeInMillis();
        Log.d("TTT", "getLimDayNow: " + getInstance().parseTime(f) + " - " + getInstance().parseTime(calendar.getTimeInMillis()));
        return new long[]{f, l};
    }

    public long[] getLimWeekNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long f = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long l = calendar.getTimeInMillis();
        Log.d("TTT", "getLimWeekNow: " + getInstance().parseTime(f) + " - " + getInstance().parseTime(calendar.getTimeInMillis()));
        return new long[]{f, l};
    }

    public long[] getLimMonthNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long f = calendar.getTimeInMillis();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long l = calendar.getTimeInMillis();
        Log.d("TTT", "getLimMonthNow: " + getInstance().parseTime(f) + " - " + getInstance().parseTime(calendar.getTimeInMillis()));
        return new long[]{f, l};
    }

    public long[] getLimYearNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long f = calendar.getTimeInMillis();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long l = calendar.getTimeInMillis();
        Log.d("TTT", "getLimMonthNow: " + getInstance().parseTime(f) + " - " + getInstance().parseTime(calendar.getTimeInMillis()));
        return new long[]{f, l};
    }
}
