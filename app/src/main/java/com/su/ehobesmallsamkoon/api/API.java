package com.su.ehobesmallsamkoon.api;


import com.su.ehobesmallsamkoon.bean.requst.Activation;
import com.su.ehobesmallsamkoon.bean.requst.Login;
import com.su.ehobesmallsamkoon.bean.respond.BaseRespond;
import com.su.ehobesmallsamkoon.bean.respond.Dept;
import com.su.ehobesmallsamkoon.bean.respond.LoginResults;
import com.su.ehobesmallsamkoon.bean.respond.TerminalInfo;


import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.login)
//    Observable<BaseRespond<LoginResult>> login(@Body Login login);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.homeGetOrderNumber)
//    Observable<BaseRespond<ArrayList<HomeOrderNumbers>>> getHomeOrderNumber(@Body Object articlesuppliername);
//
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.getAllHospital)
//    Observable<BaseRespond<ArrayList<AllHospital>>> getAllHospital(@Body Object userId);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.getNewOrders)
//    Observable<BaseRespond<NewOrders>> getNewOrders(@Body GetNewOrders newOrders);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.getAllConsumables)
//    Observable<BaseRespond<Consumables>> getAllConsumables(@Body GetAllConsumables getAllConsumables);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.getAllOrders)
//    Observable<BaseRespond<AllOrders>> getAllOrders(@Body GetAllOrders getAllOrders);
//
//    @Multipart
//    @POST(HostUrl.upLoadFile)
//    Observable<BaseRespond<UpLoadFile>> upLoadFile(@Part MultipartBody.Part file);
//
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.opinion)
//    Observable<BaseRespond<String>> opinion(@Body Opinion opinion);
//
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.loginOut)
//    Observable<BaseRespond<String>> loginOut(@Body Object userId);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.getArticleInfo)
//    Observable<BaseRespond<ArticleInfo>> getArticleInfo(@Body Object info);
//
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.getCoin)
//    Observable<BaseRespond<Money>> getCoin(@Body Object info);
//
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.getVIPInfo)
//    Observable<BaseRespond<ArrayList<VIPInfo>>> getVIPInfo(@Body Object id);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.getArticleByOrder)
//    Observable<BaseRespond<ArrayList<OrderArticle>>> getArticleByOrder(@Body Object info);
//
//    @Headers({"URL:supplier"})
//    @POST(HostUrl.getDeliveryType)
//    Observable<BaseRespond<ArrayList<DeliveryType>>> getDeliveryType(@Body Object info);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.putOrderDetailed)
//    Observable<BaseRespond<String>> putOrderDetailed(@Body PutOrdersDetailed putOrdersDetailed);
//
//    @Headers({"URL:hospital"})
//    @POST(HostUrl.returnOrder)
//    Observable<BaseRespond<String>> returnOrder(@Body Object info);

//    @Headers({"URL:supllier"})
//    @GET
//    Call<ResponseBody> download(@Url String fileUrl);


    @POST(HostUrl.login)
    Observable<BaseRespond<ArrayList<LoginResults>>> login(@Body Login login);

    @POST(HostUrl.detection)
    Observable<BaseRespond<ArrayList<TerminalInfo>>> detection(@Body Object termnum);

    @POST(HostUrl.dept)
    Observable<BaseRespond<ArrayList<Dept>>> getDept();

    @POST(HostUrl.activation)
    Observable<BaseRespond<String>> activation(@Body Activation activation);

}
