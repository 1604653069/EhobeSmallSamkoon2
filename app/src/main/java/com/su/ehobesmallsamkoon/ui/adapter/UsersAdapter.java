package com.su.ehobesmallsamkoon.ui.adapter;


import android.view.View;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseRVAdapter;
import com.su.ehobesmallsamkoon.base.BaseRVHolder;
import com.su.ehobesmallsamkoon.bean.UserBean;

public class UsersAdapter extends BaseRVAdapter<UserBean> {

    public UsersAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void onBindVH(BaseRVHolder holder, UserBean data, int position) {
        holder.setText(R.id.userName,"用户："+data.getUserName())
        .setText(R.id.userId,"ID："+data.getUserName())
        .addOnClickListener(R.id.cancel)
        .addOnClickListener(R.id.delete);
    }

    //显示更多
    public void showMore(int position){
        getRecyclerView().getChildAt(position).setVisibility(View.VISIBLE);
    }

    //隐藏更多
    public void hideMore(int position){
        getRecyclerView().getChildAt(position).setVisibility(View.GONE);
    }
}
