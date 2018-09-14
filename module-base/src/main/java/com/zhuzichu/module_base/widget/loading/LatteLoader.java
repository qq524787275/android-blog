package com.zhuzichu.module_base.widget.loading;

import android.content.Context;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhuzichu.module_base.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class LatteLoader {

    public static final ArrayList<WeakReference<MaterialDialog>> LOADERS = new ArrayList<>();


    public static void show(Context context) {
        MaterialDialog mDialog = new MaterialDialog.Builder(context)
                .title("友情提示")
                .content("亲，正在加载数据")
                .progress(true, 0)
                .build();
        final Window dialogWindow = mDialog.getWindow();

        if (dialogWindow != null) {
            mDialog.getWindow().setWindowAnimations(R.style.WindowAlphaAnimation);
        }
        LOADERS.add(new WeakReference<>(mDialog));
        mDialog.show();
    }

    public static void hide() {
        for (WeakReference<MaterialDialog> weakReference: LOADERS) {
            if (weakReference != null) {
               MaterialDialog dialog=weakReference.get();
                if (dialog!=null) {
                    dialog.cancel();
                    weakReference.clear();
                    //dialog.dismiss();
                }
            }
        }
    }
}
