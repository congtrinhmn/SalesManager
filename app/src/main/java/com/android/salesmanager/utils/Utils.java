package com.android.salesmanager.utils;

import com.android.salesmanager.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String replaceSpecialCharacter(String s) {
        try {
            s = new String(Normalizer.normalize(s, Normalizer.Form.NFD).getBytes("UTF-8"), "US-ASCII").replace("\ufffd", BuildConfig.FLAVOR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static Integer tryParseInt(String s) {
        try {
            return Integer.valueOf(Integer.parseInt(s.replace(",", BuildConfig.FLAVOR)));
        } catch (NumberFormatException e) {
            return null;
        }
    }
     public static Integer tryParseInt(String s, int defaultValue) {
        try {
            return Integer.valueOf(Integer.parseInt(s.replace(",", BuildConfig.FLAVOR)));
        } catch (NumberFormatException e) {
            return Integer.valueOf(defaultValue);
        }
    }

    public static String numberFormat(int i) {
        return new DecimalFormat("###,###,###,###").format((long) i);
    }

    public static Double tryParseDouble(String s) {
        try {
            return Double.valueOf(Double.parseDouble(s.replace(",", BuildConfig.FLAVOR)));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double tryParseDouble(String s, double defaultValue) {
        try {
            return Double.valueOf(Double.parseDouble(s.replace(",", BuildConfig.FLAVOR)));
        } catch (NumberFormatException e) {
            return Double.valueOf(defaultValue);
        }
    }

    public static String numberFormat(double d) {
        return NumberFormat.getNumberInstance(Locale.US).format(d);
    }
}
