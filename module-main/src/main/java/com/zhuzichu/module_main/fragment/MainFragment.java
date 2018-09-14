package com.zhuzichu.module_main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhuzichu.module_base.Keys;
import com.zhuzichu.module_base.base.BaseBackFragment;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.widget.animate.Techniques;
import com.zhuzichu.module_base.widget.animate.YoYo;
import com.zhuzichu.module_base.widget.bottom.BottomBar;
import com.zhuzichu.module_base.widget.bottom.BottomBarTab;
import com.zhuzichu.module_cartoon.fragment.CartoonFragment;
import com.zhuzichu.module_home.fragment.HomeFragment;
import com.zhuzichu.module_main.R;
import com.zhuzichu.module_main.R2;
import com.zhuzichu.module_person.fragment.PersonFragment;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-06-13.
 * 版本: v1.0
 */
public class MainFragment extends BaseBackFragment {
    public static final int HOME = 0;
    public static final int CARTOON = 1;
    public static final int PERSON = 2;

    private BaseFragment[] mBaseFragments = new BaseFragment[3];

    @BindView(R2.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    public Object setLayout() {
        return R.layout.fragment_main;
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        BaseFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mBaseFragments[HOME] = HomeFragment.newInstance();
            mBaseFragments[PERSON] = PersonFragment.newInstance();
            mBaseFragments[CARTOON] = CartoonFragment.newInstance();

            loadMultipleRootFragment(R.id.delegate_containe, HOME, mBaseFragments[HOME]
                    , mBaseFragments[PERSON], mBaseFragments[CARTOON]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mBaseFragments[HOME] = firstFragment;
            mBaseFragments[PERSON] = findChildFragment(PersonFragment.class);
            mBaseFragments[CARTOON] = findChildFragment(CartoonFragment.class);
        }
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.main_tab_home_normal, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.main_tab_cartoon_normal, "漫画"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.main_tab_person_normal, "我的"));
        mBottomBar.setCurrentItem(_mActivity.getIntent().getIntExtra(Keys.KEY_SELECT_INDEX, 0));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, View view) {
//                showHideAnimaFragment(mBaseFragments[position], mBaseFragments[prePosition], getFragmentManager());
                showHideFragment(mBaseFragments[position], mBaseFragments[prePosition]);
                switch (position) {
                    case 0:
                        YoYo.with(Techniques.BounceInLeft).duration(500).playOn(view);
                        break;
                    case 1:
                        YoYo.with(Techniques.BounceInDown).duration(500).playOn(view);
                        break;
                    case 2:
                        YoYo.with(Techniques.BounceInRight).duration(500).playOn(view);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {


            }
        });
    }

}
