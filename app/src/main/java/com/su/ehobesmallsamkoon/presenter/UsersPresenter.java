package com.su.ehobesmallsamkoon.presenter;

import android.os.Handler;

import com.su.ehobesmallsamkoon.api.HostUrl;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.BasePresenter;
import com.su.ehobesmallsamkoon.util.OkhttpUtils;

import java.util.HashMap;
import java.util.Map;

public class UsersPresenter extends BasePresenter {

    public void getDate(Handler handler){
        Map<String,Object> map = new HashMap<>();
        map.put("termnum", Constant.TERM_ID);
        map.put("operateuserid",1);
        map.put("index",1);
        map.put("pagesize",100);
        OkhttpUtils.post(HostUrl.userInform,OkhttpUtils.mapToStr(map),handler);
    }
    //测试接口
       public void deleteUser(String  userId,String opuserid,Handler handler){
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userId);
        map.put("opuserid",opuserid);
        OkhttpUtils.post(HostUrl.deleteUser,OkhttpUtils.mapToStr(map),handler);
    }

}
