package com.zhuzichu.module_cartoon.fragment;

import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sdsmdg.tastytoast.TastyToast;
import com.zhuzichu.module_base.GlideApp;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.utils.AppCacheUtils;
import com.zhuzichu.module_base.widget.sidemenu.interfaces.Resourceble;
import com.zhuzichu.module_base.widget.sidemenu.interfaces.ScreenShotable;
import com.zhuzichu.module_base.widget.sidemenu.util.ViewAnimator;
import com.zhuzichu.module_cartoon.R;
import com.zhuzichu.module_cartoon.R2;
import com.zhuzichu.module_cartoon.enums.TagType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者: Zzc on 2018-06-26.
 * 版本: v1.0
 */
public class CartoonFragment extends BaseFragment {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.left_drawer)
    LinearLayout mLeftMenu;
    @BindView(R2.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R2.id.content_overlay)
    LinearLayout mOverlay;
    @BindView(R2.id.content_frame)
    LinearLayout mContentFrame;
    @BindView(R2.id.container_frame)
    RevealFrameLayout mContainer;
    @BindView(R2.id.menu)
    ActionMenuView mMenu;
    private ViewAnimator viewAnimator;
    private ActionBarDrawerToggle mDrawerToggle;
    private ContentFragment mContentFragment;
    private List<TagType> list = new ArrayList<>();
    private CompositeSubscription mSubscription;
    private Subscription subscribe;

    public static CartoonFragment newInstance() {

        Bundle args = new Bundle();

        CartoonFragment fragment = new CartoonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_cartoon;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSubscription = new CompositeSubscription();
        subscribe = Observable.from(TagType.values()).toList().subscribe(tagTypes -> list = tagTypes);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        initToolBar();
//      mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mContentFragment = ContentFragment.newInstance(TagType.TAG_0.getImageRes());
        loadRootFragment(R.id.content_frame, mContentFragment);
        viewAnimator = new ViewAnimator<>(_mActivity, list, mContentFragment, mDrawerLayout, new ViewAnimator.ViewAnimatorListener() {
            @Override
            public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
                toolbar.setTitle("漫画-" + slideMenuItem.getName());
                return replaceFragment(screenShotable, position, slideMenuItem.getImageRes());
            }

            @Override
            public void disableHomeButton() {
                getSupportActionBar().setHomeButtonEnabled(false);
            }

            @Override
            public void enableHomeButton() {
                getSupportActionBar().setHomeButtonEnabled(true);
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void addViewToContainer(View view) {
                mLeftMenu.addView(view);
            }
        }, toolbar);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, int type) {

        int finalRadius = Math.max(mContentFrame.getWidth(), mContentFrame.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mContentFrame, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        mOverlay.setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(type);
        getChildFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu.getMenu().clear();
        inflater.inflate(R.menu.menu_cartoon, mMenu.getMenu());
        mMenu.getOverflowIcon().setColorFilter(ATEUtil.getToolbarTitleColor(_mActivity,toolbar), PorterDuff.Mode.SRC_IN);
        mMenu.setOnMenuItemClickListener((item) -> onOptionsItemSelected(item));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cartoon_clean) {
            new MaterialDialog.Builder(_mActivity)
                    .title("清理缓存")
                    .content("当前缓存:" + AppCacheUtils.getGlidecacheFileSizeStr(_mActivity))
                    .positiveText("清理")
                    .negativeText("取消")
                    .onPositive((dialog, which) -> cleanCache(dialog))
                    .onNegative((dialog, which) -> dialog.dismiss()).show();
        } else if (id == R.id.browser_see) {
            ((BaseFragment) getParentFragment()).start(CartoonWebFragment.newInstance());
        }
        return super.onOptionsItemSelected(item);
    }

    private void cleanCache(MaterialDialog dialog) {
        mSubscription.clear();
        Subscription subscribe = Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            GlideApp.get(_mActivity).clearDiskCache();
            subscriber.onNext("清除成功~");
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    TastyToast.makeText(_mActivity.getApplicationContext(), s, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    dialog.dismiss();
                });
        mSubscription.add(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ATE.postApply(_mActivity, ATEUtil.getATEKey(_mActivity));
    }

    private void initToolBar() {
        toolbar.setTitle("漫画-全部");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                _mActivity,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mLeftMenu.removeAllViews();
                mLeftMenu.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && mLeftMenu.getChildCount() == 0) {
                    viewAnimator.showMenuContent();
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mDrawerToggle.getDrawerArrowDrawable().setColorFilter(ATEUtil.getToolbarTitleColor(_mActivity, toolbar), PorterDuff.Mode.SRC_IN);

    }

}
