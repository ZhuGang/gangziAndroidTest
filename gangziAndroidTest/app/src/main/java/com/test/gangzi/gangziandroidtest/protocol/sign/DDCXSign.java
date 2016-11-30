package com.test.gangzi.gangziandroidtest.protocol.sign;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bingo on 2015/3/20.
 */
public class DDCXSign {
    public final static Map<String, String> KEYS = new HashMap<String, String>();

    static {
        KEYS.put("code", "e69e603b5fc1d8eee69e603b5fc1d8ee");
        KEYS.put("common", "d41d8cd98f00b204d41d8cd98f00b204");
        KEYS.put("voice", "00251bfa22d422fd00251bfa22d422fd");
        KEYS.put("login", "bdd55894293f604abdd55894293f604a");
        KEYS.put("delete", "032f7fa841afdc3a3032f7fa841afdc3");
        KEYS.put("profile", "f6e3cff9f95733386f6e3cff9f957333");
        KEYS.put("verify", "bbadab85c33b46bdcbbadab85c33b46b");
        KEYS.put("save", "f0a4ff04f5906e04fb47056c4ec578dc");
    }

//    public static void changeSignKeyToProducation() {
//        KEYS.clear();
//        KEYS.put("code", "6ecdfe1fd043369b8ae120d08fa13918");
//        KEYS.put("common", "5c7903c3d3fb1c7f3cb9cc612d26a451");
//        KEYS.put("voice", "2b146e59364471220b25b2ec17d82b54");
//        KEYS.put("login", "d2b19ba9b0e7431585dd9a0282b29fb8");
//        KEYS.put("delete", "032f7fa841afdc3a3032f7fa841afdc3");
//        KEYS.put("profile", "2a5fe2f8bacd5bb4c43a658df55a61b5");
//        KEYS.put("verify", "96527add336b419a872f9977a7c07e4d");
//        KEYS.put("save", "94591f3b4d8337b013ff2e5ca5647904");
//    }

    private String url;

    public String getSign(Map<String, String> params, String url) {
        this.url = url;
        String sortStr = sortParams(params);
        return getRealSign(sortStr, getUrl());
    }

    public String getUrl() {
        return url;
    }

