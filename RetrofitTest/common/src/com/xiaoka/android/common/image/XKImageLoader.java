package com.xiaoka.android.common.image;///**
// * Copyright © 2014 XiaoKa. All Rights Reserved
// */
//package com.xiaoka.android.core.image;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.ImageLoader;
//import com.xiaoka.android.core.image.cache.LruBitmapCache;
//import com.xiaoka.android.core.image.cache.local.name.LocalName;
//import com.xiaoka.android.core.image.utils.XKImageUtils;
//
///**
// * <p>
// * <b>ImageLoader增强版</b>  之前用Volley时使用的Imageloader
// * </p>
// * <li>支持本地缓存</li> <li>支持取消指定的图片请求</li> <li>比之前更强大的内存缓存池</li>
// *
// * @version 1.0
// * @since 1.0
// * @author Shun
// * @hide 暂时不对外不暴露
// *
// */
//class XKImageLoader extends ImageLoader {
//
//	private LruBitmapCache mCache;
//	private LocalName mLocalName;
//	private String mLocalDirsPath;
//	private ExecutorService mPool;
//	private Map<String, ImageContainer> mRequests;
//
//	/**
//	 *
//	 * @param queue
//	 * @param imageCache
//	 * @param localDirsPath
//	 *            本地缓存文件夹路径
//	 * @param localName
//	 *            文件名策略接口
//	 */
//	public XKImageLoader(RequestQueue queue, ImageCache imageCache,
//			String localDirsPath, LocalName localName) {
//		super(queue, imageCache);
//		// 这里记录引用,为了添加本地缓存
//		mCache = (LruBitmapCache) imageCache;
//		mLocalDirsPath = localDirsPath;
//		mLocalName = localName;
//		mPool = Executors.newFixedThreadPool(2);
//		mRequests = new ConcurrentHashMap<String, ImageContainer>();
//	}
//
//	/**
//	 * 复写get方法,增加本地是否有缓存的逻辑
//	 */
//	@Override
//	public ImageContainer get(String requestUrl, ImageListener imageListener,
//			int maxWidth, int maxHeight) {
//		String cacheKey = getCacheKey(requestUrl, maxWidth, maxHeight);
//		// 不在缓存,则从本地找
//		if (mCache.getBitmap(cacheKey) == null) {
//			File file = new File(mLocalDirsPath,
//					mLocalName.getFileName(cacheKey));
//			// 如果缓存文件存在,那么加入到缓存
//			if (file.exists()) {
//				mPool.execute(new LoadLocalTask(cacheKey, file
//						.getAbsolutePath(), requestUrl, imageListener,
//						maxWidth, maxHeight));
//				return null;
//			}
//		}
//		ImageContainer container = super.get(requestUrl, imageListener,
//				maxWidth, maxHeight);
//		// 如果bitmap为空,表示容器中没有缓存,即把它添加到请求Map中
//		if (null == container.getBitmap()) {
//			mRequests.put(cacheKey, container);
//		}
//		return container;
//	}
//
//	/**
//	 * 加载本地图片
//	 *
//	 * @param path
//	 *            图片路径
//	 * @param imageListener
//	 *            图片监听
//	 * @param maxWidth
//	 *            最大宽度
//	 * @param maxHeight
//	 *            最大高度
//	 */
//	public void loadImage(String path, ImageListener imageListener,
//			int maxWidth, int maxHeight) {
//		String key = getCacheKey(path, maxWidth, maxHeight);
//		// 不在缓存,则从本地找
//		if (mCache.getBitmap(key) == null) {
//			File file = new File(path);
//			// 如果缓存文件存在,那么加入到缓存
//			if (file.exists()) {
//				mPool.execute(new LoadLocalTask(key, file.getAbsolutePath(),
//						path, imageListener, maxWidth, maxHeight));
//			}
//		} else {
//			Bitmap bitmap = mCache.getBitmap(key);
//			ImageContainer container = new ImageContainer(bitmap, path, null,
//					null);
//			imageListener.onResponse(container, true);
//		}
//	}
//
//	/**
//	 * 复写onGetImageSuccess,增加线程池来管理持久化
//	 */
//	@Override
//	protected void onGetImageSuccess(String cacheKey, Bitmap response) {
//		// 持久化图片缓存
//		File file = new File(mLocalDirsPath, mLocalName.getFileName(cacheKey));
//		if (!file.exists()) {
//			mPool.execute(new LocalTask(file.getAbsolutePath(), response));
//		}
//		super.onGetImageSuccess(cacheKey, response);
//		// 从请求Map中移除这条请求,因为它已经完成
//		mRequests.remove(cacheKey);
//	}
//
//	@Override
//	protected void onGetImageError(String cacheKey, VolleyError error) {
//		super.onGetImageError(cacheKey, error);
//		// 从请求Map中移除这条请求,因为它已经完成
//		mRequests.remove(cacheKey);
//	}
//
//	/**
//	 * <p>
//	 * <b>持久化图片到本地的Task</b>
//	 * </P>
//	 *
//	 * @version 1.0
//	 * @since 1.0
//	 * @author Shun
//	 *
//	 */
//	private class LocalTask implements Runnable {
//
//		private String mPath;
//		private Bitmap mBitmap;
//
//		public LocalTask(String path, Bitmap bitmap) {
//			this.mPath = path;
//			this.mBitmap = bitmap;
//		}
//
//		@Override
//		public void run() {
//			FileOutputStream out = null;
//			try {
//				if (null != mBitmap) {
//					out = new FileOutputStream(mPath);
//					mBitmap.compress(CompressFormat.PNG, 100, out);
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} finally {
//				if (out != null) {
//					try {
//						out.close();
//						out = null;
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 加载本地图片Task
//	 *
//	 * @version 1.0
//	 * @since 1.0
//	 * @author Shun
//	 *
//	 */
//	private class LoadLocalTask implements Runnable {
//		String mCacheKey;
//		String mPath;
//		String mUrl;
//		ImageListener mImageListener;
//		int mWidth;
//		int mHeight;
//
//		public LoadLocalTask(String cacheKey, String path, String url,
//				ImageListener imageListener, int width, int height) {
//			this.mCacheKey = cacheKey;
//			this.mPath = path;
//			this.mUrl = url;
//			this.mImageListener = imageListener;
//			this.mWidth = width;
//			this.mHeight = height;
//		}
//
//		@Override
//		public void run() {
//			Bitmap bitmap = XKImageUtils.loadBitmap(mPath, mWidth, mHeight);
//			if (null != bitmap) {
//				mCache.putBitmap(mCacheKey, bitmap);
//			}
//			// 这个Hanlder是父类的全局变量
//			mHandler.postDelayed(new DeliverTask(bitmap, mUrl, mImageListener),
//					100);
//		}
//	}
//
//	/**
//	 * 转发到主线程的Task
//	 *
//	 * @version 1.0
//	 * @since 1.0
//	 * @author Shun
//	 *
//	 */
//	private class DeliverTask implements Runnable {
//		Bitmap mCacheBitmap;
//		String mUrl;
//		ImageListener mImageListener;
//
//		public DeliverTask(Bitmap cacheBitmap, String url,
//				ImageListener imageListener) {
//			this.mCacheBitmap = cacheBitmap;
//			this.mUrl = url;
//			this.mImageListener = imageListener;
//		}
//
//		@Override
//		public void run() {
//			ImageContainer container = new ImageContainer(mCacheBitmap, mUrl,
//					null, null);
//			if (null != mCacheBitmap)
//				mImageListener.onResponse(container, true);
//			else
//				mImageListener.onErrorResponse(null);
//		}
//	}
//
//	/**
//	 *
//	 * <p>
//	 * <b>取消所有图片请求</b>
//	 * </p>
//	 * 注意的是,此类接口对本地图片无效
//	 */
//	public void cancelRequest() {
//		for (String key : mRequests.keySet()) {
//			ImageContainer container = mRequests.get(key);
//			if (null != container)
//				container.cancelRequest();
//		}
//		mRequests.clear();
//	}
//
//	/**
//	 *
//	 * <p>
//	 * <b>取消指定的请求</b>
//	 * </p>
//	 *
//	 * @param url
//	 *            请求的URL
//	 */
//	public void cancelRequest(String url) {
//		cancelRequest(url, 0, 0);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * <b>取消指定的请求</b>
//	 * </p>
//	 *
//	 * @param url
//	 *            请求的URL
//	 * @param maxWidth
//	 *            加载时设置的宽度
//	 * @param maxHeight
//	 *            加载时设置的高度
//	 */
//	public void cancelRequest(String url, int maxWidth, int maxHeight) {
//		String key = getCacheKey(url, maxWidth, maxHeight);
//		ImageContainer container = mRequests.remove(key);
//		if (null != container) {
//			container.cancelRequest();
//		}
//	}
//
//	/**
//	 *
//	 * <p>
//	 * <b>清除所有缓存</b>
//	 * </p>
//	 */
//	public void clearCache() {
//		if (null != mCache) {
//			mCache.clear();
//		}
//	}
//
//	/**
//	 *
//	 * <p>
//	 * <b>清除指定缓存</b>
//	 * </p>
//	 *
//	 * @param urlOrPath
//	 *            可以是Url可以是本地路径
//	 */
//	public boolean cearCache(String urlOrPath) {
//		return clearCache(urlOrPath, 0, 0);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * <b>清除指定缓存</b>
//	 * </p>
//	 * 注意maxWidth,maxHeight必须与缓存请求时的大小保持一致
//	 *
//	 * @param urlOrPath
//	 *            可以是Url可以是本地路径
//	 * @param maxWidth
//	 *            加载时设置的宽度
//	 * @param maxHeight
//	 *            加载时设置的高度
//	 */
//	public boolean clearCache(String urlOrPath, int maxWidth, int maxHeight) {
//		if (null != mCache) {
//			Bitmap bitmap = mCache.remove(getCacheKey(urlOrPath, maxWidth,
//					maxHeight));
//			if (null != bitmap) {
//				if (!bitmap.isRecycled()) {
//					bitmap.recycle();
//				}
//				bitmap = null;
//				return true;
//			} else {
//				return false;
//			}
//		}
//		return false;
//	}
//}
