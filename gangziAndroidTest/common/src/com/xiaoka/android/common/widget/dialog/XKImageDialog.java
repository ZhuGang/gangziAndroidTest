/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.widget.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.xiaoka.android.common.R;
import com.xiaoka.android.common.image.XKDefaultImageManager;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 全屏显示图片的Dialog
 * </p>
 * <li>支持设Dialog的背景颜色,具体参考<code>setBackgroundXXX()</code>
 * 开头的接口,当使用默认的背景颜色时,会是Dialog风格的背景颜色</li> <li>
 * 支持滑动显示多张图片</li><li>
 * 支持滑动显示网络图片,或者本地图片</li><li>
 * 支持设置图片显示的缩放方式</li><li>
 * 内部对OOM情况做了处理,如果显示的是网络图片,会持久化到本地</li><li>
 * 内置维护加载图片时的加载框</li>
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public class XKImageDialog extends XKFullscreenDialog {

    private ViewPager mInnerViewPager;
    private List<Integer> mRess;
    private List<String> mPaths;
    private List<String> mUrls;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private int mSource;
    private boolean mIsCancel;

    public XKImageDialog(Context context) {
        super(context, R.style.XK_Theme_Image_Dialog);
        init();
    }

    public XKImageDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public void setRes(int res) {
        mRess = Collections.singletonList(res);
        mSource = 3;
    }

    public void setRess(List<Integer> ress) {
        mRess = ress;
        mSource = 3;
    }

    /**
     * <p>
     * <b>设置本地图片路径</b>
     * </p>
     *
     * @param path
     */
    public void setPath(String path) {
        if (TextUtils.isEmpty(path))
            return;
        mPaths = Collections.singletonList(path);
        mSource = 1;
    }

    /**
     * <p>
     * <b>设置本地路径集合</b>
     * </p>
     *
     * @param paths
     */
    public void setPaths(List<String> paths) {
        if (isEmpty(paths))
            return;
        mPaths = paths;
        mSource = 1;
    }

    /**
     * <p>
     * <b>设置网路图片地址</b>
     * </p>
     *
     * @param url
     */
    public void setUrl(String url) {
        if (TextUtils.isEmpty(url))
            return;
        mUrls = Collections.singletonList(url);
        mSource = 2;
    }

    /**
     * <p>
     * <b>设置网络图片地址集合</b>
     * </p>
     *
     * @param urls
     */
    public void setUrls(List<String> urls) {
        if (isEmpty(urls))
            return;
        mUrls = urls;
        mSource = 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(mInnerViewPager,
                new LayoutParams(-1, -1));
    }

    private void init() {
        setDimAmount(0.9f);
        mInnerViewPager = new ViewPager(getContext());
    }

    /**
     * <p>
     * <b>设置是否点击图片取消</b>
     * </p>
     *
     * @param isCancel
     */
    public void setClickCancel(boolean isCancel) {
        mIsCancel = isCancel;
    }

    /**
     * <p>
     * <b>设置图片的缩放类型</b>
     * </p>
     *
     * @param type {@linkplain android.widget.ImageView.ScaleType}
     */
    public void setImageScaleType(ScaleType type) {
        mScaleType = type;
    }

    /**
     * <p>
     * <b>设置容器缓存View的数量</b>
     * </p>
     * 默认为2个
     *
     * @param num 缓存数量
     */
    public void setCacheNum(int num) {
        mInnerViewPager.setOffscreenPageLimit(num);
    }

    @Override
    public void setContentView(int layoutResID) {
        // 不允许外部设置显示的View
    }

    @Override
    public void setContentView(View view) {
        // 不允许外部设置显示的View
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        // 不允许外部设置显示的View
    }

    @Override
    public void show() {
        super.show();
        mInnerViewPager.setAdapter(new ImageAdapter());
    }

    /**
     * <p>
     * <b>设置显示的索引</b>
     * </p>
     *
     * @param selectIndex
     */
    public void setSelection(int selectIndex) {
        mInnerViewPager.setCurrentItem(selectIndex, false);
    }

    /**
     * <p>
     * <b>显示图片的适配器</b>
     * </p>
     *
     * @author Shun
     * @version 1.0
     * @since 1.0
     */
    class ImageAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        ImageAdapter() {
            inflater = LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            switch (mSource) {
                case 2:
                    return mUrls.size();
                case 1:
                    return mPaths.size();
                default:
                    return mRess.size();
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_pager_image_dialog, container, false);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsCancel)
                        cancel();
                }
            });
            final ImageView imageView = (ImageView) layout.findViewById(R.id.iv_image_dialog_item_pic);
            imageView.setScaleType(mScaleType);
            final ProgressBar bar = (ProgressBar) layout.findViewById(R.id.pb_image_dialog_item_loadingbar);
            switch (mSource) {
                case 2:
                    XKDefaultImageManager.getInstance().requestImage(mUrls.get(position), imageView, new XKDefaultImageManager.XKSimpleImageListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap, boolean isImmediate) {
                            super.onSuccess(bitmap, isImmediate);
                            bar.setVisibility(View.GONE);
                            imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onError(Drawable errorDrawable) {
                            super.onError(errorDrawable);
                            bar.setVisibility(View.GONE);
                        }
                    });
                    break;
                case 1:
                    XKDefaultImageManager.getInstance().loadImage(mPaths.get(position), imageView, new XKDefaultImageManager.XKSimpleImageListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap, boolean isImmediate) {
                            super.onSuccess(bitmap, isImmediate);
                            bar.setVisibility(View.GONE);
                            imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onError(Drawable errorDrawable) {
                            super.onError(errorDrawable);
                            bar.setVisibility(View.GONE);
                        }
                    });
                    break;
                default:
                    bar.setVisibility(View.GONE);
                    imageView.setImageResource(mRess.get(position));
            }

            container.addView(layout, 0);
            return layout;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0.equals(arg1);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private static boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }
}
