package com.zhuzichu.module_base.react;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.lzy.okgo.model.Progress;
import com.zhuzichu.module_base.R;
import com.zhuzichu.module_base.react.preloadreact.PreLoadReactActivity;
import com.zhuzichu.module_base.widget.RxProgressBar;
import com.zhuzichu.module_base.widget.nicedialog.BaseNiceDialog;
import com.zhuzichu.module_base.widget.nicedialog.ViewHolder;

import java.math.BigDecimal;

/**
 * 作者: Zzc on 2018-07-12.
 * 版本: v1.0
 */
public class ReactLoadingFragment extends BaseNiceDialog {
    private RxProgressBar mRxProgressBar;
    private boolean isFilish = false;

    public static ReactLoadingFragment newInstance(String component) {

        Bundle args = new Bundle();
        args.putString("component", component);
        ReactLoadingFragment fragment = new ReactLoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int intLayoutId() {
        return R.layout.loading;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mRxProgressBar= holder.getView(R.id.round_flikerbar);

        ReactUtils.loadJSBundle(getActivity(), new ReactUtils.LoadJsBunldeListener() {
            @Override
            public void onFilish() {
                isFilish = true;
                dismiss();
            }

            @Override
            public void onStart(Progress progress) {
            }

            @Override
            public void onProgress(Progress progress) {
                mRxProgressBar.setProgress(new BigDecimal(progress.fraction).setScale(2, BigDecimal.ROUND_UP).floatValue() * 100);
            }

            @Override
            public void onError(Progress progress) {
            }

            @Override
            public void onRemove(Progress progress) {
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isFilish) {
            Intent intent = new Intent(getActivity(), PreLoadReactActivity.class);
            intent.putExtras(getArguments());
            startActivity(intent);
        }
    }

}
