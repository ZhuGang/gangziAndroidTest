/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.image.cache.local.name;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5做为文件名存储本地
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
public class MD5LocalName implements LocalName {

	private static final String HASH_ALGORITHM = "MD5";
	/**
	 * 加密基数
	 */
	private static final int RADIX = 10 + 26;

	@Override
	public String getFileName(String imageUri) {
		byte[] md5 = getMD5(imageUri.getBytes());
		BigInteger bi = new BigInteger(md5).abs();
		return bi.toString(RADIX);
	}

	private byte[] getMD5(byte[] data) {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(data);
			hash = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}
}
