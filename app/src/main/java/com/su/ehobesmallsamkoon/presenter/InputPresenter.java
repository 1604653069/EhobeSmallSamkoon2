package com.su.ehobesmallsamkoon.presenter;

import com.su.ehobesmallsamkoon.api.NetworkTransformers;
import com.su.ehobesmallsamkoon.api.RetrofitClient;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.BasePresenter;
import com.su.ehobesmallsamkoon.bean.requst.Login;
import com.su.ehobesmallsamkoon.bean.respond.LoginResults;

import io.reactivex.Observable;

public class InputPresenter extends BasePresenter {
    //登录
//    public Observable<LoginResults> login(String userName, String password){
//        return RetrofitClient.getService().login(new Login(Constant.TERM_ID,userName,password,1)).compose(new NetworkTransformers<>(mView));
//    }
}
