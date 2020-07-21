package com.su.ehobesmallsamkoon.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.bean.GoodMessage;
import com.su.ehobesmallsamkoon.ui.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataActivity extends AppCompatActivity {
    @BindView(R.id.img_data_out)
    ImageView imgDataOut;
    @BindView(R.id.data_tv_in_name)
    TextView dataTvInName;
    @BindView(R.id.data_ll_out)
    LinearLayout dataTvOut;
    @BindView(R.id.img_data_in)
    ImageView imgDataIn;
    @BindView(R.id.data_tv_out_name)
    TextView dataTvOutName;
    @BindView(R.id.data_ll_in)
    LinearLayout dataTvIn;
    @BindView(R.id.img_date_sort)
    LinearLayout imgDateSort;
    @BindView(R.id.img_number_sort)
    LinearLayout imgNumberSort;
    @BindView(R.id.data_listview)
    ListView dataListview;
    @BindView(R.id.data_btn_back)
    Button dataBtnBack;
    @BindView(R.id.data_btn_sure)
    Button dataBtnSure;
    @BindView(R.id.exc_count)
    TextView excCount;
    @BindView(R.id.data_title)
    LinearLayout dataTitle;
    private List<GoodMessage> messages = new ArrayList<>();
    private DataAdapter adapter;
    private int back = 0;
    private SortListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
        hideBottomUIMenu();
        initData();
        initListener();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            messages.add(new GoodMessage("复方氨酚烷胺片" + i, "mg", "浙江医药浙江医药浙江医药", "2020-07-01", random() + "", random(), random()));
        }
        adapter = new DataAdapter(this, messages);
        listener = adapter;
        dataListview.setAdapter(adapter);
    }

    public void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @OnClick({R.id.data_btn_back, R.id.data_btn_sure, R.id.data_ll_out, R.id.data_ll_in, R.id.img_number_sort})
    public void click(View view) {
        if (view.getId() == R.id.data_btn_back){
              if(back!=0){
                  change(2);
                  DataAdapter dataAdapter = new DataAdapter(this, getTypeData(2));
                  listener = dataAdapter;
                  dataListview.setAdapter(dataAdapter);
                  excCount.setVisibility(View.GONE);
                  dataTitle.setVisibility(View.VISIBLE);
                  back=0;
              }else
                  finish();

        }
        else if (view.getId() == R.id.data_btn_sure)
             Toast.makeText(this, "你点击的是确定按钮", Toast.LENGTH_SHORT).show();
        else if (view.getId() == R.id.data_ll_in) {
            change(1);
            DataAdapter dataAdapter = new DataAdapter(this, getTypeData(1));
            listener = dataAdapter;
            dataListview.setAdapter(dataAdapter);
        }
        else if (view.getId() == R.id.data_ll_out) {
            change(2);
            DataAdapter dataAdapter = new DataAdapter(this, getTypeData(2));
            listener = dataAdapter;
            dataListview.setAdapter(dataAdapter);
        }
        else if (view.getId() == R.id.img_number_sort) {
            listener.onSortListener();
        }
    }

    private int random() {
        return new Random().nextInt(3) + 1;
    }

    private void change(int type) {
        if (type == 1) {
            imgDataIn.setImageResource(R.mipmap.icon_in_sle);
            imgDataOut.setImageResource(R.mipmap.icon_out_nor);
            dataTvInName.setTextColor(Color.parseColor("#333333"));
            dataTvOutName.setTextColor(Color.parseColor("#04A8FF"));
            dataTvIn.setBackgroundResource(R.drawable.title_bg2);
            dataTvOut.setBackgroundResource(R.drawable.title_bg);
        }
        if (type == 2) {
            imgDataIn.setImageResource(R.mipmap.icon_in_nor);
            imgDataOut.setImageResource(R.mipmap.icon_out_sle);
            dataTvInName.setTextColor(Color.parseColor("#04A8FF"));
            dataTvOutName.setTextColor(Color.parseColor("#333333"));
            dataTvIn.setBackgroundResource(R.drawable.title_bg);
            dataTvOut.setBackgroundResource(R.drawable.title_bg2);
        }
    }

    private void initListener() {
        dataListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (messages.get(i).getState() == 3) {
                    DataAdapter dataAdapter = new DataAdapter(DataActivity.this, getState(3));
                    listener = dataAdapter;
                    dataListview.setAdapter(dataAdapter);
                    dataTitle.setVisibility(View.GONE);
                    excCount.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    back = 1;
                }
            }
        });
    }

    private List<GoodMessage> getTypeData(int type) {
        List<GoodMessage> list = new ArrayList<>();
        for (GoodMessage message : messages) {
            if (message.getType() == type)
                list.add(message);
        }
        return list;
    }

    private List<GoodMessage> getState(int state) {
        List<GoodMessage> list = new ArrayList<>();
        for (GoodMessage message : messages) {
            if (message.getState() == state)
                list.add(message);
        }
        return list;
    }
    public interface SortListener {
        void onSortListener();
    }
}