package com.zhuzichu.module_base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.afollestad.appthemeengine.ATE;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 作者: Zzc on 2018-04-03.
 * 版本: v1.0
 */

public class App extends Application {
    private static Context content;

    public static Context getContent() {
        return content;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        content = this;
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        //LOGGER 日志
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
        Logger.i("App创建了");
        setupATE();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void setupATE() {
        if (!ATE.config(this, "light_theme").isConfigured()) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppThemeLight)
                    .coloredNavigationBar(false)
                    .commit();
        }

        if (!ATE.config(this, "dark_theme").isConfigured()) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .coloredNavigationBar(false)
                    .commit();
        }
    }
}
