package com.zhuzichu.module_base.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.afollestad.appthemeengine.Config;

/**
 * 作者: Zzc on 2018-06-19.
 * 版本: v1.0
 */
public class ATEUtil {

    public static String getATEKey(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("dark_theme", false) ?
                "dark_theme" : "light_theme";
    }

    public static int getThemePrimaryColor(Context context) {
        return Config.primaryColor(context, getATEKey(context));
    }

    public static int getThemePrimaryColorDark(Context context) {
        return Config.primaryColorDark(context, getATEKey(context));
    }

    public static int getThemeAccentColor(Context context) {
        return Config.accentColor(context, getATEKey(context));
    }

    public static int getThemeTextColorPrimary(Context context) {
        TypedValue textColorPrimary = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColorPrimary, textColorPrimary, true);
        return context.getResources().getColor(textColorPrimary.resourceId);
    }

    public static int getThemeTextColorSecondly(Context context) {
        TypedValue textColorSecondly = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColorSecondary, textColorSecondly, true);
        return context.getResources().getColor(textColorSecondly.resourceId);
    }

    public static int getToolbarTitleColor(Context context, Toolbar toolbar){
        return Config.getToolbarTitleColor(context,toolbar ,getATEKey(context));
    }
}
