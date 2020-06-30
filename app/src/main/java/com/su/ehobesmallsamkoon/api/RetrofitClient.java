package com.su.ehobesmallsamkoon.api;



import com.su.ehobesmallsamkoon.base.BaseApplication;
import com.su.ehobesmallsamkoon.util.L;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 获取单列
 */

public class RetrofitClient {

    public volatile static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null)
            synchronized (RetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = getRetrofit();
                }
            }
        return retrofit;
    }


    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置超时
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                //需要对请求参数进行处理的时候添加
//                .addInterceptor(new ParameterInterceptor())// 拦截器
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request oldRequest = chain.request();
//                        Request request = oldRequest.newBuilder()
//                                .header("Content-Type","application/json; charset=UTF-8")
//                                .header("userId","3")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                .addInterceptor(loggingInterceptor);
//                .addInterceptor(new MoreBaseUrlInterceptor());

        return builder.build();
    }

    public static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    L.w("RetrofitLog", message + "");
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);


    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(HostUrl.Ehobe)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }


    public static <T> T getAPIService(Class<T> service) {
        return getInstance().create(service);
    }

    public static API getService() {
        return getAPIService(API.class);
    }

    /**
     * @explain 多地址拦截器
     **/
    public static class MoreBaseUrlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            HttpUrl oldUrl = originalRequest.url();
            Request.Builder builder = originalRequest.newBuilder();
            List<String> urlNameList = originalRequest.headers("URL");
            if(urlNameList != null && urlNameList.size() > 0){
                builder.removeHeader("URL");
                String URL = urlNameList.get(0);
                HttpUrl baseUrl = null;
                if("hospital".equals(URL)){
                    baseUrl = HttpUrl.parse(BaseApplication.Hospital);
                }else if("supplier".equals(URL)){
                    baseUrl = HttpUrl.parse(HostUrl.Ehobe);
                }
                HttpUrl newHttpUrl = oldUrl.newBuilder()
                        .scheme(baseUrl.scheme())
                        .host(baseUrl.host())
                        .port(baseUrl.port())
                        .build();
                Request newRequest = builder.url(newHttpUrl).build();
                return chain.proceed(newRequest);
            }else{
                return chain.proceed(originalRequest);
            }
        }
    }


}
