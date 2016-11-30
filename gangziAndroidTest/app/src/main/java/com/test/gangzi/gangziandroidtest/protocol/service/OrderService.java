package com.test.gangzi.gangziandroidtest.protocol.service;


import com.chediandian.business.order.OrderResponseProtocol;
import com.chediandian.business.order.bean.OrderDetailBean;
import com.chediandian.business.order.bean.OrderListBean;
import com.chediandian.business.order.params.AddProofBody;
import com.chediandian.business.protocol.DaggerServiceCallback;
import com.chediandian.business.protocol.ResponseProtocol;
import com.chediandian.business.protocol.ServiceCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by LeiYu on 2015/7/2.
 */
public interface OrderService {

    @GET("/care/order/list/3.0")
    public void getOrderList(@Query("careShopId") String careShopId, @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("isWashOrder") Integer isWashOrder, ServiceCallback<OrderResponseProtocol<List<OrderListBean>>> cb);

    @GET("/care/order/list/3.0")
    public void getOrderList(@Query("careShopId") String careShopId,
                             @Query("pageNumber") Integer pageNumber,
                             @Query("pageSize") Integer pageSize,
                             @Query("isWashOrder") Integer isWashOrder,
                             DaggerServiceCallback<OrderResponseProtocol<List<OrderListBean>>> cb);

    @GET("/care/broker/order/list/3.2.3")
    public void getBrokerOrderList(@Query("careShopId") String careShopId, @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("isWashOrder") Integer isWashOrder, ServiceCallback<OrderResponseProtocol<List<OrderListBean>>> cb);

    @GET("/care/broker/order/list/3.2.3")
    public void getBrokerOrderList(@Query("careShopId") String careShopId,
                                   @Query("pageNumber") Integer pageNumber,
                                   @Query("pageSize") Integer pageSize,
                                   @Query("isWashOrder") Integer isWashOrder,
                                   DaggerServiceCallback<OrderResponseProtocol<List<OrderListBean>>> cb);

    @GET("/care/order/detail/3.0")
    public void getOrderDetail(@Query("orderId") String orderId, ServiceCallback<ResponseProtocol<OrderDetailBean>> cb);

    @GET("/care/order/detail/3.0")
    public void getOrderDetail(@Query("orderId") String orderId, DaggerServiceCallback<ResponseProtocol<OrderDetailBean>> cb);

    @GET("/care/order/delete/3.0")
    public void deleteOrder(@Query("orderId") String orderId, ServiceCallback<ResponseProtocol<String>> cb);

    @GET("/care/order/delete/3.0")
    public void deleteOrder(@Query("orderId") String orderId, DaggerServiceCallback<ResponseProtocol<String>> cb);

    //【代办】上传补缴凭据
    @POST("/care/broker/addmoney/proof/3.2.3")
    public void postAddProof(@Body AddProofBody param, ServiceCallback<ResponseProtocol<Object>> cb);

    @POST("/care/broker/addmoney/proof/3.2.3")
    public void postAddProof(@Body AddProofBody param, DaggerServiceCallback<ResponseProtocol<Object>> cb);

    //【代办】完成服务
    @POST("/care/broker/finish/proof/3.2.3")
    public void postFinishProof(@Body AddProofBody param, ServiceCallback<ResponseProtocol<Object>> cb);

    @POST("/care/broker/finish/proof/3.2.3")
    public void postFinishProof(@Body AddProofBody param, DaggerServiceCallback<ResponseProtocol<Object>> cb);

    //【代办】完成服务
    @POST("/care/finish/proof/3.2.4")
    public void postFinishRescueProof(@Body AddProofBody param, ServiceCallback<ResponseProtocol<Object>> cb);

    @POST("/care/finish/proof/3.2.4")
    public void postFinishRescueProof(@Body AddProofBody param, DaggerServiceCallback<ResponseProtocol<Object>> cb);

}
