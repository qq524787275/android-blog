package com.zhuzichu.module_live.fragment;

import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.faucamp.simplertmp.RtmpHandler;
import com.orhanobut.logger.Logger;
import com.sdsmdg.tastytoast.TastyToast;
import com.seu.magicfilter.utils.MagicFilterType;
import com.zhuzichu.module_base.RouterConstants;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_base.utils.ATEUtil;
import com.zhuzichu.module_base.widget.materialintro.shape.Focus;
import com.zhuzichu.module_base.widget.materialintro.shape.FocusGravity;
import com.zhuzichu.module_base.widget.materialintro.shape.ShapeType;
import com.zhuzichu.module_base.widget.materialintro.view.MaterialIntroView;
import com.zhuzichu.module_live.R;
import com.zhuzichu.module_live.R2;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-06-19.
 * 版本: v1.0
 */
@Route(path = RouterConstants.LIVE_LIVE)
public class LiveFragment extends BaseFragment implements RtmpHandler.RtmpListener, SrsEncodeHandler.SrsEncodeListener, SrsRecordHandler.SrsRecordListener {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.menu)
    ActionMenuView mMenu;
    @BindView(R2.id.camera)
    SrsCameraView mCamear;
    @BindView(R2.id.push_switch)
    SwitchCompat mSwitch;
    private static final String TAG = "LiveFragment";
    private String rtmpUrl = "rtmp://zhuzichu.com:8011/hls/123";

    private SrsPublisher mPublisher;
    private MaterialIntroView a;
    private MaterialIntroView b;

    public static LiveFragment newInstance() {

        Bundle args = new Bundle();

        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_live;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initToolBar();
        initGuilde();
        initPublisher();
        ATE.postApply(_mActivity, ATEUtil.getATEKey(_mActivity));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSwitch.setChecked(false);
        mSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                mPublisher.startPublish(rtmpUrl);
                mPublisher.startCamera();
                TastyToast.makeText(_mActivity.getApplicationContext(),"开启直播",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            }else{
                mPublisher.stopPublish();
                mPublisher.stopRecord();
                TastyToast.makeText(_mActivity.getApplicationContext(),"关闭直播",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            }
        });
    }

    private void initPublisher() {
        mPublisher = new SrsPublisher(mCamear);
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        mPublisher.setPreviewResolution(640, 360);
        mPublisher.setOutputResolution(360, 640);
        mPublisher.setVideoHDMode();
        mPublisher.startCamera();
    }

    private void initGuilde() {
        a= new MaterialIntroView.Builder(_mActivity)
                .enableDotAnimation(true)
                .enableIcon(true)
                .setIconColor(ATEUtil.getThemePrimaryColor(_mActivity))
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(300)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("亲！操作这~~~可以选择滤镜。")
                .setShape(ShapeType.CIRCLE)
                .setTarget(mMenu)
                .setListener(a -> {
                    if(mSwitch==null)return;
                    b=new MaterialIntroView.Builder(_mActivity)
                            .enableDotAnimation(true)
                            .enableIcon(true)
                            .setIconColor(ATEUtil.getThemePrimaryColor(_mActivity))
                            .setFocusGravity(FocusGravity.CENTER)
                            .setFocusType(Focus.MINIMUM)
                            .setDelayMillis(300)
                            .enableFadeAnimation(true)
                            .performClick(true)
                            .setInfoText("亲！点击这开始直播吧。")
                            .setShape(ShapeType.CIRCLE)
                            .setTarget(mSwitch)
                            .show();
                })
                .show();
    }


    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("直播");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_live, mMenu.getMenu());
        Logger.i("" + ATEUtil.getThemeTextColorPrimary(_mActivity));
        mMenu.getOverflowIcon().setColorFilter(ATEUtil.getThemeTextColorPrimary(_mActivity), PorterDuff.Mode.SRC_IN);
        mMenu.setOnMenuItemClickListener((item) -> onOptionsItemSelected(item));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cool_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.COOL);

        } else if (id == android.R.id.home) {
            pop();
        } else if (id == R.id.beauty_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.BEAUTY);

        } else if (id == R.id.early_bird_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.EARLYBIRD);

        } else if (id == R.id.evergreen_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.EVERGREEN);

        } else if (id == R.id.n1977_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.N1977);

        } else if (id == R.id.nostalgia_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.NOSTALGIA);

        } else if (id == R.id.romance_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.ROMANCE);

        } else if (id == R.id.sunrise_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.SUNRISE);

        } else if (id == R.id.sunset_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.SUNSET);

        } else if (id == R.id.tender_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.TENDER);

        } else if (id == R.id.toast_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.TOASTER2);

        } else if (id == R.id.valencia_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.VALENCIA);

        } else if (id == R.id.walden_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.WALDEN);

        } else if (id == R.id.warm_filter) {
            mPublisher.switchCameraFilter(MagicFilterType.WARM);

        } else {
            mPublisher.switchCameraFilter(MagicFilterType.NONE);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPublisher.stopEncode();
        mPublisher.stopRecord();
        mPublisher.setScreenOrientation(newConfig.orientation);
        mPublisher.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPublisher.resumeRecord();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPublisher.pauseRecord();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPublisher.stopPublish();
        mPublisher.stopRecord();
        if(a!=null){
            a.dismiss();
            a=null;
        }
        if(b!=null){
            b.dismiss();
            b=null;
        }
    }

    @Override
    public void onNetworkWeak() {
        Logger.i("");
    }

    @Override
    public void onNetworkResume() {
        Logger.i("");
    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
        Logger.i("");
    }


    @Override
    public void onRtmpConnecting(String msg) {

    }

    @Override
    public void onRtmpConnected(String msg) {
        Logger.i("");
    }

    @Override
    public void onRtmpVideoStreaming() {
        Logger.i("");
    }

    @Override
    public void onRtmpAudioStreaming() {
        Logger.i("");
    }

    @Override
    public void onRtmpStopped() {
        Logger.i("");
    }

    @Override
    public void onRtmpDisconnected() {
        Logger.i("");
    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {
        Logger.i("" + fps);
    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {
        Logger.i("" + bitrate);
        int rate = (int) bitrate;
        if (rate / 1000 > 0) {
            Log.i(TAG, String.format("Video bitrate: %f kbps", bitrate / 1000));
        } else {
            Log.i(TAG, String.format("Video bitrate: %d bps", rate));
        }
    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {
        Logger.i("" + bitrate);
    }

    @Override
    public void onRtmpSocketException(SocketException e) {
        Logger.i("");
    }

    @Override
    public void onRtmpIOException(IOException e) {
        Logger.i("");
    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {
        Logger.i("");
    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {
        Logger.i("");
    }


    @Override
    public void onRecordPause() {

    }

    @Override
    public void onRecordResume() {

    }

    @Override
    public void onRecordStarted(String msg) {

    }

    @Override
    public void onRecordFinished(String msg) {

    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRecordIOException(IOException e) {

    }
}
