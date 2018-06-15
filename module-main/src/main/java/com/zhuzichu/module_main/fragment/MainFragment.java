package com.zhuzichu.module_main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.sdsmdg.tastytoast.TastyToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhuzichu.module_base.Constants;
import com.zhuzichu.module_base.base.BaseBackFragment;
import com.zhuzichu.module_base.callback.JsonCallback;
import com.zhuzichu.module_base.utils.LocationUtils;
import com.zhuzichu.module_main.R;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * 作者: Zzc on 2018-06-13.
 * 版本: v1.0
 */
public class MainFragment extends BaseBackFragment {

    LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Logger.i("onStatusChanged");
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            Logger.i("onProviderEnabled");
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            Logger.i("onProviderDisabled");
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            Logger.i("onLocationChanged");
            String info="经度: " + LocationUtils.gpsToDegree(location.getLongitude()) +
                    "\n纬度: " + LocationUtils.gpsToDegree(location.getLatitude()) +
                    "\n精度: " + location.getAccuracy() +
                    "\n海拔: " + location.getAltitude() +
                    "\n方位: " + location.getBearing() +
                    "\n速度: " + location.getSpeed();

            TastyToast.makeText(_mActivity.getApplicationContext(),info,TastyToast.LENGTH_SHORT,TastyToast.INFO);
            OkGo.<String>post(Constants.URL_SENDALL)
                    .params("msg",new Gson().toJson(LocationUtils.GPS84ToGCJ02(location.getLongitude(),location.getLatitude())))
                    .execute(new JsonCallback<String>() {
                        @Override
                        public void onSuccess(Response<String> response) {

                        }
                    });

        }
    };

    private LocationManager locationManager;
    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Object setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initLocation();
        permissionCheck();
        gpsCheck();
    }


    private void initLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
    }

    private void  permissionCheck() {
        AndPermission.with(getActivity())
                .permission(
                        //定位权限
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        getLocation();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        TastyToast.makeText(_mActivity.getApplicationContext(),"权限被拒绝",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                })
                .start();
    }

    private void gpsCheck() {
        if (!LocationUtils.isGpsEnabled(getContext())) {
            new QMUIDialog.MessageDialogBuilder(getActivity())
                    .setTitle("友情提示")
                    .setMessage("兄台，是否要去开启定位？")
                    .addAction("不去", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction(0, "去", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            LocationUtils.openGpsSettings(getContext());
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            getLocation();
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            TastyToast.makeText(_mActivity.getApplicationContext(),"兄台，定位未开权限",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            return;
        }
        locationManager.requestLocationUpdates(locationManager.getBestProvider(LocationUtils.getCriteria(), true), 2000, 0,locationListener);

        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    case GpsStatus.GPS_EVENT_STARTED:
//                        Logger.i("GPS_EVENT_STARTED");
                        break;
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
//                        Logger.i("GPS_EVENT_FIRST_FIX");
                        break;
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        Iterable<GpsSatellite> gpsSatellites = gpsStatus.getSatellites();
                        Iterator iterator = gpsSatellites.iterator();
                        int count = 0;
                        while (iterator.hasNext()) {
                            count++;
                            iterator.next();
                        }
//                        Logger.i("GPS_EVENT_SATELLITE_STATUS+count:"+count);
                        break;
                    case GpsStatus.GPS_EVENT_STOPPED:
//                        Logger.i("GPS_EVENT_STOPPED");
                        //gpsState.setText("已停止定位");
                        break;
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }
}
