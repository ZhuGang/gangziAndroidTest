package com.test.gangzi.retrofittest.service;

import com.test.gangzi.retrofittest.Model.Contributor;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by gangzi on 16/5/12.
 */
public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    void contributors(@Path("owner") String owner, @Path("repo") String repo, Callback<List<Contributor>> callback);

//    @GET("/care/careshop/spring/info/3.4.1")
//    public Observable<ResponseProtocol<SpringInfoBean>> getSpringInfo(@Query("careShopId") String careShopId);

//    @POST("/care/careshop/spring/save/3.4.1")
//    public Observable<ResponseProtocol<Object>> modifySpringInfo(@Body SpringBody pSpringBody);
}
