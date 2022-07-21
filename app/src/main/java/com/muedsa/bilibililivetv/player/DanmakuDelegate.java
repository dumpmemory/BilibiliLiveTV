package com.muedsa.bilibililivetv.player;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.fragment.PlaybackVideoFragment;

import java.util.HashMap;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class DanmakuDelegate {
    private static final String TAG = DanmakuDelegate.class.getSimpleName();

    private final PlaybackVideoFragment fragment;
    private final LiveRoom liveRoom;

    private IDanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser danmakuParser;
    private ChatBroadcastWsClient chatBroadcastWsClient;

    private int giftDanmakuType = 0;
    private int scDanmakuType = BaseDanmaku.TYPE_FIX_BOTTOM;


    public DanmakuDelegate(@NonNull PlaybackVideoFragment fragment, IDanmakuView danmakuView, @NonNull LiveRoom liveRoom){
        this.fragment = fragment;
        this.danmakuView = danmakuView;
        this.liveRoom = liveRoom;
    }

    public void init(){
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        maxLinesPair.put(BaseDanmaku.TYPE_FIX_TOP, 5);
        maxLinesPair.put(BaseDanmaku.TYPE_FIX_BOTTOM, 5);
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);

        danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair)
                .setDanmakuTransparency(0.85f)
                .setDanmakuMargin(5);

        danmakuParser = new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        };
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.showFPS(BuildConfig.DEBUG);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                danmakuView.start();
                initChatBroadcast();
            }

            @Override
            public void updateTimer(DanmakuTimer danmakuTimer) {
            }

            @Override
            public void danmakuShown(BaseDanmaku baseDanmaku) {
            }

            @Override
            public void drawingFinished() {
            }
        });
        danmakuView.prepare(danmakuParser, danmakuContext);
    }

    private void initChatBroadcast(){
        chatBroadcastWsClient = new ChatBroadcastWsClient(liveRoom.getId(), liveRoom.getDanmuWsToken());
        chatBroadcastWsClient.setCallBack(new ChatBroadcastWsClient.CallBack() {
            @Override
            public void onStart() {
                if(BuildConfig.DEBUG){
                    FragmentActivity activity = fragment.requireActivity();
                    Toast.makeText(activity,
                                    activity.getString(R.string.toast_msg_danmu_connect_success),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent) {
                addDanmaku(text, textSize, textColor, textShadowTransparent);
            }

            @Override
            public void onReceiveSuperChatMessage(String message, String messageFontColor, String uname) {
                if(scDanmakuType == BaseDanmaku.TYPE_FIX_BOTTOM
                        || scDanmakuType == BaseDanmaku.TYPE_FIX_TOP
                        || scDanmakuType == BaseDanmaku.TYPE_SCROLL_RL
                        || giftDanmakuType == BaseDanmaku.TYPE_SCROLL_LR
                        || scDanmakuType == BaseDanmaku.TYPE_SPECIAL) {
                    String text = "[SC]" + uname + ":" + message;
                    addDanmaku(text, 25, Color.parseColor(messageFontColor), false, scDanmakuType);
                }
            }

            @Override
            public void onReceiveSendGift(String action, String giftName, Integer num, String uname) {
                if(giftDanmakuType == BaseDanmaku.TYPE_FIX_BOTTOM
                        || giftDanmakuType == BaseDanmaku.TYPE_FIX_TOP
                        || giftDanmakuType == BaseDanmaku.TYPE_SCROLL_RL
                        || giftDanmakuType == BaseDanmaku.TYPE_SCROLL_LR
                        || giftDanmakuType == BaseDanmaku.TYPE_SPECIAL){
                    String text = uname + action + giftName + "X" + num;
                    addDanmaku(text, 10, Color.WHITE, false, giftDanmakuType);
                }
            }

            @Override
            public void onReceiveOtherMessage(String message) {
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                FragmentActivity activity = fragment.requireActivity();
                Toast.makeText(activity,
                                String.format(activity.getString(R.string.toast_msg_danmu_connect_error), reason),
                                Toast.LENGTH_LONG)
                        .show();
                if(fragment.isPlaying()){
                    startChatBroadcastWsClient();
                }
            }
        });
        startChatBroadcastWsClient();
    }

    private void startChatBroadcastWsClient(){
        if(chatBroadcastWsClient != null){
            try {
                chatBroadcastWsClient.start();
            }
            catch (Exception error){
                Log.d(TAG, "startChatBroadcastWsClient: ", error);
                FragmentActivity activity = fragment.requireActivity();
                Toast.makeText(activity,
                                String.format(activity.getString(R.string.toast_msg_danmu_connect_error), error.getLocalizedMessage()),
                                Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void addDanmaku(String content, float textSize, int textColor, boolean textShadowTransparent) {
        addDanmaku(content, textSize, textColor, textShadowTransparent, BaseDanmaku.TYPE_SCROLL_RL);
    }

    private void addDanmaku(String content, float textSize, int textColor, boolean textShadowTransparent, int type){
        if(danmakuContext != null && danmakuView != null && danmakuParser !=null){
            BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(type);
            if (danmaku != null) {
                danmaku.text = content;
                danmaku.padding = 0;
                danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
                danmaku.isLive = true;
                danmaku.setTime(danmakuView.getCurrentTime() + 500);
                danmaku.textSize = textSize * (danmakuParser.getDisplayer().getDensity() - 0.6f);
                danmaku.textColor = textColor;
                danmaku.textShadowColor = textShadowTransparent ? Color.TRANSPARENT : Color.BLACK;
                danmakuView.addDanmaku(danmaku);
            }
        }
    }

    public void pause(){
        if(danmakuView != null && !danmakuView.isPaused()){
            danmakuView.pause();
        }
    }

    public void resume(){
        if (danmakuView != null) {
            if(danmakuView.isPrepared() && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            init();
        }
    }

    public void setPlayer(boolean isPlaying){
        if(isPlaying){
            if(danmakuView != null && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            pause();
        }
    }

    public void setGiftDanmakuType(int giftDanmakuType) {
        this.giftDanmakuType = giftDanmakuType;
    }

    public void setScDanmakuType(int scDanmakuType) {
        this.scDanmakuType = scDanmakuType;
    }

    public void danmakuReleaseSwitch(){
        FragmentActivity activity = fragment.requireActivity();
        if(danmakuView != null){
            release();
            Toast.makeText(activity,
                            activity.getString(R.string.toast_msg_danmu_open),
                            Toast.LENGTH_SHORT)
                    .show();
        }else{
            init();
            Toast.makeText(activity,
                            activity.getString(R.string.toast_msg_danmu_stop),
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void release(){
        if(chatBroadcastWsClient != null){
            chatBroadcastWsClient.close();
            chatBroadcastWsClient = null;
        }
        if(danmakuView != null){
            danmakuView.hide();
            danmakuView.removeAllDanmakus(true);
            danmakuView.release();
            danmakuView = null;
        }
        if(danmakuContext != null){
            danmakuContext = null;
        }
        if(danmakuParser != null){
            danmakuParser = null;
        }
    }
}
