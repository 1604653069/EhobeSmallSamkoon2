package com.su.ehobesmallsamkoon.presenter;


import android.util.ArrayMap;

import com.su.ehobesmallsamkoon.api.NetworkTransformers;
import com.su.ehobesmallsamkoon.api.RetrofitClient;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.BasePresenter;
import com.su.ehobesmallsamkoon.bean.respond.TerminalInfo;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;


public class DetectionPresenter extends BasePresenter {

    public Observable<ArrayList<TerminalInfo>> getTerminals() {
        Map<String,String> map = new ArrayMap<>();
        map.put("termnum",Constant.TERM_ID);
        return RetrofitClient.getService().detection(map).compose(new NetworkTransformers<>(mView));
    }
}
