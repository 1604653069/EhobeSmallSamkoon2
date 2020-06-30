package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.util.Log;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseActivity;
import com.su.ehobesmallsamkoon.bean.respond.TerminalInfo;
import com.su.ehobesmallsamkoon.network.RxCallback;
import com.su.ehobesmallsamkoon.presenter.DetectionPresenter;

import java.util.ArrayList;

public class DetectionActivity extends BaseActivity<DetectionPresenter> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detection;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Context context = this;
        getPresenter().getTerminals().subscribe(new RxCallback<ArrayList<TerminalInfo>>() {
            @Override
            public void onSuccess(ArrayList<TerminalInfo> data) {
                if (data.size() > 0) {
                    Log.e(TAG, "onSuccess: " + data);
                    LoginActivity.start(context);
                } else {
                    ActivationActivity.start(context);
                }
            }
        });
    }

    @Override
    protected void initialize() {

    }
}
