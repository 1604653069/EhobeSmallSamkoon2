package com.su.ehobesmallsamkoon.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.su.ehobesmallsamkoon.R;

public class LoginingDialog extends Dialog {
    private Context context;

    public LoginingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoginingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.BOTTOM;
        window.clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setAttributes(params);
        View view = View.inflate(getContext(), R.layout.dialog_logining, null);
        ImageView progress_img = (ImageView)view. findViewById(R.id.iv_bg);
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.login_loading);
        //LinearInterpolator lin = new LinearInterpolator();
        // operatingAnim.setInterpolator(lin);
        progress_img.setAnimation(operatingAnim);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
    }
}
