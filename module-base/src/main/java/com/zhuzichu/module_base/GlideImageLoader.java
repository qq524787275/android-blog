package com.zhuzichu.module_base;

import android.content.Context;
import android.widget.ImageView;

import com.zhuzichu.module_base.widget.banner.loader.ImageLoader;

/**
 * Created by Zzc on 2017/11/6/006.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setTag(null);
        GlideApp.with(context).load(path)
                .placeholder(R.mipmap.img_two_bi_one)
                .error(R.mipmap.img_two_bi_one)
                .into(imageView);
//         Glide.with(context).load(path).placeholder(R.mipmap.logo).into(imageView);
    }
}
