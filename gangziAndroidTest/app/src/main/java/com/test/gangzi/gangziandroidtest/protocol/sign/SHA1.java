package com.test.gangzi.gangziandroidtest.protocol.sign;

import android.text.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SHA1 {

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	/**
	 * 使用 HMAC-SHA1 签名方法对encryptText进行签名
	 *
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return 签名
	 * @throws Exception
	 */
	public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
			throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}

	/**
	 * 构造源串
	 *
	 * @param params
	 *            参数列表(key=value)
	 * @param url
	 *            请求url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String generateBaseString(String[] params, String url)
			throws UnsupportedEncodingException {
		int i = 0;
		String baseString = "";
		String paramString = "";
		if (!TextUtils.isEmpty(url)) {
			baseString += URLEncoder.encode(url, "UTF-8") + "&";
		}
		Arrays.sort(params);
		for (String str : params) {
			// 排除sig参数
			if (!str.startsWith("sig=") && !str.startsWith("func=")
					&& !str.startsWith("callback=")) {
				if (i == 0) {
					paramString += str;
				} else {
					paramString += "&" + str;
				}
				i++;
			}
		}
		baseString += URLEncoder.encode(paramString, "UTF-8");
		return baseString;
	}

	/**
	 * Base64编码
	 *
	 * @param str
	 *            字节数组
	 * @return 编码后字符串
	 */
	public static String Base64(byte[] src) {
		return Base64.encode(src);
	}

	/**
	 * base64解码
	 *
	 * @param src
	 * @return byte数组
	 * @throws IOException
	 */
	public static byte[] Base64Decode(String src) throws IOException {
		return Base64.decode(src);
	}
}
