/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

import java.util.Set;

/**
 * SharedPreferences工具类<br/>
 * 为了性能考虑,put完之后需要手动调用{@link com.xiaoka.android.common.utils.XKSharedPreferencesUtil#commit()}<br/>
 * 来提交数据操作<br/>
 * 使用推荐:{@code SharedPreferencesUtil.load().putXX().putXX().commit();}
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
public class XKSharedPreferencesUtil {
	private static XKSharedPreferencesUtil mSharedPreferencesUtil;
	private static Editor mEditor;

	/**
	 * Step1
	 * 
	 * @param context
	 * @param name
	 *            文件名
	 */
	public static void load(Context context, String name) {
		mSharedPreferencesUtil = new XKSharedPreferencesUtil();
		mEditor = context.getSharedPreferences(name, Context.MODE_PRIVATE)
				.edit();
	}
	
	public static SharedPreferences getSharedPreferences (Context context, String name){
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	/**
	 * String
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	public static XKSharedPreferencesUtil putString(String k, String v) {
		mEditor.putString(k, v);
		return mSharedPreferencesUtil;
	}

	/**
	 * Int
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	public static XKSharedPreferencesUtil putInt(String k, int v) {
		mEditor.putInt(k, v);
		return mSharedPreferencesUtil;
	}

	/**
	 * Long
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	public static XKSharedPreferencesUtil putLong(String k, long v) {
		mEditor.putLong(k, v);
		return mSharedPreferencesUtil;
	}

	/**
	 * Float
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	public static XKSharedPreferencesUtil putFloat(String k, float v) {
		mEditor.putFloat(k, v);
		return mSharedPreferencesUtil;
	}

	/**
	 * Boolean
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	public static XKSharedPreferencesUtil putBoolean(String k, boolean v) {
		mEditor.putBoolean(k, v);
		return mSharedPreferencesUtil;
	}

	/**
	 * Set <br/>
	 * SDKINT必须>=11.即3.x+
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	@TargetApi(11)
	@SuppressLint("NewApi")
	public static XKSharedPreferencesUtil putStringSet(String k, Set<String> v) {
		if (VERSION.SDK_INT >= 11) {
			mEditor.putStringSet(k, v);
		}
		return mSharedPreferencesUtil;
	}

	/**
	 * 移除对应的key
	 * 
	 * @param k
	 * @return
	 */
	public static XKSharedPreferencesUtil remove(String k) {
		mEditor.remove(k);
		return mSharedPreferencesUtil;
	}

	/**
	 * 提交
	 */
	public static void commit() {
		if (null != mEditor) {
			mEditor.commit();
		}
	}
}
