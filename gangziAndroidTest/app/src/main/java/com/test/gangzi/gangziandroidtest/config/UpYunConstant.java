package com.test.gangzi.gangziandroidtest.config;

/**
 * 又拍云上传常量类
 * @version 2.2
 * @since 2.2
 * @author gangzi
 */
public class UpYunConstant {
	
	/*public static String IMAGE_API_KEY = "SvBZiD2sFIHHJbFMP1IsXA25Jcw=";
	public static String IMAGE_BUCKET = "chediandian-imgs";
	public static String VOICE_API_KEY = "lobcMuxk/JYCHnguZrIoNk0Gd9M=";
	public static String VOICE_BUCKET = "chediandian-voice";*/
	
	public static String IMAGE_API_KEY = "tH3NtjMCLt2M5l6m/yeRmOtwpLg=";
	public static String IMAGE_BUCKET = "cdd-app-img01"; // 存储空间
	public static String VOICE_API_KEY = "hfCApkG9tfjp3zrIGfdSeNo+W5I="; //测试使用的表单api验证密钥
	public static String VOICE_BUCKET = "cdd-app-file01";	//存储空间
	
	public static void changeUrlToProducation() {
//		IMAGE_API_KEY = "tH3NtjMCLt2M5l6m/yeRmOtwpLg=";
//		IMAGE_BUCKET = "cdd-app-img01"; // 存储空间
		IMAGE_API_KEY = "xcz4ZwS+GF+ZGHhD4aRrvWKmSMw=";
		IMAGE_BUCKET = "ddyc-store";
		VOICE_API_KEY = "hfCApkG9tfjp3zrIGfdSeNo+W5I="; //测试使用的表单api验证密钥
		VOICE_BUCKET = "cdd-app-file01";	//存储空间
	}
	
	public static void changeUrlToTest() {
//		IMAGE_API_KEY = "SvBZiD2sFIHHJbFMP1IsXA25Jcw=";
//		IMAGE_BUCKET = "chediandian-imgs";
		IMAGE_API_KEY = "5EPXPmLrlrslSMmCgf0gouood64=";
		IMAGE_BUCKET = "ddyc-store-test";
		VOICE_API_KEY = "lobcMuxk/JYCHnguZrIoNk0Gd9M=";
		VOICE_BUCKET = "chediandian-voice";
	}

	public static final String IMAGE_AVATOR = "/user/avator";
	public static final String IMAGE_ID = "/user/id";
	public static final String IMAGE_LIC = "/veichle/lic";
	public static final String IMAGE_HTML = "/h5";
	public static final String IMAGE_SHOP_RECEIPT = "/shop/receipt";
	public static final String IMAGE_ORDER_PROOF = "/order/proof";
	public static final String IMAGE_URL = "/{year}/{mon}{day}/{filemd5}.jpg";

}
