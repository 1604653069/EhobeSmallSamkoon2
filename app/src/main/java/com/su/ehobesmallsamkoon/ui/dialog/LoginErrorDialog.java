package com.su.ehobesmallsamkoon.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;

public class LoginErrorDialog extends Dialog implements View.OnClickListener {
    private View mView;
    private LinearLayout exit;
    private TextView reset;
    private Context mContext;
    private ImageView tipIcon;
    private TextView tipMsg;
    private LinearLayout doubleTip;
    private TextView leftTip;
    private TextView rightTip;
    private String tipMessage;
    private String leftName;
    private String rightName;
    private Animation animation;
    private boolean isRing;
    private int[] imgsId ={R.mipmap.fail,R.mipmap.sues,R.mipmap.warning,R.mipmap.ring};
    private OnLeftRightClickListener leftRightClickListener;
    public LoginErrorDialog(Context context){
        super(context, R.style.DialogTheme);
        mContext = context;
        mView = getLayoutInflater().inflate(R.layout.dialog_login_error, null);
        initView(mView);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        animation = AnimationUtils.loadAnimation(context,R.anim.rotate_anim);
    }
    private void initView(View view){
        exit = view.findViewById(R.id.dialog_login_error_exit);
        reset = view.findViewById(R.id.dialog_login_error_reset);
        tipIcon = view.findViewById(R.id.dialog_tip_icon);
        tipMsg = view.findViewById(R.id.dialog_tip_msg);
        doubleTip = view.findViewById(R.id.dialog_tip_double);
        leftTip = view.findViewById(R.id.dialog_tip_left_name);
        rightTip = view.findViewById(R.id.dialog_tip_right_name);
        leftTip.setOnClickListener(this);
        rightTip.setOnClickListener(this);
        exit.setOnClickListener(this);
        reset.setOnClickListener(this);
        this.setCancelable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_login_error_exit:
            case R.id.dialog_login_error_reset:
                if(isRing){
                    tipIcon.clearAnimation();
                    isRing = false;
                }
                dismiss();
                break;
            case R.id.dialog_tip_left_name:
                leftRightClickListener.onLeftClickListener();
                break;
            case R.id.dialog_tip_right_name:
                leftRightClickListener.onRightClickListener();
                break;
        }
    }

    @Override
    public void show() {
        /*禁止弹出虚拟按键*/
            if (this.getWindow() != null) {
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                super.show();
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
    }

    @Override
    public void dismiss() {
        /*禁止弹出虚拟按键*/
        if (this.getWindow() != null) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            super.dismiss();
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }
    public void setTipIcon(int iconId){
        this.tipIcon.setImageResource(iconId);
    }
    public void setTipMsg(String tipMsg){
        this.tipMessage = tipMsg;
        this.tipMsg.setText(tipMsg);
    }
    public void setLeftName(String leftName){
        this.leftName = leftName;
        this.leftTip.setText(leftName);
    }
    public void setRightName(String rightName){
        this.rightName = rightName;
        this.rightTip.setText(rightName);
    }
    public void setBottomTip(String bottomTip){
        this.reset.setText(bottomTip);
    }
    public void lrisShow(boolean b){
        if (b){
            doubleTip.setVisibility(View.VISIBLE);
            reset.setVisibility(View.GONE);
        }else{
            doubleTip.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
        }
    }

    public void setSomeMsg(int type,String tipMessage){
        tipIcon.setImageResource(imgsId[type]);
        setTipMsg(tipMessage);
        show();
    }
    public void setSomeMsg(int type,String tipMessage,String leftName,String rightName){
       setSomeMsg(type,tipMessage);
       setLeftName(leftName);
       setRightName(rightName);
       lrisShow(true);
       show();
    }
    public void setSomeMsg(int type,String tipMessage,String bottomMsg){
        setSomeMsg(type,tipMessage);
        setBottomTip(bottomMsg);
        show();
    }
    public void setTipRota(int type,String tipMessage,String bottomMsg){
        setSomeMsg(type,tipMessage,bottomMsg);
        tipIcon.startAnimation(animation);
        isRing =true;
    }
    public void clearRotato(){
        tipIcon.clearAnimation();
    }
    public interface OnLeftRightClickListener{
        void onLeftClickListener();
        void onRightClickListener();
    }
    public void setLeftRightClickListener(OnLeftRightClickListener onLeftRightClickListener){
        this.leftRightClickListener = onLeftRightClickListener;
    }
}
