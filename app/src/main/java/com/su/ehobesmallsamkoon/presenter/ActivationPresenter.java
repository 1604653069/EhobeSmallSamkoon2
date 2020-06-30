package com.su.ehobesmallsamkoon.presenter;

import com.su.ehobesmallsamkoon.api.NetworkTransformers;
import com.su.ehobesmallsamkoon.api.RetrofitClient;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.BasePresenter;
import com.su.ehobesmallsamkoon.bean.requst.Activation;
import com.su.ehobesmallsamkoon.bean.respond.Dept;
import com.su.ehobesmallsamkoon.util.IpUtils;

import java.util.ArrayList;

import io.reactivex.Observable;

public class ActivationPresenter extends BasePresenter {
    //获取科室
    public Observable<ArrayList<Dept>> getDept(){
        return RetrofitClient.getService().getDept().compose(new NetworkTransformers<>(mView));
    }

    //激活
    public Observable<String> activation(String deptNumber){
        return RetrofitClient.getService().activation(new Activation(1,2, Constant.TERM_ID,deptNumber, IpUtils.getIPAddress(getContext()))).compose(new NetworkTransformers<>(mView));
    }
}
