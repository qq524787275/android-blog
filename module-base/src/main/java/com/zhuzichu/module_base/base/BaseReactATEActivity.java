package com.zhuzichu.module_base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.afollestad.appthemeengine.ATEActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.widget.autolayout.AutoFrameLayout;
import com.zhuzichu.module_base.widget.autolayout.AutoLinearLayout;
import com.zhuzichu.module_base.widget.autolayout.AutoRelativeLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Created by Zzc on 2017/11/17/017.
 */

public abstract class BaseReactATEActivity extends ATEActivity implements DefaultHardwareBackBtnHandler,PermissionAwareActivity {
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    private final ReactActivityDelegate mReactDelegate;

    private Method method_onCreate;
    private Method method_onPause;
    private Method method_onResume;
    private Method method_onDestroy;
    private Method method_getReactNativeHost;
    private Method method_loadApp;

    protected BaseReactATEActivity() {
        mReactDelegate = createReactActivityDelegate();
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    protected @Nullable
    String getMainComponentName() {
        return null;
    }

    /**
     * Called at construction time, override if you have a custom delegate implementation.
     */
    protected ReactActivityDelegate createReactActivityDelegate() {
        ReactActivityDelegate instance=null;
        Class<ReactActivityDelegate> clazz = ReactActivityDelegate.class;
        try {
            Constructor<ReactActivityDelegate> constructor = clazz.getConstructor(FragmentActivity.class, String.class);
            method_onCreate = clazz.getDeclaredMethod("onCreate",Bundle.class);
            method_onPause = clazz.getDeclaredMethod("onPause");
            method_onResume = clazz.getDeclaredMethod("onResume");
            method_onDestroy = clazz.getDeclaredMethod("onDestroy");
            method_getReactNativeHost = clazz.getDeclaredMethod("getReactNativeHost");
            method_loadApp = clazz.getDeclaredMethod("loadApp",String.class);
            method_onCreate.setAccessible(true);
            method_onPause.setAccessible(true);
            method_onResume.setAccessible(true);
            method_onDestroy.setAccessible(true);
            method_getReactNativeHost.setAccessible(true);
            method_loadApp.setAccessible(true);
            instance = constructor.newInstance(this, getMainComponentName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mReactDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mReactDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!mReactDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }


    @Override
    public void requestPermissions(
            String[] permissions,
            int requestCode,
            PermissionListener listener) {
        mReactDelegate.requestPermissions(permissions, requestCode, listener);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        mReactDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected final ReactNativeHost getReactNativeHost() {
        try {
            return (ReactNativeHost) method_getReactNativeHost.invoke(mReactDelegate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected final ReactInstanceManager getReactInstanceManager() {
        return mReactDelegate.getReactInstanceManager();
    }

    protected final void loadApp(String appKey) {
        try {
            method_loadApp.invoke(mReactDelegate,appKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT))
        {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT))
        {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT))
        {
            view = new AutoRelativeLayout(context, attrs);
        }

//        if(name.equals(LAYOUT_CONSTRAINTLAYOUT))
//        {
//            view = new AutoConstraintLayout(context, attrs);
//        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    @Override
    public String getATEKey() {
       return ATEUtil.getATEKey(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            method_onCreate.invoke(mReactDelegate,savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            method_onDestroy.invoke(mReactDelegate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            method_onPause.invoke(mReactDelegate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            method_onResume.invoke(mReactDelegate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    final public void onBackPressed() {
        if (!mReactDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
