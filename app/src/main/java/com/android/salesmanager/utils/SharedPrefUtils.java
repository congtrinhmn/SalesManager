package com.android.salesmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {
    private static SharedPrefUtils instance;
    private SharedPreferences pref;

    private SharedPrefUtils(Context context) {
        this.pref = context.getSharedPreferences("SharedPref", 0);
    }

    public static SharedPrefUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefUtils(context);
        }
        return instance;
    }

    public void set(String keyName, Boolean value) {
        SharedPreferences.Editor editor = this.pref.edit();
        editor.putBoolean(keyName, value.booleanValue());
        editor.commit();
    }

    public void set(String keyName, String value) {
        SharedPreferences.Editor editor = this.pref.edit();
        editor.putString(keyName, value);
        editor.commit();
    }

    public void set(String keyName, int value) {
        SharedPreferences.Editor editor = this.pref.edit();
        editor.putInt(keyName, value);
        editor.commit();
    }

    public void set(String keyName, long value) {
        SharedPreferences.Editor editor = this.pref.edit();
        editor.putLong(keyName, value);
        editor.commit();
    }

    public boolean get(String key, Boolean defaultValue) {
        return this.pref.getBoolean(key, defaultValue.booleanValue());
    }

    public String get(String key, String defaultValue) {
        return this.pref.getString(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        return this.pref.getInt(key, defaultValue);
    }

    public long get(String key, long defaultValue) {
        return this.pref.getLong(key, defaultValue);
    }

    public String getPassword() {
        return this.pref.getString("password", null);
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = this.pref.edit();
        editor.putString("password", password);
        editor.commit();
    }
}
