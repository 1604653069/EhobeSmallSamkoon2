package com.su.ehobesmallsamkoon.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;

public class DelDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private View mView;
    private LinearLayout close;
    private TextView delTx;
    private TextView closeTx;
    private TextView msg;
    private String message;
    private DeleteListener deleteListener;
    public DelDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        mContext = context;
        mView = getLayoutInflater().inflate(R.layout.dialog_user_del, null);
        initView(mView);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        deleteListener = (DeleteListener) context;
    }

    private void initView(View mView) {
        close = mView.findViewById(R.id.dialog_del_user_close);
        msg = mView.findViewById(R.id.dialog_user_del_msg);
        delTx = mView.findViewById(R.id.dialog_del_user_close_tx);
        closeTx = mView.findViewById(R.id.dialog_del_user_canle);
        intiListener();
    }

    private void intiListener() {
        closeTx.setOnClickListener(this);
        delTx.setOnClickListener(this);
        close.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_del_user_close:
            case R.id.dialog_del_user_canle:
                dismiss();
                return;
            case R.id.dialog_del_user_close_tx:
                deleteListener.onDeleteLisetner(getMessage());
                return;

        }
    }
    public void setMessage(String message){
        this.message = message;
        msg.setText(message+"的用户ID");
    }
    public String getMessage(){
        return message;
    }

    public  interface DeleteListener{
        void onDeleteLisetner(String message);
    }
}
