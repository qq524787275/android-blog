package com.zhuzichu.module_home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_home.MyReactActivity;
import com.zhuzichu.module_home.R;
import com.zhuzichu.module_home.R2;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-06-19.
 * 版本: v1.0
 */
public class HomeFragment extends BaseFragment {
    @BindView(R2.id.click)
    Button button;
//    private ReactRootView mReactRootView;
//    private ReactInstanceManager mReactInstanceManager;

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

    @Override
    protected void initEvent() {
        super.initEvent();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_mActivity, MyReactActivity.class);
                startActivity(intent);
            }
        });
    }
}
