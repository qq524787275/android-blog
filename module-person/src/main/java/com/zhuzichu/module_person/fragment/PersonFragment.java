package com.zhuzichu.module_person.fragment;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.prefs.BorderCircleView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sdsmdg.tastytoast.TastyToast;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhuzichu.module_base.Constants;
import com.zhuzichu.module_base.Keys;
import com.zhuzichu.module_base.RouterConstants;
import com.zhuzichu.module_base.base.BaseATEActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.callback.JsonCallback;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.utils.LocationUtils;
import com.zhuzichu.module_person.R;
import com.zhuzichu.module_person.R2;

import butterknife.BindView;


/**
 * 作者: Zzc on 2018-06-15.
 * 版本: v1.0
 */
public class PersonFragment extends BaseFragment implements LocationUtils.OnLocationChangeListener {

    private String mAteKey;
    @BindView(R2.id.theme)
    RelativeLayout mTheme;
    @BindView(R2.id.theme_circle)
    BorderCircleView mCircleView;
    @BindView(R2.id.color)
    TextView mColor;
    @BindView(R2.id.other)
    TextView mOther;
    @BindView(R2.id.location)
    RelativeLayout mLocation;
    @BindView(R2.id.live)
    RelativeLayout mLive;
    @BindView(R2.id.dark)
    RelativeLayout mDark;
    @BindView(R2.id.dark_checkbox)
    CheckBox mDarkCheckBox;
    @BindView(R2.id.location_switch)
    SwitchCompat mLocationSwitch;


    @Override
    public Object setLayout() {
        return R.layout.fragment_person;
    }

    public static PersonFragment newInstance() {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mAteKey = ((BaseATEActivity) _mActivity).getATEKey();
//        initGroupListView();
        ATE.postApply(_mActivity, mAteKey);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mCircleView.setBackgroundColor(Config.primaryColor(getActivity(), mAteKey));
        mCircleView.setBorderColor(Color.BLACK);

        mColor.setTextColor(ATEUtil.getThemePrimaryColor(_mActivity));
        mOther.setTextColor(ATEUtil.getThemePrimaryColor(_mActivity));

        boolean isDark = PreferenceManager.getDefaultSharedPreferences(_mActivity).getBoolean("dark_theme", false);
        mDarkCheckBox.setChecked(isDark);
        mLocationSwitch.setChecked(LocationUtils.isUse());

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTheme.setOnClickListener(view -> clickItemWithTheme());
        mDark.setOnClickListener(view -> clickItemWithDark());
        mLocation.setOnClickListener(view -> clickItemWithLocation());
        mLive.setOnClickListener(view -> clickItemWithLive());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //点击主题设置
    private void clickItemWithTheme() {
// Pass a context, along with the title of the dialog
        new ColorChooserDialog.Builder(_mActivity, R.string.primary_color)
                .titleSub(R.string.primary_color)  // title of dialog when viewing shades of a color
                .accentMode(true)  // when true, will display accent palette instead of primary palette
                .preselect(Config.primaryColor(getActivity(), mAteKey))  // optionally preselects a color
                .dynamicButtonColor(true)  // defaults to true, false will disable changing action buttons' color to currently selected color
                .show(_mActivity); // an AppCompatActivity which implements ColorCallback

    }

    //点击夜间模式
    private void clickItemWithDark() {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(_mActivity).edit();
        boolean isDark = PreferenceManager.getDefaultSharedPreferences(_mActivity).getBoolean("dark_theme", false);
        mDarkCheckBox.setChecked(!isDark);
        if (isDark) {
            //夜间模式跳转到白天模式
            edit.putBoolean("dark_theme", false);
        } else {
            //白天模式跳转到夜间模式
            edit.putBoolean("dark_theme", true);
        }
        edit.commit();
        Config.markChanged(getActivity(), "light_theme");
        Config.markChanged(getActivity(), "dark_theme");
//        _mActivity.recreate();
        Intent intent = new Intent(_mActivity, _mActivity.getClass());
        intent.putExtra(Keys.KEY_SELECT_INDEX, 2);
        startActivity(intent);
        _mActivity.finish();
        _mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void clickItemWithLocation() {
        if (!LocationUtils.isGpsEnabled(getContext())) {
            applyGps();
            return;
        }

//        applyPermission();
        if (AndPermission.hasPermissions(_mActivity, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
            TastyToast.makeText(_mActivity.getApplicationContext(), "执行了", TastyToast.LENGTH_SHORT, TastyToast.INFO);
            if (mLocationSwitch.isChecked()) {
                //关闭定位
                LocationUtils.unRegisterLocation();
            } else {
                //开启定位
                LocationUtils.registerLocation(_mActivity, 2000, 0, this);
            }
            mLocationSwitch.setChecked(!mLocationSwitch.isChecked());
        } else {
            applyLocationPermission();
        }

    }

    //跳转直播页面
    private void clickItemWithLive() {
        if (AndPermission.hasPermissions(_mActivity,
                Manifest.permission.CAMERA,
                Permission.RECORD_AUDIO,
                Permission.WRITE_EXTERNAL_STORAGE
        )) {
            BaseFragment baseFragment = (BaseFragment) ARouter.getInstance().build(RouterConstants.LIVE_LIVE)
                    .navigation();
            ((BaseFragment) getParentFragment()).start(baseFragment);
        } else {
            applyCameraPermission();
        }

    }

    private void applyCameraPermission() {
        AndPermission.with(_mActivity)
                .runtime()
                .permission(
                        //定位权限
                        Permission.CAMERA,
                        Permission.RECORD_AUDIO,
                        Permission.WRITE_EXTERNAL_STORAGE
                )
                .onGranted(permissions -> clickItemWithLive())
                .onDenied(permissions -> TastyToast.makeText(_mActivity.getApplicationContext(), "权限被拒绝", TastyToast.LENGTH_SHORT, TastyToast.ERROR))
                .start();
    }


    private void applyLocationPermission() {
        AndPermission.with(_mActivity)
                .runtime()
                .permission(
                        //定位权限
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                )
                .onGranted(permissions -> clickItemWithLocation())
                .onDenied(permissions -> TastyToast.makeText(_mActivity.getApplicationContext(), "权限被拒绝", TastyToast.LENGTH_SHORT, TastyToast.ERROR))
                .start();
    }

    private void applyGps() {
        new MaterialDialog.Builder(_mActivity)
                .title("友情提示")
                .content("兄台，是否要去开启定位")
                .positiveText("去")
                .negativeText("不去")
                .onPositive((dialog, which) -> {
                    LocationUtils.openGpsSettings(getContext());
                    dialog.dismiss();
                })
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void getLastKnownLocation(Location location) {

    }

    @Override
    public void onLocationChanged(Location location) {
        OkGo.<String>post(Constants.URL_SENDALL)
                .params("msg", new Gson().toJson(LocationUtils.GPS84ToGCJ02(location.getLongitude(), location.getLatitude())))
                .execute(new JsonCallback<String>() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        NotificationManagerCompat.from(_mActivity).notify(1, buildNotification(location.getLongitude(), location.getLatitude()));
                    }
                });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public Notification buildNotification(Double longitude, Double latitude) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(_mActivity, "default")
                .setSmallIcon(com.zhuzichu.module_service.R.mipmap.ic_pikachu)
                .setTicker("开启定位")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("我的博客")
                .setContentText("定位坐标:longitude=" + longitude + ",latitude" + latitude);
        return builder.build();
    }

}

