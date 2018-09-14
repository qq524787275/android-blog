package com.zhuzichu.module_base.react.likeview;

import android.view.LayoutInflater;
import android.view.View;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.zhuzichu.module_base.R;
import com.zhuzichu.module_base.widget.likeview.RxShineButton;

/**
 * 作者: Zzc on 2018-07-17.
 * 版本: v1.0
 */
public class ReactLikeViewManager extends SimpleViewManager<RxShineButton> {
    public static final String REACT_CLSAS = "LikeView";

    @Override
    public String getName() {
        return REACT_CLSAS;
    }

    @Override
    protected RxShineButton createViewInstance(ThemedReactContext reactContext) {
        RxShineButton rxShineButton = (RxShineButton) LayoutInflater.from(reactContext).inflate(R.layout.view_likebutton, null);

        return rxShineButton;
    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, final RxShineButton rxShineButton) {
        final EventDispatcher dispatcher =
                reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        rxShineButton.setOnCheckStateChangeListener(new RxShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                dispatcher.dispatchEvent(new ReactLikeViewEvent(rxShineButton.getId(), checked));
            }
        });
    }
}
