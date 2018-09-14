package com.zhuzichu.module_cartoon.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.MaterialDialog;
import com.just.agentweb.IWebLayout;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuzichu.module_base.base.BaseWebFragment;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.utils.AppCacheUtils;
import com.zhuzichu.module_base.webview.layout.SmartRefreshWebLayout;
import com.zhuzichu.module_cartoon.R;
import com.zhuzichu.module_cartoon.R2;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-07-02.
 * 版本: v1.0
 */
public class CartoonWebFragment extends BaseWebFragment {
    @BindView(R2.id.parent)
    LinearLayout mParent;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.menu)
    ActionMenuView mMenu;

    private SmartRefreshWebLayout mSmartRefreshWebLayout = null;

    public static CartoonWebFragment newInstance() {

        Bundle args = new Bundle();
//        args.putString(BaseSonicFragment.URL_KEY,"http://www.kuaikanmanhua.com/");
//        args.putString(BaseSonicFragment.URL_KEY,"http://mc.vip.qq.com/demo/indexv3");
        CartoonWebFragment fragment = new CartoonWebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return mParent;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_web_cartoon;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        initToolBar();
    }

    private void initToolBar() {
        mToolbar.setTitle("快漫");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SmartRefreshLayout mSmartRefreshLayout = (SmartRefreshLayout) this.mSmartRefreshWebLayout.getLayout();

        final WebView mWebView = this.mSmartRefreshWebLayout.getWebView();
        mSmartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mAgentWeb.getUrlLoader().reload();
            mSmartRefreshLayout.postDelayed(() -> mSmartRefreshLayout.finishRefresh(), 2000);
        });
        mSmartRefreshLayout.autoRefresh();
        ATE.postApply(_mActivity, ATEUtil.getATEKey(_mActivity));
    }



    @Nullable
    @Override
    protected String getUrl() {
        return "http://www.kuaikanmanhua.com/";
    }


    @Nullable
    @Override
    protected WebChromeClient getWebChromeClient() {
        return new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mToolbar.setTitle(title);
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_web, mMenu.getMenu());
        mMenu.getOverflowIcon().setColorFilter(ATEUtil.getToolbarTitleColor(_mActivity,mToolbar), PorterDuff.Mode.SRC_IN);
        mMenu.setOnMenuItemClickListener((item) -> onOptionsItemSelected(item));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.web_clean){
            new MaterialDialog.Builder(_mActivity)
                    .title("清理缓存")
                    .content("当前缓存:" + (Formatter.formatFileSize(_mActivity,
                            AppCacheUtils.getSonicSizeNum(_mActivity)+AppCacheUtils.getWebViewSizeNum(_mActivity)+AppCacheUtils.getAgentWebSizeNum(_mActivity)
                            )))
                    .positiveText("清理")
                    .negativeText("取消")
                    .onPositive((dialog, which) -> cleanCache(dialog))
                    .onNegative((dialog, which) -> dialog.dismiss()).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cleanCache(MaterialDialog dialog) {
        Logger.i(""+ AppCacheUtils.getWebViewSizeNum(_mActivity));
        Logger.i(""+AppCacheUtils.getAgentWebSizeNum(_mActivity));
        Logger.i(""+AppCacheUtils.getSonicSizeNum(_mActivity));
    }

    @Nullable
    @Override
    protected IWebLayout getWebLayout() {
      return this.mSmartRefreshWebLayout = new SmartRefreshWebLayout(_mActivity);
    }
}
