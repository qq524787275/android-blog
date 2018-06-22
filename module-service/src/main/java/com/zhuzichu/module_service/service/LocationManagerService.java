package com.zhuzichu.module_service.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.orhanobut.logger.Logger;
import com.zhuzichu.module_base.utils.LocationUtils;
import com.zhuzichu.module_service.ILocationManager;
import com.zhuzichu.module_service.R;

/**
 * 作者: Zzc on 2018-06-15.
 * 版本: v1.0
 */
public class LocationManagerService extends Service implements LocationListener {

    private boolean mServiceInUse = false;
    private int mServiceStartId = -1;
    private NotificationManagerCompat mNotificationManager;
    private LocationManager mLocationManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.i("");
        mServiceInUse = true;
        return new ILocationManager.Stub() {
            @SuppressLint("MissingPermission")
            @Override
            public void startLocation() throws RemoteException {
                Logger.i("执行了startLocation");
//                LocationUtils.registerLocation(LocationManagerService.this,2000,0,LocationManagerService.this);
                mLocationManager.requestLocationUpdates(mLocationManager.getBestProvider(LocationUtils.getCriteria(), true), 2000, 0,LocationManagerService.this);

            }

            @SuppressLint("MissingPermission")
            @Override
            public void stopLocation() throws RemoteException {
                Logger.i("执行了stopLocation");
//                LocationUtils.unRegisterLocation();
                mLocationManager.removeUpdates(LocationManagerService.this);
            }

            @Override
            public boolean isUse() throws RemoteException {
                return mServiceInUse;
            }
        };
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = NotificationManagerCompat.from(this);
//        initLocationManager();
        Logger.i("执行了");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    SystemClock.sleep(2000);
//                    Logger.i(Thread.currentThread().getName()+","+Thread.currentThread().getId());
//                    updateNotification();
//                }
//            }
//        }).start();
    }

    @SuppressLint("MissingPermission")
    private void initLocationManager() {
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addGpsStatusListener(new GpsStatus.Listener() {
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i("");
        mServiceStartId = startId;

        return START_NOT_STICKY;
    }


    public void updateNotification(){
        int notificationId = hashCode();
        mNotificationManager.notify(notificationId,buildNotification());
    }

    public Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_pikachu)
                .setTicker("开启定位")
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getString(R.string.app_name))
                .setContentText("定位状态");
        return builder.build();
    }


    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        mServiceInUse = true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.i("");
        mServiceInUse = false;
        stopSelf(mServiceStartId);
        return true;
    }

    @Override
    public void onDestroy() {
        Logger.i("");
        super.onDestroy();
    }


    @Override
    public void onLocationChanged(Location location) {
        String info="经度: " + LocationUtils.gpsToDegree(location.getLongitude()) +
                "\n纬度: " + LocationUtils.gpsToDegree(location.getLatitude()) +
                "\n精度: " + location.getAccuracy() +
                "\n海拔: " + location.getAltitude() +
                "\n方位: " + location.getBearing() +
                "\n速度: " + location.getSpeed();
        Logger.i(info);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
