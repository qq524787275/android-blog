package com.zhuzichu.module_cartoon.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.callback.BaseResponse;
import com.zhuzichu.module_base.callback.JsonCallback;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.utils.DensityUtils;
import com.zhuzichu.module_base.widget.loading.LatteLoader;
import com.zhuzichu.module_base.widget.recyclerview.FastScrollRecyclerView;
import com.zhuzichu.module_base.widget.sidemenu.interfaces.ScreenShotable;
import com.zhuzichu.module_cartoon.Constants;
import com.zhuzichu.module_cartoon.R;
import com.zhuzichu.module_cartoon.R2;
import com.zhuzichu.module_cartoon.adapter.TagsAdapter;
import com.zhuzichu.module_cartoon.beans.TagsBean;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者: Zzc on 2018-06-26.
 * 版本: v1.0
 */
public class ContentFragment extends BaseFragment implements ScreenShotable {


    @BindView(R2.id.container)
    FrameLayout mContainer;
    @BindView(R2.id.recyclerview)
    FastScrollRecyclerView mRv;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefresh;
    private TagsAdapter mTagsAdapter;
    private Bitmap bitmap;
    private int mTag;
    private int mCount = 20;
    private int mPage = 0;
    private CompositeSubscription mSubscription;

    public static ContentFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTag = getArguments().getInt("position");

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSubscription = new CompositeSubscription();
        mTagsAdapter = new TagsAdapter();
        mTagsAdapter.setWidth(DensityUtils.getScreenW(_mActivity) / 2);
        mRv.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mRv.setAdapter(mTagsAdapter);

        mRefresh.setPrimaryColors(Color.parseColor("#00000000"), ATEUtil.getThemePrimaryColor(_mActivity));
        ((ClassicsFooter) mRefresh.getRefreshFooter()).setAccentColor(ATEUtil.getThemePrimaryColor(_mActivity));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                loadTags();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                loadTags();
            }
        });
    }

    @Override
    public void takeScreenShot() {
        mSubscription.clear();
        Subscription subscribe = Observable.create((Observable.OnSubscribe<Bitmap>) subscriber -> subscriber.onNext(Bitmap.createBitmap(mContainer.getWidth(), mContainer.getHeight(), Bitmap.Config.ARGB_8888))).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    Canvas canvas = new Canvas(bitmap);
                    mContainer.draw(canvas);
                    ContentFragment.this.bitmap = bitmap;
                });
        mSubscription.add(subscribe);
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadTags();
    }

    public void loadTags() {
        LatteLoader.show(_mActivity);
        OkGo.<BaseResponse<TagsBean>>get(Constants.URL_TAGS + mTag)
                .tag(this)
                .params("count", mCount)
                .params("page", mPage)
                .execute(new JsonCallback<BaseResponse<TagsBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<TagsBean>> response) {
                        List<TagsBean.TopicsBean> list = response.body().data.getTopics();
                        if(list.size()==0){
                            mRefresh.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        if(mPage==0){
                            mTagsAdapter.setNewData(list);
                        }else{
                            mTagsAdapter.addData(list);
                        }

                    }

                    @Override
                    public void onError(Response<BaseResponse<TagsBean>> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mRefresh.finishLoadMore();
                        mRefresh.finishRefresh();
                        LatteLoader.hide();
                    }
                });

    }


}
