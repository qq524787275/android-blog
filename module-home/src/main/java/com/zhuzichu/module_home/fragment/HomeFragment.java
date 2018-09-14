package com.zhuzichu.module_home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.tastytoast.TastyToast;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.react.preloadreact.PreLoadReactActivity;
import com.zhuzichu.module_base.widget.animate.Techniques;
import com.zhuzichu.module_base.widget.animate.YoYo;
import com.zhuzichu.module_base.widget.likeview.RxShineButton;
import com.zhuzichu.module_home.R;
import com.zhuzichu.module_home.R2;

import butterknife.BindView;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 作者: Zzc on 2018-06-19.
 * 版本: v1.0
 */
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.click3)
    Button button3;
    @BindView(R2.id.po_image0)
    RxShineButton shineButton;
    @BindView(R2.id.submit)
    Button mButton;

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
//        shineButton.init(_mActivity);
        shineButton.setOnCheckStateChangeListener(new RxShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                TastyToast.makeText(_mActivity.getApplicationContext(), checked + "", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
            }
        });
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PreLoadReactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("component", "Home");
                intent.putExtras(bundle);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(_mActivity.getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent, optionsCompat.toBundle());
//                ReactLoadingFragment home = ReactLoadingFragment.newInstance("Home");
//                home.show(getChildFragmentManager());
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int badgeCount = 1;
                ShortcutBadger.applyCount(_mActivity, badgeCount); //for 1.1.4+
                YoYo.with(Techniques.RubberBand).duration(1000).playOn(mButton);
            }
        });
    }

}
