package com.su.ehobesmallsamkoon.presenter;

import android.util.Log;

import com.su.ehobesmallsamkoon.api.NetworkTransformers;
import com.su.ehobesmallsamkoon.api.RetrofitClient;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.BasePresenter;
import com.su.ehobesmallsamkoon.bean.requst.Login;
import com.su.ehobesmallsamkoon.bean.respond.LoginResults;

import io.reactivex.Observable;

public class FingerPresenter extends BasePresenter {
    //登录
//    public Observable<LoginResults> login(String chara){
//        Log.e(TAG, "login: " );
//        return RetrofitClient.getService().login(new Login("", 0, "", "", 4, "", Constant.TERM_ID, chara)).compose(new NetworkTransformers<>(mView));
//    }
}
