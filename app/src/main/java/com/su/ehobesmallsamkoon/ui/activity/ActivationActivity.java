package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.bean.respond.Dept;
import com.su.ehobesmallsamkoon.network.RxCallback;
import com.su.ehobesmallsamkoon.presenter.ActivationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivationActivity extends ToolbarActivity<ActivationPresenter> {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.submit)
    Button submit;

    private List<String> deptList = new ArrayList<>();
    private String deptNumber;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ActivationActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activation;
    }

    @OnClick(R.id.submit)
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.submit:
                if (deptNumber!=null) {
                    getPresenter().activation(deptNumber).subscribe(new RxCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            finish();
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void initialize() {
        Context context = this;
        getPresenter().getDept().subscribe(new RxCallback<ArrayList<Dept>>() {
            @Override
            public void onSuccess(ArrayList<Dept> data) {
                for (Dept datum : data) {
                    deptList.add(datum.getDeptName());
                }
                ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(context, R.layout.item_spinner_blacktext, deptList);
                deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(deptAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        deptNumber = data.get(i).getDeptNum();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("设备激活")
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
