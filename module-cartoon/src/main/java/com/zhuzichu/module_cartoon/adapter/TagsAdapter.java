package com.zhuzichu.module_cartoon.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuzichu.module_base.GlideApp;
import com.zhuzichu.module_cartoon.R;
import com.zhuzichu.module_cartoon.beans.TagsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: Zzc on 2018-06-28.
 * 版本: v1.0
 */
public class TagsAdapter extends BaseQuickAdapter<TagsBean.TopicsBean,BaseViewHolder> {
    private Map<String, Integer> heightMap = new HashMap<>();
    private int width;
    public TagsAdapter() {
        super(R.layout.item_tag,new ArrayList<>());
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, TagsBean.TopicsBean item) {
        GlideApp.with(mContext)
                .load(item.getVertical_image_url())
                .placeholder(R.mipmap.img_default_meizi)
                .transition(new DrawableTransitionOptions().crossFade(300))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .into((ImageView) helper.getView(R.id.item_tag_img));
        int height;
        if (!heightMap.containsKey(item.getVertical_image_url())) {
            height = 424 * width / 320;
            heightMap.put(item.getVertical_image_url(), height);
        } else {
            height = heightMap.get(item.getVertical_image_url());
        }

        GridLayoutManager.LayoutParams layoutParams= (GridLayoutManager.LayoutParams) helper.itemView.getLayoutParams();
        layoutParams.height = height;
        helper.itemView.setLayoutParams(layoutParams);
        helper.setText(R.id.item_tag_title,item.getTitle());
    }
}
