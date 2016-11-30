package com.xiaoka.android.common.widget.quickadapter;

import android.content.Context;

import java.util.List;


/**
 * XKBaseQuickAdapter的简单封装
 * @author bingo
 */
public abstract class XKQuickAdapter<T> extends XKBaseQuickAdapter<T, XKBaseAdapterHelper> {

    public XKQuickAdapter(Context context, int layoutResId) {
        super(context);
    }

    public XKQuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context, data);
    }

}
