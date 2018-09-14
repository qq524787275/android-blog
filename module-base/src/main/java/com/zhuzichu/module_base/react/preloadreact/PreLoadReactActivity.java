package com.zhuzichu.module_base.react.preloadreact;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.afollestad.appthemeengine.ATEActivity;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.zhuzichu.module_base.R;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.widget.autolayout.AutoFrameLayout;
import com.zhuzichu.module_base.widget.autolayout.AutoLinearLayout;
import com.zhuzichu.module_base.widget.autolayout.AutoRelativeLayout;

import org.devio.rn.splashscreen.SplashScreen;

import javax.annotation.Nullable;

public class PreLoadReactActivity extends ATEActivity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    private PreLoadReactDelegate mPreLoadReactDelegate;


    private PreLoadReactDelegate createPreLoadReactDelegate() {
        return new PreLoadReactDelegate(this, getMainComponentName());
    }

    @Override
    public String getATEKey() {
        return ATEUtil.getATEKey(this);
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        if (view != null) return view;
        return super.onCreateView(name, context, attrs);
    }

    /**
     * 子类重写，返回RN对应的界面组件名称
     *
     * @return
     */
    protected @Nullable
    String getMainComponentName() {
        return getIntent().getExtras().getString("component");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreLoadReactDelegate = createPreLoadReactDelegate();
        mPreLoadReactDelegate.onCreate();
        SplashScreen.show(this, R.style.SplashScreenTheme);  // here
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreLoadReactDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPreLoadReactDelegate.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreLoadReactDelegate.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mPreLoadReactDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!mPreLoadReactDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPreLoadReactDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mPreLoadReactDelegate.onRNKeyUp(keyCode) || super.onKeyUp(keyCode, event);
    }

    /**
     * 处理权限授权
     *
     * @param permissions
     * @param requestCode
     * @param listener
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        mPreLoadReactDelegate.requestPermissions(permissions, requestCode, listener);
    }

    /**
     * 授权结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        mPreLoadReactDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}

