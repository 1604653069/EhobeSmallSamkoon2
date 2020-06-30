package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.bean.UserBean;
import com.su.ehobesmallsamkoon.presenter.UsersPresenter;
import com.su.ehobesmallsamkoon.ui.adapter.UsersAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class UsersActivity extends ToolbarActivity<UsersPresenter> {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.adduser)
    ImageView adduser;

    private UsersAdapter usersAdapter;
    private ArrayList<UserBean> userBeans = new ArrayList<>();

    public static void start(Context context) {
        context.startActivity(new Intent(context,UsersActivity.class));
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("人员列表")
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .setBackgroundColor(Color.parseColor("#FFFFFF"))
                .addLeftImage(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_users;
    }

    @Override
    protected void initialize() {
        for (int i = 0; i < 20; i++) {
            userBeans.add(new UserBean("哇哈哈","20"));
        }
        usersAdapter = new UsersAdapter(R.layout.item_user);
        usersAdapter.setNewData(userBeans);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(usersAdapter);
        usersAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                view.findViewById(R.id.more).setVisibility(View.VISIBLE);
//                usersAdapter.showMore(position);
                return true;
            }
        });
        usersAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.cancel:
                        //TODO 取消
                        mRecyclerView.getChildAt(position).findViewById(R.id.more).setVisibility(View.GONE);
                        break;
                    case R.id.delete:
                        //TODO 删除
                        Log.e(TAG, "onItemChildClick: delete");
                        break;
                }
            }
        });
    }

    @OnClick(R.id.adduser)
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.adduser:
                //TODO 添加用户
                break;
        }
    }
}
