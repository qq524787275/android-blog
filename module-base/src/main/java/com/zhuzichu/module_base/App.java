package com.zhuzichu.module_base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.afollestad.appthemeengine.ATE;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhuzichu.module_base.react.AnReactPackage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * 作者: Zzc on 2018-04-03.
 * 版本: v1.0
 */

public class App extends Application implements ReactApplication {
    private static Context content;
    private static App mInstance;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Context getContent() {
        return content;
    }

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        content = getApplicationContext();
        mInstance = this;
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
        Logger.i("App创建了:"+ Build.MANUFACTURER);
        setupATE();
        CrashReport.initCrashReport(getApplicationContext(), "04d7ee0826", false);

        OkGo.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @SuppressLint("ResourceType")
    private void setupATE() {
        if (!ATE.config(this, "light_theme").isConfigured(4)) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppThemeLight)
                    .coloredNavigationBar(false)
                    .primaryColorRes(R.color.colorPrimaryLightTheme)
                    .primaryColorDarkRes(R.color.colorPrimaryDarkLightTheme)
                    .accentColorRes(R.color.colorAccentLightTheme)
                    .commit();
        }

        if (!ATE.config(this, "dark_theme").isConfigured(4)) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .coloredNavigationBar(false)
                    .primaryColorRes(R.color.colorPrimaryDarkTheme)
                    .primaryColorDarkRes(R.color.colorPrimaryDarkDarkTheme)
                    .accentColorRes(R.color.colorAccentDarkTheme)
                    .commit();
        }
    }


    //初始化ReactNativeHost----------------------------分割线------------------------------------
    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    public ReactInstanceManager getReactInstanceManager() {
        return getReactNativeHost().getReactInstanceManager();
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return false;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.asList(
                    new MainReactPackage(),
                    new AnReactPackage()
            );
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            String path = getContent().getExternalFilesDir(null).getAbsolutePath() + File.separator + "index.android.bundle";
            File file = new File(path);
            if (file != null && file.exists()) {
                return path;
            } else {
                return super.getJSBundleFile();
            }
        }


        @Override
        protected String getJSMainModuleName() {
            return "App";
        }
    };
    //-------------------------------分割线--------------------------------------------------------
}
