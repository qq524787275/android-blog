package com.zhuzichu.module_base.utils;

import android.content.Context;
import android.text.format.Formatter;

import com.just.agentweb.AgentWebConfig;

import java.io.File;

/**
 * 作者: Zzc on 2018-06-28.
 * 版本: v1.0
 */
public class AppCacheUtils {
    private final static String GLIDE_DISCACHE_DIR = "/glide_cache_dir";
    private final static String WEBVIEW_SONIC_DISCACHE_DIR="/webview_sonic_cache_dir";

    /**
     * 获取Webview缓存目录
     *
     * @param context context
     * @return 缓存目录
     */
    public static File getSonicCacheDir(Context context) {
        String path;
        if (SDCardUtils.isSDCardMounted()) {
            path = context.getExternalCacheDir() + WEBVIEW_SONIC_DISCACHE_DIR;
        } else {
            path = context.getCacheDir() + WEBVIEW_SONIC_DISCACHE_DIR;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsoluteFile();
    }


    /**
     * 获取glide缓存目录
     *
     * @param context context
     * @return 缓存目录
     */
    public static File getGlideDiskCacheDir(Context context) {
        String path;
        if (SDCardUtils.isSDCardMounted()) {
            path = context.getExternalCacheDir() + GLIDE_DISCACHE_DIR;
        } else {
            path = context.getCacheDir() + GLIDE_DISCACHE_DIR;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsoluteFile();
    }



    /**
     * 获取Glide缓存大小
     *
     * @param context context
     * @return 缓存大小
     */
    public static String getGlidecacheFileSizeStr(Context context) {
        long fileSize = getGlidecacheFileSizeNum(context);
        return Formatter.formatFileSize(context, fileSize);
    }

    public static long getGlidecacheFileSizeNum(Context context) {
        long fileSize = 0;
        File file = getGlideDiskCacheDir(context);
        for (File childFile : file.listFiles()) {
            fileSize += childFile.length();
        }
        return fileSize;
    }


    /**
     * 获取sonic 缓存大小
     * @param context
     * @return
     */
    public static long getSonicSizeNum(Context context) {
        long fileSize = 0;
        File file = getSonicCacheDir(context);
        for (File childFile : file.listFiles()) {
            fileSize += childFile.length();
        }
        return fileSize;
    }


    /**
     * 获取WebView 缓存大小
     * @param context
     * @return
     */
    public static long getWebViewSizeNum(Context context) {
        long fileSize = 0;
        File file = new File(AgentWebConfig.getCachePath(context));
        if(!file.exists()) return 0;
        for (File childFile : file.listFiles()) {
            fileSize += childFile.length();
        }
        return fileSize;
    }


    /**
     * 获取sonic 缓存大小
     * @param context
     * @return
     */
    public static long getAgentWebSizeNum(Context context) {
        long fileSize = 0;
        File file = new File(AgentWebConfig.getExternalCachePath(context));
        if(!file.exists()) return 0;
        for (File childFile : file.listFiles()) {
            fileSize += childFile.length();
        }
        return fileSize;
    }

}
