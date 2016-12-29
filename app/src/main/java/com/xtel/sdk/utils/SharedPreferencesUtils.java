package com.xtel.sdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xtel.basicsample.view.MyApplication;
import com.xtel.sdk.commons.Constants;


/**
 * Created by Vũ Hà Vi on 11/4/2016.
 */

public class SharedPreferencesUtils {
    private static final SharedPreferencesUtils instance = new SharedPreferencesUtils();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesUtils() {
        sharedPreferences = MyApplication.context.getSharedPreferences(Constants.SHARED_USER_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance() {
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private void prepair() {
        editor = sharedPreferences.edit();
    }

    public void putLongValue(String name, long value) {
        if (editor == null)
            prepair();
        editor.putLong(name, value);
        editor.commit();
    }

    public long getLongValue(String name) {
        return sharedPreferences.getLong(name, -1);
    }

    public void putStringValue(String name, String value) {
        if (editor == null)
            prepair();
        editor.putString(name, value);
        editor.commit();
    }

    public String getStringValue(String name) {
        return sharedPreferences.getString(name, null);
    }

    public void putIntValue(String name, int value) {
        if (editor == null)
            prepair();
        editor.putInt(name, value);
        editor.commit();
    }

    public int getIntValue(String name) {
        return sharedPreferences.getInt(name, -1);
    }

    public void putBooleanValue(String name, boolean value) {
        if (editor == null)
            prepair();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public boolean getBooleanValue(String name) {
        return sharedPreferences.getBoolean(name, false);
    }

    public void clearData() {
        if (editor == null)
            prepair();
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        if (editor == null)
            prepair();
        editor.remove(key);
        editor.commit();
    }
}
