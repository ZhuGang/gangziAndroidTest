package com.xiaoka.android.common.http.sign;

import java.util.Map;

/**
 * 签名器
 */
public interface Sign {
    String getSign(String body, String url);

    String getSign(Map<String, String> params, String url);
}
