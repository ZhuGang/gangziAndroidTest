/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.image;


import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.xiaoka.android.common.image.cache.local.name.LocalName;

/**
 * 图片管理相关接口
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public interface ImageManager {

    /**
     * 请求一张网络图片,且更具实际返回情况显示具体的图像
     *
     * @param url       资源地址
     * @param imageView
     */
    void requestImage(String url, View imageView);

    /**
     * 加载本地图片
     *
     * @param path
     * @param image
     */
    void loadImage(String path, ImageView image);

    /**
     * 取消所有的图片请求
     */
    void cancelRequest();


    /**
     * 图片配置
     *
     * @author Shun
     * @version 1.0
     * @since 1.0
     */
    public static class Config {
        private static Config mConfig;
        private LocalName mLocalName;
        private String mLocalDirs;
        private Animation mAnim;
        private int mDefaultRes;
        private int mErrorRes;

        private Config() {

        }

        public static Config newInstance() {
            return mConfig = new Config();
        }


        public Config setErrorRes(int errorRes) {
            this.mErrorRes = errorRes;
            return mConfig;
        }

        public Config setDefaultRes(int defaultRes) {
            this.mDefaultRes = defaultRes;
            return mConfig;
        }

        public int getErrorRes() {
            return mErrorRes;
        }

        public int getDefaultRes() {
            return mDefaultRes;
        }

        /**
         * <p>
         * <b>设置缓存路径</b>
         * </p>
         *
         * @param localDirs
         * @return
         */
        public Config setLocalDirs(String localDirs) {
            mLocalDirs = localDirs;
            return mConfig;
        }

        /**
         * <p>
         * <b>设置缓存文件策略生成器</b>
         * </p>
         *
         * @param localName
         * @return
         */
        public Config setLocalName(LocalName localName) {
            mLocalName = localName;
            return mConfig;
        }

        /**
         * <p>
         * <b>设置加载图片成功时显示的动画</b>
         * </p>
         *
         * @param anim
         */
        public void setAnim(Animation anim) {
            this.mAnim = anim;
        }

        public LocalName getLocalName() {
            return mLocalName;
        }

        public String getLocalDirs() {
            return mLocalDirs;
        }

        public Animation getmAnim() {
            return mAnim;
        }

    }
}
