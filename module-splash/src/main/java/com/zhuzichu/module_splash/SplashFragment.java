package com.zhuzichu.module_splash;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhuzichu.module_base.App;
import com.zhuzichu.module_base.Constants;
import com.zhuzichu.module_base.GlideApp;
import com.zhuzichu.module_base.RouterConstants;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.widget.OnClickListener;

import java.util.Random;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-04-03.
 * 版本: v1.0
 */

public class SplashFragment extends BaseFragment {
    @BindView(R2.id.iv_pic)
    ImageView mIvPic;
    @BindView(R2.id.tv_jump)
    TextView mTvJump;

    private boolean isJump=false;
    private boolean isIn;

    public static SplashFragment newInstance() {

        Bundle args = new Bundle();

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_splash;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initView(View view) {
        final int i = new Random().nextInt(Constants.TRANSITION_URLS.length);
        //先显示默认图
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GlideApp.with(App.getContent())
                        .load(Constants.TRANSITION_URLS[i])
                        .placeholder(R.mipmap.img_transition_default)
                        .error(R.mipmap.img_transition_default)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                mIvPic.setImageDrawable(resource);
                                init();
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                startToMainActivity();
                            }
                        });
            }
        },1000);


    }

    private void init() {
        mTvJump.setVisibility(View.VISIBLE);
        mTvJump.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                isJump=true;
                startToMainActivity();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isJump){
                    startToMainActivity();
                }
            }
        },2000);
    }

    private void startToMainActivity() {

        ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeCustomAnimation(_mActivity.getApplicationContext(),R.anim.screen_zoom_in,R.anim.screen_zoom_out);
        ARouter.getInstance().build(RouterConstants.MAIN_MAIN)
                .withOptionsCompat(optionsCompat)
                .navigation();
        _mActivity.finish();
    }
}
