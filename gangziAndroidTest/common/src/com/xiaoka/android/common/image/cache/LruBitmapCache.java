package com.xiaoka.android.common.image.cache;///**
// * Copyright © 2014 XiaoKa. All Rights Reserved
// */
//package com.xiaoka.android.core.image.cache;
//
//import android.annotation.TargetApi;
//import android.app.ActivityManager;
//import android.content.Context;
//import android.content.pm.ApplicationInfo;
//import android.graphics.Bitmap;
//import android.os.Build;
//
//import com.android.volley.toolbox.ImageLoader.ImageCache;
//import com.xiaoka.android.core.image.cache.impl.LruMemoryCache;
//
///**
// * LRU算法缓存
// *
// * @version 1.0
// * @since 1.0
// * @author Shun
// *
// */
//public class LruBitmapCache extends LruMemoryCache implements ImageCache {
//
//	public LruBitmapCache(int maxSize) {
//		super(maxSize);
//	}
//
//	public LruBitmapCache(Context ctx) {
//		this(getCacheSize(ctx, 0));
//	}
//
//	@Override
//	public Bitmap getBitmap(String url) {
//		return get(url);
//	}
//
//	@Override
//	public void putBitmap(String url, Bitmap bitmap) {
//		put(url, bitmap);
//	}
//
//	/**
//	 * 得到默认设置的缓存大小,Width*Height*4,也就是屏幕大小的四倍
//	 *
//	 * @param context
//	 * @return
//	 */
//	public static int getCacheSize(Context context, int memoryCacheSize) {
//		if (memoryCacheSize == 0) {
//			ActivityManager am = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//			int memoryClass = am.getMemoryClass();
//			if (hasHoneycomb() && isLargeHeap(context)) {
//				memoryClass = getLargeMemoryClass(am);
//			}
//			memoryCacheSize = 1024 * 1024 * memoryClass / 8;
//		}
//		return memoryCacheSize;
//	}
//
//	private static boolean isLargeHeap(Context context) {
//		return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
//	}
//
//	private static boolean hasHoneycomb() {
//		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
//	}
//
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	private static int getLargeMemoryClass(ActivityManager am) {
//		return am.getLargeMemoryClass();
//	}
//}
