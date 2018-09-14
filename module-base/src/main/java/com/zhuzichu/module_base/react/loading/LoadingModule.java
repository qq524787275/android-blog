package com.zhuzichu.module_base.react.loading;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.zhuzichu.module_base.widget.loading.LatteLoader;

/**
 * 作者: Zzc on 2018-07-08.
 * 版本: v1.0
 */
public class LoadingModule extends ReactContextBaseJavaModule {


    public LoadingModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "LoadingModule";
    }


    @ReactMethod
    public void hide(){
        LatteLoader.hide();
    }

    @ReactMethod
    public void show(){
        LatteLoader.show(getCurrentActivity());
    }
}
