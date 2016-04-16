package com.test.gangzi.gangziandroidtest.config;

/**
 * 上传图片类型枚举类
 * @author gangzi
 * @version 1.2.0
 * @date 2015/12/18
 */
public enum ImageType {


    AVATOR(UpYunConstant.IMAGE_AVATOR + UpYunConstant.IMAGE_URL),
    IDCARD(UpYunConstant.IMAGE_ID + UpYunConstant.IMAGE_URL),
    VEICHLE(UpYunConstant.IMAGE_LIC + UpYunConstant.IMAGE_URL),
    HTML(UpYunConstant.IMAGE_HTML + UpYunConstant.IMAGE_URL),
    RECEIPT(UpYunConstant.IMAGE_SHOP_RECEIPT + UpYunConstant.IMAGE_URL),
    ORDER(UpYunConstant.IMAGE_ORDER_PROOF + UpYunConstant.IMAGE_URL);

    private String type;

    ImageType(String pType) {
        type = pType;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }
}
