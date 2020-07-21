package com.su.ehobesmallsamkoon.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.bean.GoodMessage;
import com.su.ehobesmallsamkoon.ui.activity.DataActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataAdapter extends BaseAdapter implements DataActivity.SortListener {
    private Context context;
    private List<GoodMessage> messages;
    private int order=0;
    public DataAdapter(Context context, List<GoodMessage> messages){
            this.context = context;
            this.messages = messages;
    }
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_data,viewGroup,false);
        if(i%2==0)
            view.findViewById(R.id.item_data_bg).setBackgroundColor(Color.parseColor("#F5F5F5"));
        else
            view.findViewById(R.id.item_data_bg).setBackgroundColor(Color.parseColor("#FFFFFF"));
        TextView name = view.findViewById(R.id.item_data_name);
        TextView specifiaction = view.findViewById(R.id.item_data_specification);
        TextView supplier =view.findViewById(R.id.item_data_supplier);
        TextView date = view.findViewById(R.id.item_data_date);
        TextView count = view.findViewById(R.id.item_data_count);
        name.setText(messages.get(i).getGoodName());
        specifiaction.setText(messages.get(i).getSpecification());
        supplier.setText(messages.get(i).getSupplier());
        date.setText(messages.get(i).getDate());
        count.setText(messages.get(i).getCount());
        if(messages.get(i).getState()==3){
            date.setTextColor(Color.parseColor("#FF6A6A"));
        }
        return view;
    }

    @Override
    public void onSortListener() {
        order++;
        Collections.sort(messages, new Comparator<GoodMessage>() {
            @Override
            public int compare(GoodMessage goodMessage, GoodMessage t1) {
                return order%2==0 ? Integer.parseInt(goodMessage.getCount())-Integer.parseInt(t1.getCount()) : Integer.parseInt(t1.getCount())-Integer.parseInt(goodMessage.getCount());
            }
        });
        notifyDataSetChanged();
    }
}
