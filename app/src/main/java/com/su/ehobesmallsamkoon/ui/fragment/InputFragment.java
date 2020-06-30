package com.su.ehobesmallsamkoon.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseFragment;
import com.su.ehobesmallsamkoon.bean.respond.LoginResults;
import com.su.ehobesmallsamkoon.network.RxCallback;
import com.su.ehobesmallsamkoon.presenter.InputPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InputFragment extends BaseFragment<InputPresenter> {
    private static final String TAG = "InputFragment";
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.passWord)
    EditText passWord;
    @BindView(R.id.login)
    Button login;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_input;
    }

    @Override
    protected void initialize() {

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
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
