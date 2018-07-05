package com.zhuzichu.module_base.utils;

import android.os.Environment;

/**
 * 作者: Zzc on 2018-06-28.
 * 版本: v1.0
 */
public class SDCardUtils {
    /**
     * 存储卡是否挂载
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
