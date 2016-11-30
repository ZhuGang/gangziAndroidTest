/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.image.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public class XKImageUtils {

    /**
     * <p>
     * <b>加载本地图片</b>
     * </p>
     * 这是一个无OOM问题的加载本地图片接口
     *
     * @param filePath 图片路径
     * @param width    期望的宽度
     * @param height   期望的高度
     * @return 最理想的Bitmap
     */
    @SuppressWarnings("deprecation")
    public static Bitmap loadBitmap(String filePath, int width, int height) {
        try {
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            if (width == 0 && height == 0) {
                decodeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
                return BitmapFactory.decodeStream(
                        new FileInputStream(filePath), null, decodeOptions);
            }
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(filePath), null,
                    decodeOptions);
            int desiredWidth = getResizedDimension(width, height,
                    decodeOptions.outWidth, decodeOptions.outHeight);
            int desiredHeight = getResizedDimension(height, width,
                    decodeOptions.outHeight, decodeOptions.outWidth);
            decodeOptions.inJustDecodeBounds = false;
            decodeOptions.inSampleSize = findBestSampleSize(
                    decodeOptions.outWidth, decodeOptions.outHeight,
                    desiredWidth, desiredHeight);
            decodeOptions.inPurgeable = true;
            decodeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap tempBitmap = BitmapFactory.decodeStream(new FileInputStream(
                    filePath), null, decodeOptions);
            // 这个时候如果实例尺寸还是>理想尺寸,那么将缩放到理想尺寸
            if (tempBitmap != null
                    && (tempBitmap.getWidth() > desiredWidth || tempBitmap
                    .getHeight() > desiredHeight)) {
                Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap,
                        desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
                tempBitmap = null;
                return bitmap;
            } else {
                return tempBitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * 计算图片缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * 缩放图片
     *
     * @param src       图片源
     * @param dstWidth  缩放宽度
     * @param dstHeight 缩放高度
     * @param filter
     * @return
     */
    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                           int dstHeight, boolean filter) {
        Bitmap dst = Bitmap
                .createScaledBitmap(src, dstWidth, dstHeight, filter);
        if (src != dst) {
            src.recycle();
            src = null;
        }
        return dst;
    }

    /**
     * Scales one side of a rectangle to fit aspect ratio.
     *
     * @param maxPrimary      Maximum size of the primary dimension (i.e. width for max
     *                        width), or zero to maintain aspect ratio with secondary
     *                        dimension
     * @param maxSecondary    Maximum size of the secondary dimension, or zero to maintain
     *                        aspect ratio with primary dimension
     * @param actualPrimary   Actual size of the primary dimension
     * @param actualSecondary Actual size of the secondary dimension
     */
    private static int getResizedDimension(int maxPrimary, int maxSecondary,
                                           int actualPrimary, int actualSecondary) {
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling
        // ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * Returns the largest power-of-two divisor for use in downscaling a bitmap
     * that will not result in the scaling past the desired dimensions.
     *
     * @param actualWidth   Actual width of the bitmap
     * @param actualHeight  Actual height of the bitmap
     * @param desiredWidth  Desired width of the bitmap
     * @param desiredHeight Desired height of the bitmap
     */
    static int findBestSampleSize(int actualWidth, int actualHeight,
                                  int desiredWidth, int desiredHeight) {
        // 出自Volley,根据实例的高宽和期望的高宽,计算出一个最小的比例
        // 注意的是N=1f,2倍递增,直到它比最小的比例大才返回一个合适的samplesize
        // actualWidth = 1000 ,actualHeight=500
        // 理想的desiredWidth = 200 , desiredHeight = 100
        // while(n=6<5) return 6;
        // 则n=6为最理想的samplesize
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }

    /**
     * <p>
     * <b>得到图片的角度信息</b>
     * </p>
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * <p>
     * <b>根据角度旋转图片</b>
     * </p>
     * 此接口会回收源Bitmap内存,返回一个旋转后的
     *
     * @param b
     * @param degrees 角度
     * @return
     */
    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees == 0 || null == b) {
            return b;
        }
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            Bitmap resizedBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                    b.getHeight(), matrix, true);
            return resizedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            b.recycle();
            b = null;
        }
        return null;
    }

    /**
     * <p>
     * <b>保存一个Bitmap到本地</b>
     * </p>
     * 此接口会覆盖原来的图片,如果它存在
     *
     * @param src       保存的Bitmap
     * @param quality   保存质量 0~100
     * @param savePath  保存路径
     * @param isRecycle 是否要回收
     */
    public static void saveBitmap(Bitmap src, int quality, String savePath,
                                  boolean isRecycle) {
        try {
            File file = new File(savePath);
            if (file.exists()) {
                file.delete();
            }
            src.compress(CompressFormat.JPEG, quality, new FileOutputStream(
                    savePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (isRecycle)
                if (null != src) {
                    src.recycle();
                    src = null;
                }
        }
    }
}
