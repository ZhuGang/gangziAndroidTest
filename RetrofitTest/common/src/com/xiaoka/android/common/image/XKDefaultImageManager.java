/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.okhttp.OkHttpClient;
import com.xiaoka.android.common.image.utils.XKImageUtils;
import com.xiaoka.android.common.utils.XKDisplayUtil;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 默认的图片管理器
 *
 * @author Shun
 * @version 3.0
 * @since 1.0
 */
public class XKDefaultImageManager implements ImageManager {
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;
    private final static int CONNECT_TIME_OUT = 15;
    private final static int READ_TIME_OUT = 15;
    private final static int WRITE_TIME_OUT = 15;
    private static XKDefaultImageManager mInstance;
    private Context mContext;
    private Config mConfig;
    private List<Target> mTargetList = new ArrayList<>();

    private XKDefaultImageManager() {
    }

    public static XKDefaultImageManager getInstance() {
        if (null == mInstance) {
            mInstance = new XKDefaultImageManager();
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context 建议使用ApplicationContext
     * @param config  配置信息,请参考 {@link ImageManager.Config}
     */
    public void init(Context context, Config config) {
        this.mContext = context;
        this.mConfig = config;
        SCREEN_WIDTH = XKDisplayUtil.getWidth(context);
        SCREEN_HEIGHT = XKDisplayUtil.getHeigth(context);
        //使用OKHttp作为网络协议栈
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        client.setReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        client.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        Glide.get(context).setMemoryCategory(MemoryCategory.HIGH);
    }

    /**
     * 请求加载一张图片
     *
     * @param url       资源地址
     * @param imageView imageview
     */
    @Override
    public void requestImage(final String url, final View imageView) {
        requestImage(url, imageView, getImageListener(imageView, mConfig.getDefaultRes(), mConfig.getErrorRes()));
    }

    /**
     * 请求一张网络图片<br/>
     * 它将根据w与h 缩放出一个合适大小的Bitmap,对内存有更好的控制
     *
     * @param url       资源地址
     * @param imageView imageview
     * @param w         理想宽度
     * @param h         理想高度
     */
    public void requestImage(final String url, final View imageView, int w, int h) {
        requestImage(url, imageView, w, h, mConfig.getDefaultRes(), mConfig.getErrorRes());
    }

    /**
     * 请求一张网络图片<br/>
     * 需自行设置ImageView显示的内容
     *
     * @param url           资源地址
     * @param imageView     imageview
     * @param imageListener 图片监听器
     */
    public void requestImage(final String url, final View imageView, final XKImageListener imageListener) {
        requestImage(url, 0, 0, imageView, imageListener);
    }

    /**
     * 请求一张网络图片<br/>
     * 它将根据maxWidth与maxHeight 缩放出一个合适大小的Bitmap,对内存有更好的控制<br/>
     * 当这个请求发生错误时,会显示errorRes提供的资源,默认时即显示defaultRes提供的资源
     *
     * @param url        资源地址
     * @param imageView  imageview
     * @param w          理想宽度
     * @param h          理想高度
     * @param errorRes   错误时显示的资源
     * @param defaultRes 默认显示的资源
     */
    public void requestImage(String url, final View imageView, int w, int h, int errorRes, int defaultRes) {
        requestImage(url, w, h, imageView, getImageListener(imageView, defaultRes, errorRes));
    }

    /**
     * 请求一张网络图片<br/>
     * 这个接口会根据w和h2个参数,来自动计算, Bitmap应该使用的Size,而这2个参数往往就是显示容器
     * <code>ImageView</code> 的高宽.这样能有效的节省图片所占用的内存资源.
     * 如果w与h其中一个为了0,那么不会去计算bitmap大小. 目标 <code>Bitmap</code>
     * 有多么的大,就会去显示多大.建议使用了此接口的地方 最好指定这2个参数
     *
     * @param url      资源地址
     * @param w        理想宽度
     * @param h        理想高度
     * @param iv       imageview
     * @param listener 图片监听器,如果想监听到图片成功加载的消息,可以自行实现这个参数.需要注意的是,如果设置了此参数,那么不会自动给
     *                 <code>ImageView</code>设置请求到的图片,需要自行在此回调接口中设置
     */
    private void requestImage(final String url, final int w, final int h, final View iv, final XKImageListener listener) {
        if (TextUtils.isEmpty(url)) {
            if (mConfig.getDefaultRes() != 0)
                listener.onError(mContext.getResources().getDrawable(mConfig.getErrorRes()));
            return;
        }
        Target target = new ViewTarget<View, GlideDrawable>(iv) {
            @Override
            public void onStart() {
                super.onStart();
                if (iv instanceof ImageView) {
                    ((ImageView) this.view).setImageBitmap(null);
                } else {
                    this.view.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                if (resource instanceof GlideBitmapDrawable) {
                    listener.onSuccess(((GlideBitmapDrawable) resource).getBitmap(), false);
                } else if (resource instanceof GifDrawable) {
                    listener.onSuccess(XKImageUtils.drawableToBitmap(resource), false);
                } else {
                    listener.onSuccess(XKImageUtils.drawableToBitmap(resource), false);
                }
                mTargetList.remove(this);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                listener.onPrepareLoad(placeholder);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listener.onError(errorDrawable);
                mTargetList.remove(this);
            }

            @Override
            public void getSize(SizeReadyCallback cb) {
                if (w == 0 && h == 0) {
                    super.getSize(cb);
                } else {
                    cb.onSizeReady(w, h);
                }
            }
        };
        DrawableTypeRequest request = Glide.with(mContext).load(url);
        if (iv instanceof ImageView) {
            ImageView.ScaleType type = ((ImageView) iv).getScaleType();
            if (type != null) {
                switch (type) {
                    case CENTER_CROP:
                        request.centerCrop();
                        // applyCenterCrop();
                        break;
                    case FIT_CENTER:
                    case FIT_START:
                    case FIT_END:
                        request.fitCenter();
                        //  applyFitCenter();
                        break;
                    //$CASES-OMITTED$
                    default:
                        // Do nothing.
                }
            }
        }
        request.placeholder(mConfig.getDefaultRes()).error(mConfig.getErrorRes()).crossFade().into(target);
        mTargetList.add(target);
    }

    /**
     * 得到一个图片监听器
     *
     * @param image      图片对象
     * @param defaultRes 默认是显示的图片资源
     * @param errorRes   错误时显示的图片资源
     * @return 一个实现后的默认图片监听器
     */
    private static XKImageListener getImageListener(final View image, final int defaultRes, final int errorRes) {
        return new XKImageListener() {
            @Override
            public void onSuccess(Bitmap bitmap, boolean isImmediate) {
                if (image instanceof ImageView) {
                    ((ImageView) image).setImageBitmap(bitmap);
                } else {
                    if (Build.VERSION.SDK_INT >= 16) {
                        image.setBackground(new BitmapDrawable(bitmap));
                    } else {
                        image.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                }
            }

            @Override
            public void onError(Drawable errorDrawable) {
                //优先使用errorRes
                errorDrawable = (errorRes == 0 ? errorDrawable : image.getResources().getDrawable(errorRes));
                if (image instanceof ImageView) {
                    ((ImageView) image).setImageDrawable(errorDrawable);
                } else {
                    if (Build.VERSION.SDK_INT >= 16) {
                        image.setBackground(errorDrawable);
                    } else {
                        image.setBackgroundDrawable(errorDrawable);
                    }
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                //优先使用defaultRes
                placeHolderDrawable = (defaultRes == 0 ? placeHolderDrawable : image.getResources().getDrawable(defaultRes));
                if (image instanceof ImageView) {
                    ((ImageView) image).setImageDrawable(placeHolderDrawable);
                } else {
                    if (Build.VERSION.SDK_INT >= 16) {
                        image.setBackground(placeHolderDrawable);
                    } else {
                        image.setBackgroundDrawable(placeHolderDrawable);
                    }
                }
            }
        };
    }

    /**
     * 加载本地图片
     *
     * @param path      图片路径
     * @param imageView
     */
    @Override
    public void loadImage(String path, final ImageView imageView) {
        loadImage(path, imageView, getImageListener(imageView, 0, 0));
    }

    /**
     * 加载本地图片
     *
     * @param path
     * @param imageView
     * @param listener
     */
    public void loadImage(final String path, final ImageView imageView, final XKImageListener listener) {
        if (TextUtils.isEmpty(path)) {
            if (mConfig.getDefaultRes() != 0)
                listener.onError(mContext.getResources().getDrawable(mConfig.getErrorRes()));
            return;
        }
        if (imageView.getWidth() == 0 && imageView.getHeight() == 0) {
            final ViewTreeObserver vto = imageView.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    final int width = imageView.getMeasuredWidth();
                    final int height = imageView.getMeasuredHeight();
                    loadImage(path, imageView, width, height, listener);
                    return true;
                }
            });
        } else {
            loadImage(path, imageView, imageView.getWidth(), imageView.getHeight(), listener);
        }
    }

    /**
     * 加载本地图片
     *
     * @param path
     * @param iv
     * @param w
     * @param h
     */
    public void loadImage(final String path, final ImageView iv, final int w, final int h, final XKImageListener listener) {
        //final XKImageListener listener = getImageListener(iv, mConfig.getDefaultRes(), mConfig.getErrorRes());
        if (TextUtils.isEmpty(path)) {
            if (mConfig.getDefaultRes() != 0)
                listener.onError(mContext.getResources().getDrawable(mConfig.getErrorRes()));
            return;
        }
        Target target = new ViewTarget<View, GlideDrawable>(iv) {
            @Override
            public void onStart() {
                super.onStart();
                if (this.view instanceof ImageView) {
                    ((ImageView) this.view).setImageBitmap(null);
                } else {
                    this.view.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                if (resource instanceof GlideBitmapDrawable) {
                    listener.onSuccess(((GlideBitmapDrawable) resource).getBitmap(), false);
                } else if (resource instanceof GifDrawable) {
                    listener.onSuccess(XKImageUtils.drawableToBitmap(resource), false);
                } else {
                    listener.onSuccess(XKImageUtils.drawableToBitmap(resource), false);
                }
                mTargetList.remove(this);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                listener.onPrepareLoad(placeholder);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listener.onError(errorDrawable);
                mTargetList.remove(this);
            }

            @Override
            public void getSize(SizeReadyCallback cb) {
                if (w == 0 && h == 0) {
                    super.getSize(cb);
                } else {
                    cb.onSizeReady(w, h);
                }
            }
        };
        DrawableTypeRequest request = Glide.with(mContext).load(new File(path));
        if (iv instanceof ImageView) {
            ImageView.ScaleType type = ((ImageView) iv).getScaleType();
            if (type != null) {
                switch (type) {
                    case CENTER_CROP:
                        request.centerCrop();
                        // applyCenterCrop();
                        break;
                    case FIT_CENTER:
                    case FIT_START:
                    case FIT_END:
                        request.fitCenter();
                        //  applyFitCenter();
                        break;
                    //$CASES-OMITTED$
                    default:
                        // Do nothing.
                }
            }
        }
        request.placeholder(mConfig.getDefaultRes()).error(mConfig.getErrorRes()).crossFade().into(target);
        mTargetList.add(target);
    }

    @Override
    public void cancelRequest() {
        if (mContext != null) {
            for (Target t : mTargetList) {
                Glide.clear(t);
            }
        }
    }

    /**
     * 图片监听(对外暴露的监听接口)
     *
     * @author Shun
     * @version 1.0
     * @since 1.0
     */
    public interface XKImageListener {

        void onSuccess(Bitmap bitmap, boolean isImmediate);

        void onError(Drawable errorDrawable);

        void onPrepareLoad(Drawable placeHolderDrawable);

    }

    /**
     * 简单的图片监听实现
     *
     * @author Shun
     * @version 1.0
     * @since 1.0
     */
    public static class XKSimpleImageListener implements XKImageListener {

        @Override
        public void onSuccess(Bitmap bitmap, boolean isImmediate) {

        }

        @Override
        public void onError(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    }
}
