package com.xiaoka.android.common.widget.quickadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一些通用操作的RecyclerView的Adapter
 * @author bingo
 */
public abstract class XKBaseQuickAdapter <T, H extends XKBaseAdapterHelper> extends RecyclerView.Adapter<XKBaseAdapterHelper> implements View.OnClickListener{
    protected static final String TAG = XKBaseQuickAdapter.class.getSimpleName();

    protected final Context context;

    protected final int layoutResId;

    protected final List<T> data;

    protected boolean displayIndeterminateProgress = false;

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public XKBaseQuickAdapter(Context context) {
        this(context, null);
    }

    public XKBaseQuickAdapter(Context context, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : data;
        this.context = context;
        this.layoutResId = getLayoutResId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public T getItem(int position) {
        if (position >= data.size()) return null;
        return data.get(position);
    }
    @Override
    public XKBaseAdapterHelper onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        view.setOnClickListener(this);
        XKBaseAdapterHelper vh = new XKBaseAdapterHelper(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(XKBaseAdapterHelper helper,  int position) {
        helper.itemView.setTag(position);
        T item = getItem(position);
        convert((H)helper, item);
    }

    protected abstract void convert(H helper, T item);

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    protected abstract int getLayoutResId();

}
