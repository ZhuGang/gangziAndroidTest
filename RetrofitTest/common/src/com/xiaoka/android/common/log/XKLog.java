package com.xiaoka.android.common.log;

import android.util.Log;

import com.xiaoka.android.common.utils.XKFileUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日志
 *
 * @author Shun
 * @version 2.2
 * @sing 2.2
 */
public class XKLog {
    public final static String TAG = "XKLog";
    private static XKLog mInstance;
    private boolean mIsShow;
    private boolean mEnabledStorage;
    private static String mRoot;
    private DateFormat mFormat;

    private XKLog() {
        mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 初始化
     *
     * @param root 保存的根目录
     */
    public static void init(String root) {
        mRoot = root;
        if (null == mInstance) {
            mInstance = new XKLog();
        }
    }

    public static XKLog getXKLog() {
        return mInstance;
    }

    /**
     * 是否启用日志储存
     *
     * @param enabled
     */
    public void setEnabledStorage(boolean enabled) {
        this.mEnabledStorage = enabled;
    }

    /**
     * 是否显示调试信息
     *
     * @param isShow
     */
    public void setShowLogInfo(boolean isShow) {
        this.mIsShow = isShow;
    }

    public void d(String msg) {
        d(TAG, msg);
    }

    public void d(Class clz, String msg) {
        d(clz.getSimpleName(), msg);
    }

    public void d(String tag, String msg) {
        if (mIsShow)
            Log.d(tag, msg);
        if (mEnabledStorage)
            storage(msg);
    }

    public void i(String msg) {
        i(TAG, msg);
    }

    public void i(Class clz, String msg) {
        i(clz.getSimpleName(), msg);
    }

    public void i(String tag, String msg) {
        if (mIsShow)
            Log.i(tag, msg);
        if (mEnabledStorage)
            storage(msg);
    }

    public void e(String msg) {
        e(TAG, msg);
    }

    public void e(Class clz, String msg) {
        e(clz.getSimpleName(), msg);
    }

    public void e(String tag, String msg) {
        if (mIsShow)
            Log.e(tag, msg);
        if (mEnabledStorage)
            storage(msg);
    }

    public void w(String msg) {
        w(TAG, msg);
    }

    public void w(Class clz, String msg) {
        w(clz.getSimpleName(), msg);
    }

    public void w(String tag, String msg) {
        if (mIsShow)
            Log.w(tag, msg);
        if (mEnabledStorage)
            storage(msg);
    }

    private void storage(String msg) {
        if (mEnabledStorage) {
            Calendar cal = Calendar.getInstance();//使用日历类
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            //拼接文件名
            String path = mRoot + "/log-" + year + "-" + month + "-" + day + "-" + hour + ".txt";
            msg = mFormat.format(new Date()) + ":" + msg;
            XKFileUtil.storageMsg(path, msg, false);
        }
    }

    public void finish() {
        mInstance = null;
    }
}
