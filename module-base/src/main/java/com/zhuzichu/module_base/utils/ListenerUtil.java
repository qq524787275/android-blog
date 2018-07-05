package com.zhuzichu.module_base.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;

/**
 * 作者: Zzc on 2018-06-28.
 * 版本: v1.0
 */
public class ListenerUtil {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isRtl(Resources res) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) &&
                (res.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }
}
