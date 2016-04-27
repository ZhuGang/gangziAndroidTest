package com.xiaoka.android.common.utils;

import android.text.TextUtils;

/**
 * 字符串操作类
 * @author oracle
 */
public class XKStringUtil { 
	
	/**
	 * 
	 * 将字符串转化为整数
	 * @param num
	 */
	public static int toInt(String num){
		int n = 0;
		try {
			n = Integer.parseInt(num);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return n;
	}
	
	/**
	 * 
	 * 判断字符串是否是空的
	 * @param str
	 */
	public static boolean isEmpty(String str){
		return TextUtils.isEmpty(str);
	}
	
	/**
	 * 字符串转整型
	 * @param time
	 * @return long
	 */
	public static long toLong(String time){
		long n = 0;
		try {
			n = Long.parseLong(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return n;
	}
	
	/**
	 * 将字符串分解为字符串数组
	 * @param array
	 * @param split
	 * @return 返回String数组
	 */
	public static String[] toStringArray(String array,String split){
		String info[] = {""};
		try{
			info = array.split(split);
		}catch(Exception e){
			
		}
		return info;
	}
	
	/**
	 * 将字符串转化为布尔值
	 * @param s
	 * @return boolean
	 */
	public static boolean toBooleann(String s){
		boolean isTrue = false;
		try{
			isTrue = Boolean.parseBoolean(s);
		}catch(Exception e){
			
		}
		return isTrue;
	}
	
	/**
	 * 获取4位字符串
	 * @return String
	 */
	public static String get4LengthStr(String s){
		s = s.substring(0,4);
		if(s != null){
			return s + "...";
		}
		return "";
	}
	
}
