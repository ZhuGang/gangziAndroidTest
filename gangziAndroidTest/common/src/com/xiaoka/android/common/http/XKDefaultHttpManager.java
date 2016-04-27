/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.http;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;
import com.xiaoka.android.common.log.XKLog;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HTTP请求管理类
 *
 * @author Shun
 * @version 2.0.1
 * @since 1.0
 */
public final class XKDefaultHttpManager {
    private final static int CONNECT_TIME_OUT = 30;
    private final static int READ_TIME_OUT = 30;
    private final static int WRITE_TIME_OUT = 30;
    private static XKDefaultHttpManager mInstance;
    private OkHttpClient client;

    private XKDefaultHttpManager() {
        //初始化OKHttpClient
        client = new OkHttpClient();
        client.setConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        client.setReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        client.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
    }

    public static XKDefaultHttpManager getInstance() {
        if (null == mInstance) {
            mInstance = new XKDefaultHttpManager();
        }
        return mInstance;
    }

    /**
     * 请求一个JSON串(异步)
     *
     * @param url      请求地址
     * @param params   参数Map
     * @param headers  请求头Map
     * @param callback 回调接口
     */
    public void json(String url, Map<String, String> params, Map<String, String> headers, Callback callback) {
        client.newCall(createRequest(url, params, headers)).enqueue(callback);
    }

    /**
     * 请求一个JSON串(同步)
     *
     * @param url     请求地址
     * @param params  参数Map
     * @param headers 请求头Map
     * @return
     */
    public Response syncJson(String url, Map<String, String> params, Map<String, String> headers) {
        try {
            return client.newCall(createRequest(url, params, headers)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建一个Request
     *
     * @param url     请求地址
     * @param params  参数Map
     * @param headers 请求头Map
     * @return
     */
    private Request createRequest(String url, Map<String, String> params, Map<String, String> headers) {
        Request request = null;
        if (null != params) {
//            FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                formEncodingBuilder.add(entry.getKey(), entry.getValue());
//            }
            JSONObject json = new JSONObject(params);
            String content = json.toString();
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), content.getBytes(Util.UTF_8));
            Request.Builder builder = new Request.Builder();
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
            builder.addHeader("Content-Type", "application/json");
            // request = builder.post(formEncodingBuilder.build()).url(url).build();
            request = builder.post(body).url(url).build();
        } else {
            Request.Builder builder = new Request.Builder();
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
            request = builder.url(url).build();
        }
        return request;
    }
}
