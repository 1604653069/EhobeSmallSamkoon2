package com.su.ehobesmallsamkoon.presenter;



import android.os.Handler;

import com.su.ehobesmallsamkoon.api.HostUrl;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.BasePresenter;
import com.su.ehobesmallsamkoon.util.OkhttpUtils;

import java.util.HashMap;
import java.util.Map;



public class InputPresenter extends BasePresenter {
    //登录
//    public Observable<LoginResults> login(String userName, String password){
//        return RetrofitClient.getService().login(new Login(Constant.TERM_ID,userName,password,1)).compose(new NetworkTransformers<>(mView));
//    }
     public void login(String username,String password ,Handler handler){
         Map map = new HashMap();
         map.put("account",username);
         map.put("pwd",password);
         map.put("termnum", Constant.TERM_ID);
         map.put("logintype",1);
         OkhttpUtils.post(HostUrl.login,OkhttpUtils.mapToStr(map),handler);
     }
}
