package com.zhuzichu.module_base.react.color;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.utils.ColorUtils;

/**
 * 作者: Zzc on 2018-07-10.
 * 版本: v1.0
 */
public class ColorModule extends ReactContextBaseJavaModule {

    public ColorModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ColorModule";
    }

    @ReactMethod
    public void getThemePrimaryColor(Promise callback) {
        String color = ColorUtils.toHexa(ATEUtil.getThemePrimaryColor(getReactApplicationContext()));
        callback.resolve(color);
    }

    @ReactMethod
    public void getThemePrimaryColorDark(Promise callback) {
        String color = ColorUtils.toHexa(ATEUtil.getThemePrimaryColorDark(getReactApplicationContext()));
        callback.resolve(color);
    }

    @ReactMethod
    public void getThemeAccentColor(Promise callback) {
        String color = ColorUtils.toHexa(ATEUtil.getThemeAccentColor(getReactApplicationContext()));
        callback.resolve(color);
    }

    @ReactMethod
    public void getThemeTextColorPrimary(Promise callback) {
        String color = ColorUtils.toHexa(ATEUtil.getThemeTextColorPrimary(getReactApplicationContext()));
        callback.resolve(color);
    }

    @ReactMethod
    public void getThemeTextColorSecondly(Promise callback) {
        String color = ColorUtils.toHexa(ATEUtil.getThemeTextColorSecondly(getReactApplicationContext()));
        callback.resolve(color);
    }
}
