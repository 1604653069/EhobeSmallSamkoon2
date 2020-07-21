package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseActivity;
import com.su.ehobesmallsamkoon.frame.RFIDFrame;
import com.su.ehobesmallsamkoon.presenter.OpenAndClosePresenter;
import com.su.ehobesmallsamkoon.serial_port.InstructionsUtils;
import com.su.ehobesmallsamkoon.serial_port.SerialPortUart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenAndCloseActivity extends BaseActivity<OpenAndClosePresenter> {

    @BindView(R.id.logintip)
    LinearLayout logintip;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.img_dorr)
    ImageView imgDorr;

    public static void start(Context context) {
        context.startActivity(new Intent(context, OpenAndCloseActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oc;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        //发送开门指令
        SerialPortUart.getInstance().sendSerialPort(InstructionsUtils.getInstruction(InstructionsUtils.OPEN_DOOR, "010203040507"), true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerRFFrame(RFIDFrame frame) {
        imgDorr.setImageResource(R.mipmap.readingdata);
        if (frame.data.length() == 0) {
            Log.i("TAG","单片机数据为空");
        } else {
            if (frame.in != 0){
                Log.i("TAG","数据增加了"+frame.in);
                Log.i("TAG","增加的数据为:"+frame.getInList());
            }
            if (frame.out != 0){
                Log.i("TAG","数据减少了"+frame.out);
                Log.i("TAG","减少的数据为:"+frame.getOutList());
            }
        }
        startActivity(new Intent(OpenAndCloseActivity.this,DataActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