    public String sortParams(Map<String, String> params) {
        TreeMap<String, String> newParams = new TreeMap<String, String>(params);
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : newParams.entrySet()) {
            // 过滤无效参数
            String name = entry.getKey();
            String value = entry.getValue();
            if (!name.equals("sign") && value != null
                    && !value.trim().equals("")) {
                sb.append("&" + entry.getKey().toLowerCase() + "="
                        + entry.getValue());
            }
        }
        if (sb.toString().length() > 0)
            return sb.toString().substring(1);
        else
            return "";
    }

    //绑定PUSH
    private static final String PUSH_STR = "/ins/common/push/bind/1.1";
    //检查升级
    private static final String UPDATE_STR = "/ins/common/app/version/1.1";
    //登录
    private static final String LOGIN_STR = "/ins/user/login/1.1";
    //获取验证码
    private static final String VERIFICATION_CODE_STR = "/ins/user/code/1.1";
    //通用接口(可支付方式 )
    private static final String BIND_STR = "/ins/common/info/1.1";
    //现金券
    private static final String COUPON_STR = "/ins/activity/refer/get/code/1.1";

    private static final String COUPON_FUNCTION = "/ins/promotion/listRule/1.1";

    private static final String LOCATE_CITY = "/ins/locate/city/1.1";

    private static final String CITY_LIST = "/ins/insCity/list";

    //

    public String getRealSign(String baseString, String url) {
        String urlSuffix = parserSignKeyFromUrl(url);
        String KEY = null;
//        if (getKeys().get(urlSuffix) != null) {
//            KEY = getKeys().get(urlSuffix);
//        } else {
        KEY = getKeys().get("common");
//        }

        byte[] sigByte = null;
        try {

            //  if (url.contains(PUSH_STR) || url.contains(UPDATE_STR) || url.contains(LOGIN_STR) ||
            //          url.contains(VERIFICATION_CODE_STR) || url.contains(BIND_STR)||
            //         url.contains(COUPON_STR) || url.contains(COUPON_FUNCTION) || url.contains(CITY_LIST)
            //          || url.contains(LOCATE_CITY)) {
            sigByte = SHA1.HmacSHA1Encrypt(baseString, KEY);
            //   } else {
            //  sigByte = SHA1.HmacSHA1Encrypt(UserController.getInstance().getToken() + baseString, KEY);
            //   }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // HMAC-SHA1加密
        String sig = SHA1.Base64(sigByte);// BASE-64编码
        return MD5Util.get32MD5Str(sig).toUpperCase();
    }

    /**
     * getRealSignUseJS供js调用误改
     *
     * @param baseString
     * @return
     */
    public String getRealSignUseJS(String baseString) {
        String KEY = null;
//        if (getKeys().get(urlSuffix) != null) {
//            KEY = getKeys().get(urlSuffix);
//        } else {
        KEY = getKeys().get("common");
//        }

        byte[] sigByte = null;
        try {

            //  if (url.contains(PUSH_STR) || url.contains(UPDATE_STR) || url.contains(LOGIN_STR) ||
            //          url.contains(VERIFICATION_CODE_STR) || url.contains(BIND_STR)||
            //         url.contains(COUPON_STR) || url.contains(COUPON_FUNCTION) || url.contains(CITY_LIST)
            //          || url.contains(LOCATE_CITY)) {
            sigByte = SHA1.HmacSHA1Encrypt(baseString, KEY);
            //   } else {
            //  sigByte = SHA1.HmacSHA1Encrypt(UserController.getInstance().getToken() + baseString, KEY);
            //   }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // HMAC-SHA1加密
        String sig = SHA1.Base64(sigByte);// BASE-64编码
        return MD5Util.get32MD5Str(sig).toUpperCase();
    }

    public String getRealSignUseJS(String baseString, String type) {


        String urlSuffix = parserSignKeyFromUrl(url);
        String KEY = null;
        if (getKeys().get(urlSuffix) != null) {
            KEY = getKeys().get(urlSuffix);
        } else {
            KEY = getKeys().get("common");
        }

        byte[] sigByte = null;
        try {

//            if (url.contains(PUSH_STR) || url.contains(UPDATE_STR) || url.contains(LOGIN_STR) ||
//                    url.contains(VERIFICATION_CODE_STR) || url.contains(BIND_STR) ||
//                    url.contains(COUPON_STR) || url.contains(COUPON_FUNCTION) || url.contains(CITY_LIST)
//                    || url.contains(LOCATE_CITY)) {
            sigByte = SHA1.HmacSHA1Encrypt(baseString, KEY);
//            } else {
//                sigByte = SHA1.HmacSHA1Encrypt(UserController.getInstance().getToken() + baseString, KEY);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // HMAC-SHA1加密
        String sig = SHA1.Base64(sigByte);// BASE-64编码
        return MD5Util.get32MD5Str(sig).toUpperCase();


    }

    protected Map<String, String> getKeys() {
        return KEYS;
    }

    private String parserSignKeyFromUrl(String requestURI) {
        Matcher matcher = signPattern.matcher(requestURI);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException(requestURI
                    + " is not compliance with API");
        }
    }

    private final static Pattern signPattern = Pattern
            .compile("([^\\/]+)\\/[^\\/]+\\/?$");

    /**
     * Map转为String 参数格式<br/>
     * 转化后的格式:a=b&c=d&e=f
     *
     * @param params
     * @return
     */
    public String map2String(Map<String, String> params) {
        if (null == params || params.size() == 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (String key : params.keySet()) {
            buffer.append(key + "=" + params.get(key) + "&");
        }
        String returnString = buffer.toString();
        final int lastIndex = returnString.lastIndexOf("&");
        // 去除最后一个&
        if (lastIndex + 1 == returnString.length()) {
            returnString = returnString.substring(0, lastIndex);
        }
        return returnString;
    }
}