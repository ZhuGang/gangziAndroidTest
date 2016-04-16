package com.test.gangzi.gangziandroidtest.config;

import android.os.Environment;

import java.io.File;

/**
 * SD卡资源常量
 * 
 * @version 2.2
 * @since 2.2
 * @author gangzi
 *
 */
public final class SDCardConstant {

	/**
	 * 资源根目录
	 */
	public final static File ROOT = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "DDYC");
	/**
	 * 旧的资源根目录
	 */
	public final static File OLD_ROOT = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "YCDD_BUSINESS");
	
	/**
	 * 旧的存放图片缓存的根目录
	 */
	public final static File OLD_IMG_CACHE = new File(OLD_ROOT, "image");
	
	/**
	 * 旧的存放录音的缓存目录
	 */
	public final static File OLD_VOICE_CACHE = new File(OLD_ROOT, "voice");
	
	/**
	 * 存放图片缓存的根目录
	 */
	public final static File IMG_CACHE = new File(ROOT, "image");

	/**
	 * 存放用户头像的缓存目录
	 */
	public final static File IMG_USER_PIC_CACHE = new File(ROOT, "pic");

	/**
	 * 存放录音的缓存目录
	 */
	public final static File VOICE_CACHE = new File(ROOT, "voice");

	/**
	 * 存放缓存临时目录
	 */
	public final static File TEMP_CACHE = new File(ROOT, "temp");

	/**
	 * 存放广告图片目录
	 */
	public final static File ADVERTISING_CACHE = new File(ROOT, "advertising");
	
	/**
	 * 存放二维码图片目录
	 */
	public final static File QRCODE_IMAGE_CACHE = new File(ROOT, "qrcode");

	/**
	 * 存放行驶证图片目录
	 */
	public final static File DRIVING_IMAGE_CACHE = new File(ROOT, "driving");

	/**
	 * 日志文件目录
	 */
	public final static File LOG = new File(ROOT, "log");

    /**
     * 日志文件目录
     */
    public final static File DEBUG_LOG = new File(ROOT, "debuglog");
}
