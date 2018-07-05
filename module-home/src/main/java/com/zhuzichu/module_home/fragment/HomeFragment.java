package com.zhuzichu.module_home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_home.R;

/**
 * 作者: Zzc on 2018-06-19.
 * 版本: v1.0
 */
public class HomeFragment extends BaseFragment {
    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {


    }
}
