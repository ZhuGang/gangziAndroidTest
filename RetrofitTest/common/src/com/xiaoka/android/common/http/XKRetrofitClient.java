package com.xiaoka.android.common.http;

import android.util.Base64;

import com.xiaoka.android.common.http.sign.Sign;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

public class XKRetrofitClient {
    public static Map<String, String> times = new HashMap<String, String>();

    private RestAdapter restAdapter;
    private Map<String, String> mHeaders = new HashMap<>();
    private String mHost;
    private int mLogLevel;
    private Sign mSign;

    private XKRetrofitClient() {

    }

    private void init(Map<String, String> headers, String host, int logLevel, Sign sign) {
        this.mHeaders = headers;
        this.mHost = host;
        this.mLogLevel = logLevel;
        this.mSign = sign;
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                for (String k : mHeaders.keySet()) {
                    request.addHeader(k, mHeaders.get(k));
                }
            }
        };

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(mLogLevel == 0 ? RestAdapter.LogLevel.NONE : RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint(mHost)
                .setClient(new InnerClient())
                .build();
    }

    /**
     * 添加一个Header
     *
     * @param k
     * @param v
     */
    public void addHeader(String k, String v) {
        mHeaders.put(k, v);
    }

    /**
     * 移除一个Header
     *
     * @param k
     */
    public void removeHeader(String k) {
        if (mHeaders.containsKey(k))
            mHeaders.remove(k);
    }

    /**
     * 移除所有Header
     */
    public void removeAllHeader() {
        if (null != mHeaders)
            mHeaders.clear();
    }

    public <T> T getService(Class<T> service) {
        return restAdapter.create(service);
    }

    public class InnerClient extends OkClient {
        @Override
        public Response execute(Request request) throws IOException {
            Request newReq = null;
            List<Header> headers = request.getHeaders();
            List<Header> dealedHeaders = new ArrayList<Header>();
            for (Header header : headers ) {
                dealedHeaders.add(header);
            }

            if (!request.getMethod().equals("GET") && null != mSign) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                request.getBody().writeTo(out);
                String body = new String(out.toByteArray(), "UTF-8");
                String url = request.getUrl() + "?sign=" + mSign.getSign(body, request.getUrl());

                times.put(url, System.currentTimeMillis() + "");
                dealedHeaders.add(new Header("timestamp", times.get(url)));
                newReq = new Request(request.getMethod(), url, dealedHeaders, request.getBody());
            }
            if (request.getMethod().equals("GET")) {
                String url = request.getUrl();
                if (url.contains("?")) {
                    url = url.substring(url.indexOf("?") + 1, url.length());
                    String params[] = url.split("&");
                    String kv[];
                    Map<String, String> map = new HashMap<>();
                    for (String p : params) {
                        kv = p.split("=");
                        if (null != kv && kv.length == 2) {
                            //// FIXME: 15/7/2  暂时性修复
                            if (kv[0].equalsIgnoreCase("plateNumber")) {
                                map.put(kv[0], new String(Base64.decode(kv[1],0), "utf-8"));
                            } else {
                                map.put(kv[0], kv[1]);
                            }
                        }
                    }
                    String sign = mSign.getSign(map, request.getUrl());
                    url = request.getUrl() + "&sign=" + sign;
                    times.put(url, System.currentTimeMillis() + "");
                    dealedHeaders.add(new Header("timestamp", times.get(url)));
                    newReq = new Request(request.getMethod(), url, dealedHeaders, request.getBody());
                } else {
                    //无参数也要进行签名
                    String sign = mSign.getSign(new HashMap<String, String>(), request.getUrl());
                    url += "?sign=" + sign;
                    times.put(url, System.currentTimeMillis() + "");
                    dealedHeaders.add(new Header("timestamp", times.get(url)));
                    newReq = new Request(request.getMethod(), url, dealedHeaders, request.getBody());
                }
            }
            return super.execute(null != newReq ? newReq : request);
        }
    }

    /**
     * 构建器
     */
    public static class Build {
        private Map<String, String> headers = new HashMap<>();
        private String host;
        private int logLevel;
        private Sign sign;

        public Build setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Build setHost(String host) {
            this.host = host;
            return this;
        }

        public Build setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Build setSign(Sign sign) {
            this.sign = sign;
            return this;
        }

        public XKRetrofitClient create() {
            XKRetrofitClient client = new XKRetrofitClient();
            client.init(headers, host, logLevel, sign);
            return client;
        }
    }
}
