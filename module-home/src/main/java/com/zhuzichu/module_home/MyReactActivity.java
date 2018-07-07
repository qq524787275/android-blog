package com.zhuzichu.module_home;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhuzichu.module_base.base.BaseReactATEActivity;

import javax.annotation.Nullable;

/**
 * 作者: Zzc on 2018-07-07.
 * 版本: v1.0
 */
public class MyReactActivity extends BaseReactATEActivity {

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "Home";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MaterialDialog.Builder(this)
                .title("友情提示")
                .content("亲，正在加载数据")
                .progress(true, 0)
                .show();
    }
}
