package com.su.ehobesmallsamkoon.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.api.HostUrl;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.bean.UserBean;
import com.su.ehobesmallsamkoon.bean.respond.UserInform;
import com.su.ehobesmallsamkoon.presenter.UsersPresenter;
import com.su.ehobesmallsamkoon.ui.adapter.UsersAdapter;
import com.su.ehobesmallsamkoon.ui.dialog.DelDialog;
import com.su.ehobesmallsamkoon.ui.dialog.LoginErrorDialog;
import com.su.ehobesmallsamkoon.util.OkhttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UsersActivity extends ToolbarActivity<UsersPresenter>  implements DelDialog.DeleteListener {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.adduser)
    ImageView adduser;
    private TextView tvDel;
    private DelDialog delDialog;
    private LoginErrorDialog  dialog;
    private UsersAdapter usersAdapter;
    private ArrayList<UserBean> userBeans = new ArrayList<>();
    private FrameLayout hidden;
    public static void start(Context context) {
       // context.startActivity(new Intent(context,UsersActivity.class));
        context.startActivity(new Intent(context,UserInfoActivity.class));
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
        initData();
        delDialog = new DelDialog(this);
        dialog = new LoginErrorDialog(this);
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
                hidden =view.findViewById(R.id.more);
                hidden.setVisibility(View.VISIBLE);
                delDialog.setMessage(userBeans.get(position).getUserName());
               //usersAdapter.showMore(position);
                dialogShow(view);
                return true;
            }
        });
        usersAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.cancel:
                        //TODO 取消
                        view.findViewById(R.id.more).setVisibility(View.GONE);
                        break;
                    case R.id.delete:
                        //TODO 删除
                        Log.e(TAG, "onItemChildClick: delete");
                        break;
                }
            }
        });
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("termnum", Constant.TERM_ID);
        map.put("operateuserid",1);
        map.put("index",1);
        map.put("pagesize",20);
        OkhttpUtils.post(HostUrl.userInform,OkhttpUtils.mapToStr(map),handler);
    }

    @OnClick({R.id.adduser})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.adduser:
                //TODO 添加用户
                break;
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(UsersActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Log.i("TAG",msg.obj.toString());
                    UserInform userInform = new Gson().fromJson(msg.obj.toString(), UserInform.class);
                    userBeans.clear();
                    for(UserInform.DataBean bean:userInform.getData()){
                       userBeans.add(new UserBean(bean.getUserName(),bean.getID()+""));
                    }
                    usersAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private void dialogShow(View view) {
        tvDel = view.findViewById(R.id.delete);
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delDialog.show();
            }
        });
    }

    @Override
    public void onDeleteLisetner(String message) {
        Toast.makeText(this, "删除:"+message+"信息成功", Toast.LENGTH_SHORT).show();
        Iterator<UserBean> iterator = userBeans.iterator();
        while(iterator.hasNext()){
          if(iterator.next().getUserName().equals(message))
              iterator.remove();
          usersAdapter.notifyDataSetChanged();
        }
        hidden.setVisibility(View.GONE);
        delDialog.dismiss();
        dialog.setSomeMsg(1,"删除成功","好的");
    }
}
