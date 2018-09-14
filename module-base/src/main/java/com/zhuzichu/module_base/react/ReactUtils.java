package com.zhuzichu.module_base.react;

import android.app.Activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.orhanobut.logger.Logger;
import com.zhuzichu.module_base.utils.ZipUtils;

import java.io.File;
import java.io.IOException;

/**
 * 作者: Zzc on 2018-07-11.
 * 版本: v1.0
 */
public class ReactUtils {
    public static String url = "http://zhuzichu.com/static/index.android.zip";

    public static void loadJSBundle(final Activity activity,final LoadJsBunldeListener loadJsBunlde) {
        GetRequest<File> fileGetRequest = OkGo.get(url);
        OkDownload.request(url, fileGetRequest)
                .folder(activity.getExternalFilesDir(null).getAbsolutePath())
                .save()
                .register(new DownloadListener(url) {

                    @Override
                    public void onStart(Progress progress) {
                        Logger.i("");
                        loadJsBunlde.onStart(progress);
                    }

                    @Override
                    public void onProgress(Progress progress) {
                        Logger.i("");
                        loadJsBunlde.onProgress(progress);
                    }

                    @Override
                    public void onError(Progress progress) {
                        Logger.i("");
                        loadJsBunlde.onError(progress);
                    }

                    @Override
                    public void onFinish(File file, Progress progress) {
                        Logger.i("");
                        int lastSep = file.getAbsolutePath().lastIndexOf(File.separator);
                        String substring = file.getAbsolutePath().substring(0, lastSep);
                        try {
                            ZipUtils.unzipFile(file.getAbsolutePath(), substring);
                            loadJsBunlde.onFilish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onRemove(Progress progress) {
                        loadJsBunlde.onRemove(progress);
                        Logger.i("");
                    }
                })
                .start();
    }

    public interface LoadJsBunldeListener{
        void onFilish();
        void onStart(Progress progress);
        void onProgress(Progress progress);
        void onError(Progress progress);
        void onRemove(Progress progress);
    }
}
