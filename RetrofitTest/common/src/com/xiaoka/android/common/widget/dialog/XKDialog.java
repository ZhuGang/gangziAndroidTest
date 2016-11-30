/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoka.android.common.R;
import com.xiaoka.android.common.utils.XKDisplayUtil;

import java.util.LinkedHashMap;

/**
 * <p>
 * <b>使用更方便的Dialog</b>
 * </p>
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public class XKDialog extends Dialog {

    public XKDialog(Context context) {
        super(context);
    }

    public XKDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * <p>
     * <b>设置Dialog的背景颜色</b>
     * </p>
     *
     * @param color 背景颜色
     */
    public void setBackgroundColor(int color) {
        setBackgroundDrawable(new ColorDrawable(color));
    }

    /**
     * <p>
     * <b>设置Dialog的背景颜色资源</b>
     * </p>
     *
     * @param res 背景颜色资源
     */
    public void setBackgroundRes(int res) {
        setBackgroundDrawable(getContext().getResources().getDrawable(res));
    }

    /**
     * <p>
     * <b>设置Dialog的背景颜色</b>
     * </p>
     *
     * @param drawable
     */
    public void setBackgroundDrawable(Drawable drawable) {
        getWindow().setBackgroundDrawable(drawable);
    }

    /**
     * <p>
     * <b>设置背景遮盖度</b>
     * </p>
     *
     * @param amount 0f~1f之间 0表示无遮盖 1表示完全遮盖
     */
    public void setDimAmount(float amount) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            getWindow().setDimAmount(amount);
        else {
            getWindow().getAttributes().dimAmount = amount;
            getWindow().setAttributes(getWindow().getAttributes());
        }
    }

    /**
     * <p>
     * <b>设置内容的位置</b>
     * </p>
     *
     * @param gravity
     */
    public void setContentLocation(int gravity) {
        getWindow().setGravity(gravity);
    }

    /**
     * <p>
     * <b>设置Dialog的宽度和高度</b>
     * </p>
     * 注意此接口一定要在setContextView之后才会生效
     *
     * @param w
     * @param h
     */
    public void setContentLayout(int w, int h) {
        getWindow().setLayout(w, h);
    }

    /**
     * <p>
     * <b>设置内容动画</b>
     * </p>
     *
     * @param res
     */
    public void setContentAnimationRes(int res) {
        getWindow().setWindowAnimations(res);
    }

    /**
     * 5.0风格对话框构建器
     *
     * @author Shun
     * @version 1.0
     * @since 2.0
     */
    public static class MaterialBuilder {
        private Context mContext;

        public MaterialBuilder(Context context) {
            mContext = context;
        }

        public MaterialBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public MaterialBuilder setMessage(String msg) {
            mMsg = msg;
            return this;
        }

        public MaterialBuilder setPositiveButton(String name, XKDialogItemClickListener click) {
            mPositive = name;
            mPositiveTextViewClick = click;
            return this;
        }

        public MaterialBuilder setNegativeButton(String name, XKDialogItemClickListener click) {
            mNegative = name;
            mNegativeTextViewClick = click;
            return this;
        }

        private TextView mTitleTextView;
        private TextView mMsgTextView;
        private TextView mPositiveTextView;
        private TextView mNegativeTextView;

        private XKDialogItemClickListener mPositiveTextViewClick;
        private XKDialogItemClickListener mNegativeTextViewClick;

        private String mTitle;
        private String mMsg;
        private String mPositive;
        private String mNegative;

        public XKDialog create() {
            final XKDialog dialog = new XKDialog(mContext);
            View layout = LayoutInflater.from(mContext).inflate(R.layout.material_dialog, null);
            mTitleTextView = (TextView) layout.findViewById(R.id.tv_material_title);
            mMsgTextView = (TextView) layout.findViewById(R.id.tv_material_content);
            mPositiveTextView = (TextView) layout.findViewById(R.id.tv_positive_button);
            mNegativeTextView = (TextView) layout.findViewById(R.id.tv_negative_button);
            mTitleTextView.setText(mTitle);
            mMsgTextView.setText(mMsg);
            mPositiveTextView.setText(mPositive);
            mPositiveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mPositiveTextViewClick) {
                        mPositiveTextViewClick.onClick(v, dialog);
                    }
                }
            });
            mNegativeTextView.setText(mNegative);
            mNegativeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mNegativeTextViewClick) {
                        mNegativeTextViewClick.onClick(v, dialog);
                    }
                }
            });
            dialog.setDimAmount(0.3f);
            dialog.setBackgroundColor(Color.TRANSPARENT);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(layout);
            dialog.setContentLayout(-1, -2);
            return dialog;
        }

    }

    /**
     * Item风格的Dialog 构建器
     *
     * @author Shun
     * @version 1.0.2
     * @since 1.0
     */
    public static class ItemBuilder {
        private LinkedHashMap<Object, XKDialogItemClickListener> mItemMap = new LinkedHashMap<Object, XKDialogItemClickListener>();
        private int mItemGravity = Gravity.CENTER;
        private Context mContext;
        private Drawable mContentDrawable;
        private Drawable mItemDrawable;
        private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;
        private Drawable mDivider = new ColorDrawable(Color.rgb(242, 242, 242));
        private int mDividerHeight;
        private int mItemHight;
        private View mExternal;

        public ItemBuilder(Context context) {
            mContext = context;
        }

        public ItemBuilder(Context context, int style) {
            mContext = context;
        }

        /**
         * <p>
         * <b>添加一个Item</b>
         * </p>
         *
         * @param charSequence Item文字
         * @param listener     点击监听
         * @return
         */
        public ItemBuilder addItem(CharSequence charSequence,
                                   XKDialogItemClickListener listener) {
            mItemMap.put(charSequence, listener);
            return this;
        }

        /**
         * <p>
         * <b>添加一个Item</b>
         * </p>
         *
         * @param view     一个自定义的View
         * @param listener 点击监听
         * @return
         */
        public ItemBuilder addItem(View view, XKDialogItemClickListener listener) {
            mItemMap.put(view, listener);
            return this;
        }

        /**
         * <p>
         * <b>设置Item的背景</b>
         * </p>
         * 只对
         * {@linkplain com.xiaoka.android.common.widget.dialog.XKDialog.ItemBuilder#addItem(CharSequence, com.xiaoka.android.common.widget.dialog.XKDialog.XKDialogItemClickListener)}
         * 这种方式添加的Item有效
         *
         * @param drawable
         * @return
         */
        public ItemBuilder setItemBackground(Drawable drawable) {
            mItemDrawable = drawable;
            return this;
        }

        /**
         * <p>
         * <b>设置Item的背景</b>
         * </p>
         * 只对
         * {@linkplain com.xiaoka.android.common.widget.dialog.XKDialog.ItemBuilder#addItem(CharSequence, com.xiaoka.android.common.widget.dialog.XKDialog.XKDialogItemClickListener)}
         * 这种方式添加的Item有效
         *
         * @param color
         * @return
         */
        public ItemBuilder setItemBackgroundColor(int color) {
            return setItemBackground(new ColorDrawable(color));
        }

        /**
         * <p>
         * <b>设置Item的背景</b>
         * </p>
         * 只对
         * {@linkplain com.xiaoka.android.common.widget.dialog.XKDialog.ItemBuilder#addItem(CharSequence, com.xiaoka.android.common.widget.dialog.XKDialog.XKDialogItemClickListener)}
         * 这种方式添加的Item有效
         *
         * @param res
         * @return
         */
        public ItemBuilder setItemBackgroundRes(int res) {
            return setItemBackground(mContext.getResources().getDrawable(res));
        }

        /**
         * <p>
         * <b>设置Dialog Content的背景</b>
         * </p>
         *
         * @param drawable
         * @return
         */
        public ItemBuilder setContentBackground(Drawable drawable) {
            mContentDrawable = drawable;
            return this;
        }

        /**
         * <p>
         * <b>设置Dialog Content的背景</b>
         * </p>
         *
         * @param color
         * @return
         */
        public ItemBuilder setContentBackgroundColor(int color) {
            return setContentBackground(new ColorDrawable(color));
        }

        /**
         * <p>
         * <b>设置Dialog Content的背景</b>
         * </p>
         *
         * @param res
         * @return
         */
        public ItemBuilder setContentBackgroundRes(int res) {
            return setContentBackground(mContext.getResources()
                    .getDrawable(res));
        }

        /**
         * <p>
         * <b>设置Item之间的间隔线的背景</b>
         * </p>
         *
         * @param drawable
         * @return
         */
        public ItemBuilder setDivider(Drawable drawable) {
            mDivider = drawable;
            return this;
        }

        /**
         * <p>
         * <b>设置Item之间的间隔线的高度</b>
         * </p>
         *
         * @param height
         * @return
         */
        public ItemBuilder setDividerHeight(int height) {
            mDividerHeight = height;
            return this;
        }

        /**
         * <p>
         * <b>设置Dialog Content的填充距离</b>
         * </p>
         * 默认0
         *
         * @param l 左侧填充距离
         * @param t 上侧填充距离
         * @param r 右侧填充距离
         * @param b 下侧填充距离
         * @return
         */
        public ItemBuilder setPadding(int l, int t, int r, int b) {
            mPaddingLeft = l;
            mPaddingTop = t;
            mPaddingRight = r;
            mPaddingBottom = b;
            return this;
        }

        /**
         * <p>
         * <b>设置Item中文字的居中位置</b>
         * </p>
         * 只对
         * {@linkplain com.xiaoka.android.common.widget.dialog.XKDialog.ItemBuilder#addItem(CharSequence, com.xiaoka.android.common.widget.dialog.XKDialog.XKDialogItemClickListener)}
         * 这种方式添加的Item有效
         *
         * @param gravity
         * @return
         */
        public ItemBuilder setItemTextGravity(int gravity) {
            mItemGravity = gravity;
            return this;
        }

        /**
         * <p>
         * <b>设置Item的高度</b>
         * </p>
         * 只对
         * {@linkplain com.xiaoka.android.common.widget.dialog.XKDialog.ItemBuilder#addItem(CharSequence, com.xiaoka.android.common.widget.dialog.XKDialog.XKDialogItemClickListener)}
         * 这种方式添加的Item有效
         *
         * @param height
         * @return
         */
        public ItemBuilder setItemHight(int height) {
            mItemHight = height;
            return this;
        }

        /**
         * 添加一个外部的View
         *
         * @param v
         * @return
         */
        public ItemBuilder setExternalView(View v) {
            mExternal = v;
            return this;
        }

        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        private View getDivider() {
            mDividerHeight = mDividerHeight == 0 ? XKDisplayUtil.dp2Px(mContext, 1) : mDividerHeight;
            View divider = new View(mContext);
            divider.setLayoutParams(new LinearLayout.LayoutParams(-1, mDividerHeight));
            if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                divider.setBackground(mDivider);
            } else {
                divider.setBackgroundDrawable(mDivider);
            }
            return divider;
        }

        /**
         * <p>
         * <b>创建Dialog</b>
         * </p>
         *
         * @return
         */
        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        public XKDialog create() {
            XKDialog dialog = new XKDialog(mContext);
            LinearLayout layout = new LinearLayout(mContext);
//            if (null == mContentDrawable)
//                mContentDrawable = new ColorDrawable(color.white);setPadding
            if (null != mContentDrawable) {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.setBackground(mContentDrawable);
                } else {
                    layout.setBackgroundDrawable(mContentDrawable);
                }
            }
            layout.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            layout.setOrientation(LinearLayout.VERTICAL);
            if (null != mExternal) {
                layout.addView(mExternal);
            }
            int count = 0;
            // 添加Item
            for (Object key : mItemMap.keySet()) {
                ++count;
                if (key instanceof CharSequence) {
                    CharSequence c = (CharSequence) key;
                    Button item = new Button(mContext);
                    item.setLayoutParams(new LinearLayout.LayoutParams(-1, mItemHight == 0 ? XKDisplayUtil.dp2Px(mContext, 50) : mItemHight));
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        item.setBackground(mItemDrawable == null ? mContext.getResources().getDrawable(R.drawable.itembuilder_item_bg) : mItemDrawable);
                    } else {
                        item.setBackgroundDrawable(mItemDrawable == null ? mContext.getResources().getDrawable(R.drawable.itembuilder_item_bg) : mItemDrawable);
                    }
                    item.setGravity(mItemGravity);
                    item.setTextColor(Color.rgb(88, 88, 88));
                    item.setTextSize(18);
                    item.setText(c);
                    setClickListener(item, mItemMap.get(key), dialog);
                    layout.addView(item);
                } else if (key instanceof View) {
                    setClickListener((View) key, mItemMap.get(key), dialog);
                    layout.addView((View) key);
                }
                if (count < mItemMap.size() && mDivider != null) {
                    layout.addView(getDivider());
                }
            }
            mItemMap.clear();
            dialog.setContentLocation(Gravity.BOTTOM);
            dialog.setContentAnimationRes(R.style.XK_Animation_Dialog_InToUp_OutToBottom);
            dialog.setDimAmount(0.3f);
            dialog.setBackgroundColor(Color.TRANSPARENT);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(layout);
            dialog.setContentLayout(-1, -2);
            mItemMap.clear();
            return dialog;
        }

        /**
         * <p>
         * <b>设置Item的Click监听</b>
         * </p>
         *
         * @param item
         * @param listener
         */
        private void setClickListener(final View item,
                                      final XKDialogItemClickListener listener, final XKDialog dialog) {
            if (null == listener)
                return;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, dialog);
                }
            });
        }
    }

    /**
     * <p>
     * <b>Dialog中的Item点击监听</b>
     * </p>
     *
     * @author Shun
     * @version 1.0
     * @since 1.0
     */
    public interface XKDialogItemClickListener {
        public void onClick(View item, XKDialog dialog);
    }
}
