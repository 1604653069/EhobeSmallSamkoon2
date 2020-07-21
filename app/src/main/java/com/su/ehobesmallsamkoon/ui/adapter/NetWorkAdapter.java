package com.su.ehobesmallsamkoon.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.bean.NetWorkBean;

import java.util.ArrayList;
import java.util.List;

public class NetWorkAdapter extends BaseAdapter {
    private Context context;
    private List<NetWorkBean> datas= new ArrayList<>();
    public NetWorkAdapter(Context context,List<NetWorkBean> datas){
        this.context =context;
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view  = LayoutInflater.from(context).inflate(R.layout.item_net_work,viewGroup,false);
        TextView title= view.findViewById(R.id.item_net_tv);
        ImageView img= view.findViewById(R.id.item_net_img);
        title.setText(datas.get(i).getName());
        img.setImageResource(datas.get(i).getImage());
        return view;
    }
}
