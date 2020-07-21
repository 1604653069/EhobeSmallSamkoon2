package com.su.ehobesmallsamkoon.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {
    private static final String api="http://113.142.71.161:7007/api/";
   // private static final String api2="http://192.168.1.63:7000/api";
    private static OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static Gson gson = new Gson();
    public static void post(String path,String json, Handler handler){
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(api+"/"+path)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handlerMessgae(handler,e.getMessage(),1);
                Log.i("TAG","登录失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(response.code()== HttpURLConnection.HTTP_OK){
                    handlerMessgae(handler,response.body().string(),2);
                    Log.i("TAG","获取信息成功");
                }else
                    Log.i("TAG","数据相应失败");

            }
        });
    }
    private static void handlerMessgae(Handler handler, String msg,int type){
        try {
            Message message = new Message();
            message.obj = msg;
            message.what = type;
            handler.sendMessage(message);
        }catch (Exception e){
            Log.e("TAG",e.getStackTrace()+"");
        }
    }
    public static String mapToStr(Map map){
        return gson.toJson(map);
    }
}
