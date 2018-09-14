package com.zhuzichu.module_base.utils;

/**
 * 作者: Zzc on 2018-07-10.
 * 版本: v1.0
 */
public class ColorUtils {
    public static String toHexa(int color){
     return String.format("#%06X", 0xFFFFFF & color);
    }
}
