package com.su.ehobesmallsamkoon.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.app.App;
import com.su.ehobesmallsamkoon.base.BaseFragment;
import com.su.ehobesmallsamkoon.presenter.FingerPresenter;
import com.su.ehobesmallsamkoon.util.SoundPoolUtil;
import com.xgzx.veinmanager.VeinApi;

import butterknife.BindView;
import butterknife.OnClick;

public class FingerFragment extends BaseFragment<FingerPresenter> {
    private static final String TAG = "FingerFragment";
    @BindView(R.id.finger)
    ImageView finger;
    private SoundPoolUtil soundPoolUtil = new SoundPoolUtil();
    private static String sChara = ""; //当前采集的特征
    private String sThreadMsg = "";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_finger;
    }

    @Override
    protected void initialize() {

    }

    @OnClick(R.id.finger)
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.finger:
                GetChara();
                break;
        }
    }

    Thread getThread = new Thread(new Runnable() {
        @Override
        public void run() {
            GetChara();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    //采集特征方法
    public void GetChara() {
        if(App.DevHandle <= 0)
        {
//            DebugMsg("请先连接设备");
            return;
        }
//        soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_PUTFINGER, true);
        sChara = VeinApi.GetVeinChara(App.DevHandle, 60000); //通过指静脉设备采集特征，等待手指放入超时为6秒
        Log.e(TAG, "GetChara: "+sChara );
        if(sChara.length() < 10)
        {
            int ret = 0;
            try {
                ret = Integer.parseInt(sChara);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//            soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_BEEP11, true);
            if(ret == -16) {
                sThreadMsg = "此设备不支持特征采集";
//                DebugMsg(sThreadMsg);
            } else if(ret == -11) {
                sThreadMsg = "手指检测超时";
//                DebugMsg(sThreadMsg);
            } else if(ret == -17) {
                sThreadMsg = "请正确放置手指";
                VeinApi.PrintfDebug(sThreadMsg);
                try {
                    Thread.sleep(500); //等语音播完
                } catch (InterruptedException e) {
                }
//                soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_PUTFINGER_RIGHT, true);
            } else {
                sThreadMsg = "特征采集失败:" + sChara;
//                DebugMsg(sThreadMsg);
            }
        } else {
            soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
            sThreadMsg = "特征采集成功";
//            DebugMsg(sThreadMsg);
//            boolean find = false;
//            for (String temp : temps) {
//                if (Verify(temp)) {
//                    Log.e(TAG, "Verify: 验证成功" );
//                    soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_VERIFY_SUCCESS, true);
//                    find = true;
//                    break;
//                }
//            }
//            if (!find) {
//                Log.e(TAG, "Verify: 验证失败" );
//                soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_VERIFY_FAIL, true);
//            }
//            if(ServerSocket > 0)
//            {
//                VeinApi.FVSocketSendPack(ServerSocket, 0x02, sChara);
//            }
        }
    }
}
