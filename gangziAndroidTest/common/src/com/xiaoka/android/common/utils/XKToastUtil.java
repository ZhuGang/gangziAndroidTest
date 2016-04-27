package com.xiaoka.android.common.utils;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.xiaoka.android.common.widget.dialog.XKDialog;
import com.xiaoka.android.common.widget.dialog.XKIosStyleDialog;

/**
 * 提示工具类
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public class XKToastUtil {

    private static Toast toast;

    private static Context mApplicationContext;

    public static void setApplicationContext(Context appContext) {
        mApplicationContext = appContext;
    }

    /**
     * 设置一个自定义的弱提示容器
     *
     * @param toast
     */
    public static void setToast(Toast toast) {
        XKToastUtil.toast = toast;
    }

    /**
     * 提示
     *
     * @param id      显示的字符资源id
     * @param context
     */
    public static void showToast(int id, Context context) {
        showToast(id, context, Toast.LENGTH_SHORT);
    }

    /**
     * 提示
     *
     * @param id      显示的字符资源id
     * @param context
     * @param time    短||长显示
     */
    public static void showToast(int id, Context context, int time) {
        if (toast == null) {
            toast = Toast.makeText(mApplicationContext == null ? context.getApplicationContext():mApplicationContext, context.getResources()
                    .getString(id), time);
        } else {
            toast.setText(context.getResources().getString(id));
        }
        toast.show();
    }

    /**
     * 提示
     *
     * @param msg     显示的字符
     * @param context
     */
    public static void showToast(String msg, Context context) {
        showToast(msg, context, Toast.LENGTH_SHORT);
    }

    /**
     * 提示
     *
     * @param msg     显示的字符
     * @param context
     * @param time    短||长显示
     */
    public static void showToast(String msg, Context context, int time) {
        if (toast == null) {
            toast = Toast.makeText(mApplicationContext == null ? context.getApplicationContext():mApplicationContext, msg, time);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public interface AlertDialogListener {
        public void positiveButtonClick();

        public void negativeButtonClick();
    }

    /**
     * 显示ios风格对话框
     *
     * @param context
     * @param title        标题
     * @param msg          提示语
     * @param cancelText   取消按钮文本
     * @param positiveText 确定按钮文本
     * @param listener     取消 和 确定执行的回调
     */
    public static void showIOSAlertDialog(Context context, String title, String msg, String cancelText, String positiveText, final AlertDialogListener listener) {
        if (context == null) {
            return;
        }

        final XKIosStyleDialog mDiyDialog = new XKIosStyleDialog(context).builder()
                .setTitle(title)
                .setMsg(msg)
                .setPositiveButton(positiveText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.positiveButtonClick();
                        }
                    }
                });
        if (cancelText != null) {
            mDiyDialog.setNegativeButton(cancelText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.negativeButtonClick();
                }
            });
        }

        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing() || context.isRestricted()) {
                mDiyDialog.show();
            }
        }

    }

    /**
     * Android 5.0风格对话框
     *
     * @param context          上下文
     * @param title            标题
     * @param msg              内容
     * @param positiveText     确认按钮文字
     * @param negativeText     取消按钮文字
     * @param positiveListener 确认按钮监听
     * @param negativeListener 取消按钮监听
     */
    public static void showMaterialDialog(Context context, String title, String msg, String positiveText, String negativeText, XKDialog.XKDialogItemClickListener positiveListener, XKDialog.XKDialogItemClickListener negativeListener) {
        new XKDialog.MaterialBuilder(context).setTitle(title).setMessage(msg).setPositiveButton(positiveText, positiveListener).setNegativeButton(negativeText, negativeListener).create().show();
    }

}
