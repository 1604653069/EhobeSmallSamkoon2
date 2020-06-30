package com.su.ehobesmallsamkoon.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.util.BitmapUtil;

import java.io.IOException;

import butterknife.Unbinder;

//自定义毛玻璃背景Dialog
public class FrostedDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "FrostedDialog";
    private View mView;
    private Unbinder unbinder;
    private onViewClickListener onViewClickListener;
    private onTransferListener onTransferListener;
    private Bitmap blurBg;
    private boolean selfClose = true;
    public FrostedDialog(@NonNull Context context) {
        super(context);
    }

    public FrostedDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public FrostedDialog(@NonNull Context context, int layoutId, ViewGroup root) {
        super(context, R.style.SquareEntranceDialogStyle);
        mView = LayoutInflater.from(context).inflate(layoutId, root);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//      去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//      去除状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(mView, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,
                ViewGroup.MarginLayoutParams.MATCH_PARENT));
        initView();
    }

    private void initView() {
        if (mView.findViewById(R.id.close) != null && selfClose) {
            mView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    //设置是否自己处理关闭
    public FrostedDialog setSelfClose(boolean selfClose) {
        this.selfClose = selfClose;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (onViewClickListener != null) {
            onViewClickListener.onClick(v);
        }
    }

    public FrostedDialog setViewClickListener(FrostedDialog.onViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
        return this;
    }

    public FrostedDialog setOnTransferListener(FrostedDialog.onTransferListener onTransferListener) {
        this.onTransferListener = onTransferListener;
        return this;
    }

    public interface onViewClickListener {
        void onClick(View view);
    }

    public interface onTransferListener {
        //传递操作code
        void onTransfer(int key);

        //传递字符串
        void onTransfer(String str);

        //传递对象
        void onTransfer(Object object);
    }

    //重写show方法，在show之前设置dialog背景
    @Override
    public void show() {
        showDialog();
    }

    private void showDialog() {
        Log.e(TAG, "showDialog: ");
        this.blurBg = BitmapUtil.screenShots(((ContextWrapper) getContext()).getBaseContext());
        this.getWindow().setBackgroundDrawable(new BitmapDrawable(this.getContext().getResources(), blurBg));
        super.show();
    }


    @Override
    public void dismiss() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if (blurBg != null && !blurBg.isRecycled()) {
            blurBg.recycle();
        }
        super.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //为某一控件添加监听事件
    public FrostedDialog addOnClickListener(int id) {
        ((View) mView.findViewById(id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClickListener.onClick(v);
            }
        });
        return this;
    }

    //设置某一控件的Text
    public FrostedDialog setText(int id, String str) {
        TextView textView = mView.findViewById(id);
        if (textView != null) {
            textView.setText(str);
        } else {
            Log.e(TAG, "setText: view is null!");
        }
        return this;
    }

    //设置TextView资源
    public FrostedDialog setText(int id, int resId) {
        TextView textView = mView.findViewById(id);
        if (textView != null) {
            textView.setText(getContext().getResources().getString(resId));
        } else {
            Log.e(TAG, "setText: view is null!");
        }
        return this;
    }

    //获取某一控件的Text
    public String getText(int id) {
        EditText editText = (EditText) mView.findViewById(id);
        return editText.getText().toString().trim();
    }

    //设置ImageView的Bitmap
    public FrostedDialog setImageBitmap(int id, Bitmap bitmap) {
        ImageView imageView = (ImageView) mView.findViewById(id);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    //设置ImageView图片
    public FrostedDialog setImage(int id, String URL) {
        Glide.with(getContext()).load(URL).into((ImageView) mView.findViewById(id));
        return this;
    }

    //设置ImageView图片资源
    public FrostedDialog setImageResources(int id, int resID) {
        ((ImageView) mView.findViewById(id)).setImageResource(resID);
        return this;
    }

    //设置GIF图片
//    public FrostedDialog setGIF(int id, int resID) {
//        try {
//            GifDrawable gifDrawable = new GifDrawable(getContext().getResources(), R.drawable.scancode);
//            ((GifImageView) mView.findViewById(id)).setImageDrawable(gifDrawable);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return this;
//    }

    //设置字体颜色
    public FrostedDialog setTextColor(int id, int color) {
        ((TextView) mView.findViewById(id)).setTextColor(color);
        return this;
    }

    //设置字体颜色
    public FrostedDialog setTextColor(int id, String color) {
        ((TextView) mView.findViewById(id)).setTextColor(Color.parseColor(color));
        return this;
    }

    //设置是否可见
    public FrostedDialog setVisibility(int id, boolean visible){
        if (visible){
            ((View) mView.findViewById(id)).setVisibility(View.VISIBLE);
        }else{
            ((View) mView.findViewById(id)).setVisibility(View.GONE);
        }
        return this;
    }

    //设置背景
    public FrostedDialog setBackgroundFromDrawable(int id,int drawableId){
        if (drawableId == 0) {
            mView.findViewById(id).setBackground(null);
        }else {
            mView.findViewById(id).setBackground(getContext().getResources().getDrawable(drawableId));
        }
        return this;
    }

    //设置输入框hintText
    public FrostedDialog setHintText(int id,String hintText){
        ((EditText)mView.findViewById(id)).setHint(hintText);
        return this;
    }

    //获取Dialog的View
    public View getView() {
        return mView;
    }
}
