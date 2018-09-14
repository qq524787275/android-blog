package com.zhuzichu.module_base.react.likeview;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * 作者: Zzc on 2018-07-17.
 * 版本: v1.0
 */
public class ReactLikeViewEvent extends Event<ReactLikeViewEvent> {
    public static final String EVENT_NAME = "topChange";
    private final boolean mIsChecked;

    public ReactLikeViewEvent(int viewId,boolean mIsChecked) {
        super(viewId);
        this.mIsChecked = mIsChecked;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    public boolean getIsChecked() {
        return mIsChecked;
    }

    @Override
    public short getCoalescingKey() {
        // All switch events for a given view can be coalesced.
        return 0;
    }


    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", getViewTag());
        eventData.putBoolean("value", getIsChecked());
        return eventData;
    }
}
