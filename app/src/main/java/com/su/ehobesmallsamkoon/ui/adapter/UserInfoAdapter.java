package com.su.ehobesmallsamkoon.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.bean.respond.UserInform;

public class UserInfoAdapter extends BaseAdapter {
    private Context context;
    private UserInform userInform;
    public UserInfoAdapter(Context context,UserInform userInform){
        this.context = context;
        this.userInform = userInform;
    }
    @Override
    public int getCount() {
        return userInform.getData().size();
    }

    @Override
    public Object getItem(int i) {
        return userInform.getData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_user,viewGroup,false);
        TextView name = view.findViewById(R.id.userName);
        TextView userId =view.findViewById(R.id.userId);
        name.setText("用户ID:"+userInform.getData().get(i).getUserName());
        userId.setText("序号:"+userInform.getData().get(i).getID()+"");
        return view;
    }

}
