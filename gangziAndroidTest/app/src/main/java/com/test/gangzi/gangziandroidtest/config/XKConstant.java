package com.test.gangzi.gangziandroidtest.config;

/**
 * 全局常量
 * Created by gangzi on 2015/6/24.
 */
public class XKConstant {
    public final static String SHARED_PREFRENCES_TAG = "YCDD_BUSINESS_SP";
    public static final String OS = "android";
    public static final String WX_APP_KEY = "wx5a15749b2d2ad525";
    public static final String WX_APP_KEY_DEBUG = "wxa7483bfc97a2cbc1";


    //

    // 结算host
    public static final String SETTLE_HOST = "https://secure.yangchediandian.com";
    // 推送
    public static final String SP_IS_H5_PUSH = "is_h5_push";
    public static final String SP_IS_ORDER_PUSH = "is_order_push";
    public static final String SP_ORDER_ID = "push_order_id";
    public static final String SP_MSG_TYPE = "push_msg_type";
    public static final String SP_ORDER_TITLE = "push_order_title";
    public static final String SP_ORDER_CONTENT = "push_order_content";
    public static final String SP_H5_URL = "h5_url";

    // main
    public static final int ONLY_INSURANCE = 2; //独代
    public static final int PERMISSION_SETTLEMENT = 1; //结算权限
    public static final int PERMISSION_VIOLATION = 5; //代办权限

    // 订单和报价
    public static final int FLAG_ITEM_SERVICE = 2; // 保养
    public static final int FLAG_ITEM_PAINT = 4; // 油漆
    public static final int FLAG_ITEM_TYRE = 7; // 轮胎


    public static final int ORDER_TYPE_VIOLATION = 6; //代办订单
    public static final int ORDER_TYPE_RESCUE = 7; //救援订单
    public static final int ORDER_CLOSE_TYPE = 0; // 订单关闭

    public static final String H5_EARNEST_MONEY = "earnestMoney";

    // h5跳转页面的标识
    public static final String H5PAGE_SETTLEMENT = "h5_settlement"; //结算
    public static final String H5PAGE_BEHAVIOR = "h5_behavior"; //活动
    public static final String H5PAGE_SCORE = "h5_score"; //积分
    public static final String H5PAGE_INSURANCE = "h5_insurance"; //代理
    public static final String H5PAGE_RULE = "h5_rule"; //规则


    //测试开发环境
    public final static String TEST_RONG_CLOUD_APP_KEY = "bmdehs6pdcv3s";
    public final static String TEST_RONG_CLOUD_APP_SECRET = "q7aadLbjXik6r";
    public final static String TEST_RONG_CLOUD_TARGET_ID = "ddyc_test";

    //线上环境
    public final static String PRODUCTION_RONG_CLOUD_APP_KEY = "pkfcgjstfqml8";
    public final static String PRODUCTION_RONG_CLOUD_APP_SECRET = "NgGKqx257T4dri";
    public final static String PRODUCTION_RONG_CLOUD_TARGET_ID = "ddyc";

}
