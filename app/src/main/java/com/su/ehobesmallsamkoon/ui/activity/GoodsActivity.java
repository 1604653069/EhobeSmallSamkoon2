package com.su.ehobesmallsamkoon.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseActivity;
import com.su.ehobesmallsamkoon.presenter.GoodsPresenter;

public class GoodsActivity extends BaseActivity<GoodsPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initialize() {

    }
}