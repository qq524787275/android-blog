package com.zhuzichu.module_base.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.just.agentweb.MiddlewareWebClientBase;
import com.zhuzichu.module_base.webview.sonic.SonicImpl;
import com.zhuzichu.module_base.webview.sonic.SonicJavaScriptInterface;

/**
 * 作者: Zzc on 2018-07-02.
 * 版本: v1.0
 */
public abstract class BaseSonicFragment extends BaseWebFragment {
    private SonicImpl mSonicImpl;
    public static final String URL_KEY = "url_key";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 1. 首先创建SonicImpl
        mSonicImpl = new SonicImpl(this.getArguments().getString(URL_KEY), this.getContext());
        // 2. 调用 onCreateSession
        mSonicImpl.onCreateSession();
        //3. 创建AgentWeb ，注意创建AgentWeb的时候应该使用加入SonicWebViewClient中间件
        super.onViewCreated(view, savedInstanceState); // 创建 AgentWeb 注意的 go("") 传入的 mUrl 应该null 或者""
        //4. 注入 JavaScriptInterface
        mAgentWeb.getJsInterfaceHolder().addJavaObject("sonic",
                new SonicJavaScriptInterface(mSonicImpl.getSonicSessionClient(),
                new Intent().putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME,getArguments().getLong(SonicJavaScriptInterface.PARAM_CLICK_TIME)).putExtra("loadUrlTime", System.currentTimeMillis())));
        //5. 最后绑定AgentWeb
        mSonicImpl.bindAgentWeb(mAgentWeb);
    }

    //在步骤3的时候应该传入给AgentWeb

    @NonNull
    @Override
    protected MiddlewareWebClientBase getMiddleWareWebClient() {
        return mSonicImpl.createSonicClientMiddleWare();
    }

    //getUrl 应该为null
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //销毁SonicSession
        if(mSonicImpl !=null){
            mSonicImpl.destrory();
        }
    }
}
