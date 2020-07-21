package com.su.ehobesmallsamkoon.ui.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import java.util.ArrayList;
import java.util.List;

public class WiFiAdapter extends BaseAdapter {
    private Context context;
    private List<ScanResult> datas = new ArrayList<>();

    public WiFiAdapter(Context context, List<ScanResult> list) {
            this.context = context;
            this.datas = list;
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
        view = LayoutInflater.from(context).inflate(R.layout.item_net_work,viewGroup,false);
        TextView wifiNam = view.findViewById(R.id.item_net_tv);
        ImageView img = view.findViewById(R.id.item_net_img);
        wifiNam.setText(datas.get(i).SSID);
        String capabilities = datas.get(i).capabilities;
        Log.i("TAG","capabilities=" + capabilities);
        if (!TextUtils.isEmpty(capabilities)) {
            if (capabilities.contains("WPA") || capabilities.contains("wpa")||capabilities.contains("WEP") || capabilities.contains("wep")) {
                //有加密过

                Log.i("TAG", "wpa");
                if(datas.get(i).level<=0&&datas.get(i).level>=-55)
                    img.setImageResource(R.mipmap.jiami_wifi);
                else if(datas.get(i).level<=-55&&datas.get(i).level>=-70)
                    img.setImageResource(R.mipmap.wifi_zhong);
                else
                    img.setImageResource(R.mipmap.wifi_luo);
            }  else {
                //没加密过
                if(datas.get(i).level<=0&&datas.get(i).level>=-55)
                    img.setImageResource(R.mipmap.wifi);
                else if(datas.get(i).level<=-55&&datas.get(i).level>=-70)
                    img.setImageResource(R.mipmap.wifi_zhong_free);
                else
                    img.setImageResource(R.mipmap.wifi_luo_free);
                Log.i("TAG", "no");
            }
        }
        return view;
    }
}
