package com.zhuzichu.module_service.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.zhuzichu.module_service.ILocationManager;
import com.zhuzichu.module_service.service.LocationManagerService;

import java.util.WeakHashMap;

/**
 * 作者: Zzc on 2018-06-15.
 * 版本: v1.0
 */
public class LocationManager {
    private static final WeakHashMap<Context, ServiceBinder> mConnectionMap;
    public static ILocationManager mService = null;

    static {
        mConnectionMap = new WeakHashMap<Context, ServiceBinder>();
    }

    public static ServiceToken bindToService(final Context context,
                                             final ServiceConnection callback) {

        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }
        final ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        contextWrapper.startService(new Intent(contextWrapper, LocationManagerService.class));
        final ServiceBinder binder = new ServiceBinder(callback,
                contextWrapper.getApplicationContext());
        if (contextWrapper.bindService(
                new Intent().setClass(contextWrapper, LocationManagerService.class), binder, 0)) {
            mConnectionMap.put(contextWrapper, binder);
            return new ServiceToken(contextWrapper);
        }
        return null;
    }

    public static void unbindFromService(final ServiceToken token) {
        if (token == null) {
            return;
        }
        final ContextWrapper mContextWrapper = token.mWrappedContext;
        final ServiceBinder mBinder = mConnectionMap.remove(mContextWrapper);
        if (mBinder == null) {
            return;
        }
        mContextWrapper.unbindService(mBinder);
        if (mConnectionMap.isEmpty()) {
            mService = null;
        }
    }

    public static final class ServiceToken {
        public ContextWrapper mWrappedContext;

        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }

    public static final class ServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;
        private final Context mContext;


        public ServiceBinder(final ServiceConnection callback, final Context context) {
            mCallback = callback;
            mContext = context;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            mService = ILocationManager.Stub.asInterface(service);
            if (mCallback != null) {
                mCallback.onServiceConnected(className, service);
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(className);
            }
            mService = null;
        }
    }

    public static void openLocation() {
        try {
            if (mService != null) {
                mService.startLocation();
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static void closeLocation() {
        try {
            if (mService != null) {
                mService.stopLocation();
            }
        } catch (final RemoteException ignored) {
        }
    }
}
