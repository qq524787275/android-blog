package com.zhuzichu.module_base.react.checkbox;


import android.widget.CheckBox;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * 作者: Zzc on 2018-07-17.
 * 版本: v1.0
 */

public class ReactCheckBoxManager extends SimpleViewManager<CheckBox> {
    public static final String REACT_CLSAS = "CustomCheckBox";

    @Override
    public String getName() {
        return REACT_CLSAS;
    }

    @Override
    protected CheckBox createViewInstance(ThemedReactContext reactContext) {
        return new CheckBox(reactContext);
    }

    @ReactProp(name = "switch", defaultBoolean = true)
    public void setSwitch(CheckBox checkBox, boolean enabled) {
        checkBox.setEnabled(enabled);
    }
}
