package com.zhuzichu.module_person.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 作者: Zzc on 2018-06-19.
 * 版本: v1.0
 */
public class PreferencesUtility {
    private static PreferencesUtility sInstance;
    private static volatile SharedPreferences mPreferences;
    public PreferencesUtility(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtility getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (PreferencesUtility.class) {
                if (sInstance == null) {
                    sInstance = new PreferencesUtility(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public void setOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }
}
