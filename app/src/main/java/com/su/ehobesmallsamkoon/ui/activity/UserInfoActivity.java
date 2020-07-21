package com.su.ehobesmallsamkoon.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.bean.respond.UserInform;
import com.su.ehobesmallsamkoon.presenter.UsersPresenter;
import com.su.ehobesmallsamkoon.ui.adapter.UserInfoAdapter;
import com.su.ehobesmallsamkoon.ui.dialog.DelDialog;
import com.su.ehobesmallsamkoon.ui.dialog.LoginErrorDialog;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends ToolbarActivity<UsersPresenter> implements View.OnClickListener,DelDialog.DeleteListener {

    @BindView(R.id.user_gridview)
    GridView userGridview;
    @BindView(R.id.img_beifen)
    ImageView imgBeifen;
    @BindView(R.id.img_tongbu)
    ImageView imgTongbu;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    private UserInfoAdapter adapter;
    private UserInform userInform;
    private View parent;
    private DelDialog delDialog;
    private LoginErrorDialog dialog;
    private  int index;
    private boolean isUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initialize() {
        delDialog = new DelDialog(this);
        dialog  = new LoginErrorDialog(this);
        getPresenter().getDate(handler);
        intLisnter();
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
    private void parsetData(String data) {
        userInform = new Gson().fromJson(data, UserInform.class);
        adapter = new UserInfoAdapter(getContext(),userInform);
        userGridview.setHorizontalSpacing(10);
        userGridview.setVerticalSpacing(10);
        userGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        userGridview.setAdapter(adapter);
    }
    public void intLisnter(){
        imgAdd.setOnClickListener(this);
        imgBeifen.setOnClickListener(this);
        imgTongbu.setOnClickListener(this);
        userGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                parent =view.findViewById(R.id.more);
                parent.setVisibility(View.VISIBLE);
                view.findViewById(R.id.cancel).setOnClickListener(UserInfoActivity.this::onClick);
                view.findViewById(R.id.delete).setOnClickListener(UserInfoActivity.this::onClick);

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                delDialog.setMessage(userInform.getData().get(index).getUserName());
                delDialog.show();

            case R.id.cancel:
                parent.setVisibility(View.GONE);
                break;
            case R.id.img_add:
                startActivity(new Intent(UserInfoActivity.this,CheckInActivity.class));
                finish();
                break;
            case R.id.img_tongbu:
            case R.id.img_beifen:
                dialog.setTipRota(3,"正在备份","取消");
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.clearRotato();
                        dialog.setSomeMsg(1,"备份成功","好的");
                        dialog.show();
                        autoDismiss();
                    }
                },3000);
                break;
        }
    }

    @Override
    public void onDeleteLisetner(String message) {
        getPresenter().deleteUser(userInform.getData().get(index).getID()+"","1",handler);
        removeUserData(message);
        adapter.notifyDataSetChanged();
        delDialog.dismiss();
    }

    private void removeUserData(String message){
        List<UserInform.DataBean> data = userInform.getData();
        Iterator<UserInform.DataBean> iterator = data.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getUserName().equals(message)){
                iterator.remove();
            }
        }
    }
    private void showSucDialog() {
        dialog.setTipIcon(R.mipmap.sues);
        dialog.setTipMsg("删除成功");
        dialog.setBottomTip("好的");
        dialog.show();
    }
    private void autoDismiss(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(dialog.isShowing())
                dialog.dismiss();
            }
        },3000);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    break;
                case 2:
                    if(!isUserData){
                        parsetData(msg.obj.toString());
                        isUserData = true;
                        return;
                    }
                    showSucDialog();
                    autoDismiss();
                    break;
            }
        }
    };
}