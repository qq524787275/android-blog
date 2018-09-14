package com.zhuzichu.module_base.react.toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * 作者: Zzc on 2018-07-09.
 * 版本: v1.0
 */
public class ToastModule extends ReactContextBaseJavaModule{

    public ToastModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ToastModule";
    }

    @ReactMethod
    public void success(String msg){
        TastyToast.makeText(getReactApplicationContext(),msg,TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
    }
    @ReactMethod
    public void info(String msg){
        TastyToast.makeText(getReactApplicationContext(),msg,TastyToast.LENGTH_SHORT,TastyToast.INFO);
    }
    @ReactMethod
    public void error(String msg){
        TastyToast.makeText(getReactApplicationContext(),msg,TastyToast.LENGTH_SHORT,TastyToast.ERROR);
    }
    @ReactMethod
    public void warning(String msg){
        TastyToast.makeText(getReactApplicationContext(),msg,TastyToast.LENGTH_SHORT,TastyToast.WARNING);
    }
}
