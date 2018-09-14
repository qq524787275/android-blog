package com.zhuzichu.module_base.react.preloadreact;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.react.ReactRootView;
import com.zhuzichu.module_base.App;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.utils.ColorUtils;

import java.util.Map;

public class ReactNativePreLoader {

    @SuppressLint("NewApi")
    private static final Map<String, ReactRootView> CACHE = new ArrayMap<>();

    /**
     * 初始化ReactRootView，并添加到缓存
     *
     * @param activity
     * @param componentName
     */
    public static void preLoad(Activity activity, String componentName) {

        if (CACHE.get(componentName) != null) {
            return;
        }
        //d
        initIndexAndroidJs();
        // 1.创建ReactRootView
        ReactRootView rootView = new ReactRootView(activity);

        rootView.startReactApplication(
                App.getInstance().getReactInstanceManager(),
                componentName,
                createBundle());

        // 2.添加到缓存
        CACHE.put(componentName, rootView);
    }

    private static void initIndexAndroidJs() {

    }

    private static Bundle createBundle() {
        Bundle bundle = new Bundle();
        String hexa = ColorUtils.toHexa(ATEUtil.getThemePrimaryColor(App.getInstance()));
        bundle.putString("primary", hexa);
        return bundle;
    }

    /**
     * 获取ReactRootView
     *
     * @param componentName
     * @return
     */
    public static ReactRootView getReactRootView(String componentName) {
        return CACHE.get(componentName);
    }

    /**
     * 从当前界面移除 ReactRootView
     *
     * @param component
     */
    public static void deatchView(String component) {
        try {
            ReactRootView rootView = getReactRootView(component);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
                CACHE.remove(component);
            }
        } catch (Throwable e) {
            Log.e("ReactNativePreLoader", e.getMessage());
        }
    }

    public static void removeAll() {
        CACHE.clear();
    }
}
