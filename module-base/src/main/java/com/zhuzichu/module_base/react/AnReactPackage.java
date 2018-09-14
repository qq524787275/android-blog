package com.zhuzichu.module_base.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.zhuzichu.module_base.react.checkbox.ReactCheckBoxManager;
import com.zhuzichu.module_base.react.color.ColorModule;
import com.zhuzichu.module_base.react.likeview.ReactLikeViewManager;
import com.zhuzichu.module_base.react.loading.LoadingModule;
import com.zhuzichu.module_base.react.toast.ToastModule;

import org.devio.rn.splashscreen.SplashScreenModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Zzc on 2018-07-08.
 * 版本: v1.0
 */
public class AnReactPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new LoadingModule(reactContext));
        modules.add(new ToastModule(reactContext));
        modules.add(new ColorModule(reactContext));
        modules.add(new SplashScreenModule(reactContext));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> managers = new ArrayList<>();
        managers.add(new ReactCheckBoxManager());
        managers.add(new ReactLikeViewManager());
        return managers;
    }
}
