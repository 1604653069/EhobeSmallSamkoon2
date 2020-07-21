package com.su.ehobesmallsamkoon.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseFragment;
import com.su.ehobesmallsamkoon.bean.respond.LoginRespon;
import com.su.ehobesmallsamkoon.presenter.InputPresenter;
import com.su.ehobesmallsamkoon.ui.activity.OpenAndCloseActivity;
import com.su.ehobesmallsamkoon.ui.dialog.LoginErrorDialog;
import com.su.ehobesmallsamkoon.ui.widget.LoginButton;
import com.su.ehobesmallsamkoon.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InputFragment extends BaseFragment<InputPresenter> implements LoginButton.OnAnimationButtonClickListener {
    private static final String TAG = "InputFragment";
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.passWord)
    EditText passWord;
    @BindView(R.id.login)
    LoginButton login;
    Unbinder unbinder;
    private LoginErrorDialog mMyDialog;
    private LoginListener loginListener;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_input;
    }

    @Override
    protected void initialize() {
        mMyDialog = new LoginErrorDialog(getContext());
        loginListener = (LoginListener) mContext;
        login.setAnimationButtonListener(this);
    }

    @OnClick({R.id.login})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.login:
                //登录
//                getPresenter().login("1","1").subscribe(new RxCallback<LoginResults>() {
//                    @Override
//                    public void onSuccess(LoginResults data) {
//                        Log.e(TAG, "onSuccess: "+data);
//                    }
//                });
                isEmpty();
                login.start();
                getPresenter().login(userName.getText().toString(),passWord.getText().toString(),handler);
                break;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAnimationStart() {

    }

    @Override
    public void onAnimationFinish() {

    }

    @Override
    public void onAnimationCancel() {

    }

    public interface LoginListener{
       void onSuccess(String username);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mMyDialog.show();
                    break;
                case 2:
                    Log.i("TAG",msg.obj.toString());
                    LoginRespon respon = new Gson().fromJson(msg.obj.toString(), LoginRespon.class);
                    if(respon.getMsg().equals("ok")){
                        loginListener.onSuccess(respon.getData().get(0).getUserName());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getContext(), OpenAndCloseActivity.class));
                            }
                        },2000);
                    }else{
                        ToastUtil.show("登录失败",500);
                        login.reset();
                        mMyDialog.show();
                    }
                    break;
            }

        }
    };
    private void isEmpty() {
        if(TextUtils.isEmpty(userName.getText().toString())||TextUtils.isEmpty(passWord.getText().toString())){
            mMyDialog.show();
            return;
        }
    }
}
