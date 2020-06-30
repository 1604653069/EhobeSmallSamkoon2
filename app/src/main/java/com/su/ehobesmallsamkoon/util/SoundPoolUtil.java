package com.su.ehobesmallsamkoon.util;

import android.media.AudioManager;
import android.media.SoundPool;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.app.App;
import com.xgzx.veinmanager.VeinApi;

import java.util.HashMap;
import java.util.Map;

public class SoundPoolUtil {
    private static SoundPool soundPool; //本机语音播放
    static Map<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private static void init(){
        soundPool = new SoundPool(12, AudioManager.STREAM_SYSTEM, 5);
        soundMap.put(1, soundPool.load(App.getInstance().getApplicationContext(), R.raw.enroll_success_00, 1)); //登记成功
        soundMap.put(2, soundPool.load(App.getInstance().getApplicationContext(), R.raw.enroll_fail_02, 1)); //登记失败
        soundMap.put(3, soundPool.load(App.getInstance().getApplicationContext(), R.raw.verify_success_33, 1)); //验证成功
        soundMap.put(4, soundPool.load(App.getInstance().getApplicationContext(), R.raw.verify_fail_32, 1)); //验证失败
        soundMap.put(5, soundPool.load(App.getInstance().getApplicationContext(), R.raw.put_finger_27, 1)); //请放手指
        soundMap.put(6, soundPool.load(App.getInstance().getApplicationContext(), R.raw.put_again_23, 1)); //请再放一次
        soundMap.put(7, soundPool.load(App.getInstance().getApplicationContext(), R.raw.put_right_26, 1)); //请正确放置手指
        soundMap.put(8, soundPool.load(App.getInstance().getApplicationContext(), R.raw.b_35, 1)); //滴1
        soundMap.put(9, soundPool.load(App.getInstance().getApplicationContext(), R.raw.b_36, 1)); //滴2
        soundMap.put(10, soundPool.load(App.getInstance().getApplicationContext(), R.raw.bb_37, 1)); //滴滴1
        soundMap.put(11, soundPool.load(App.getInstance().getApplicationContext(), R.raw.bb_38, 1)); //滴滴2
    }

    public static void PlayVoice(int id, boolean DevPlay) {
        if (soundPool == null) {
            init();
        }
        if (DevPlay)
            VeinApi.PlayDevSound(App.DevHandle, id);
        switch (id) {
            case VeinApi.XG_VOICE_ENRORLL_SUCCESS:
                soundPool.play(soundMap.get(1), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_ENRORLL_FAIL:
                soundPool.play(soundMap.get(2), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_VERIFY_SUCCESS:
                soundPool.play(soundMap.get(3), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_VERIFY_FAIL:
                soundPool.play(soundMap.get(4), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_PUTFINGER:
                soundPool.play(soundMap.get(5), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_PUTFINGER_AGAIN:
                soundPool.play(soundMap.get(6), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_PUTFINGER_RIGHT:
                soundPool.play(soundMap.get(7), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP1:
                soundPool.play(soundMap.get(8), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP2:
                soundPool.play(soundMap.get(9), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP11:
                soundPool.play(soundMap.get(10), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP22:
                soundPool.play(soundMap.get(11), 1, 1, 1, 0, 1);
                break;
        }
    }
}
