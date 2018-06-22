package com.zhuzichu.module_main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;
import com.zhuzichu.module_base.Keys;
import com.zhuzichu.module_base.RouterConstants;
import com.zhuzichu.module_base.base.BaseATEActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_main.fragment.MainFragment;
import com.zhuzichu.module_main.utils.ColorUtil;
import com.zhuzichu.module_service.ILocationManager;
import com.zhuzichu.module_service.manager.LocationManager;

import static com.zhuzichu.module_service.manager.LocationManager.mService;

@Route(path=RouterConstants.MAIN_MAIN)
public class MainActivity extends BaseATEActivity implements ColorChooserDialog.ColorCallback,ATEActivityThemeCustomizer, ServiceConnection {
    private LocationManager.ServiceToken mToken;
    @Override
    public BaseFragment setRootFragment() {
        LocationManager.bindToService(this,this);
        return MainFragment.newInstance();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        Logger.i("onColorSelection:"+selectedColor);

        final Config config = ATE.config(this, getATEKey());
        config.primaryColor(selectedColor);
        config.primaryColorDark(ColorUtil.getStatusBarColor(selectedColor));
        config.accentColor(selectedColor);
        config.commit();
        Config.markChanged(this, "light_theme");
        Intent intent = new Intent(this, this.getClass());
        intent.putExtra(Keys.KEY_SELECT_INDEX, 1);
        startActivity(intent);
        this.finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {
        Logger.i("onColorChooserDismissed");
    }


    @StyleRes
    @Override
    public int getActivityTheme() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", false) ?
                R.style.AppThemeDark : R.style.AppThemeLight;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mToken != null) {
            LocationManager.unbindFromService(mToken);
            mToken = null;
        }
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mService = ILocationManager.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
