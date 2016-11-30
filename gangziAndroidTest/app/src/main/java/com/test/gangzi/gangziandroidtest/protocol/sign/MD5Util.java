package com.test.gangzi.gangziandroidtest.protocol.sign;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 *
 * @author Administrator
 *
 */
public class MD5Util {

	public static String get32MD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字??
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			MessageDigest md = MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是???? 128 位的长整数，
										// 用字节表示就??16 个字??
			char str[] = new char[16 * 2]; // 每个字节??16 进制表示的话，使用两个字符，
											// ????表示??16 进制???? 32 个字??
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第????字节????，对 MD5 的每????字节
											// 转换??16 进制字符的转??
				byte byte0 = tmp[i]; // 取第 i 个字??
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中??4 位的数字转换,
															// >>>
															// 为??辑右移，将符号位????右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中??4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符??

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

}
