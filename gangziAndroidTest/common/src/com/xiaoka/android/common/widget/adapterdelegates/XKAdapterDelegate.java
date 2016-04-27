/*
 * Copyright (c) 2015 Hannes Dorfmann.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.xiaoka.android.common.widget.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoka.android.common.widget.quickadapter.XKBaseAdapterHelper;

/**
 * 对AdapterDelegate的简单实现
 * @author bingo
 */
public abstract class XKAdapterDelegate<T> implements AdapterDelegate<T>,
        View.OnClickListener {
    private int mLayoutResId;
    protected int mViewType;

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public XKAdapterDelegate(int pViewType) {
      this.mLayoutResId = getLayoutResId();
      this.mViewType = pViewType;
    }

    @Override public int getItemViewType() {
      return mViewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
      View _View = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
      _View.setOnClickListener(this);
      XKBaseAdapterHelper _Vh = new XKBaseAdapterHelper(_View);
      return _Vh;
    }

    @Override
    public void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder holder) {
        XKBaseAdapterHelper helper = (XKBaseAdapterHelper) holder;
        helper.itemView.setTag(position);

        convert(helper, items, position);
    }

    protected abstract void convert(XKBaseAdapterHelper helper, T items, int postion);

    protected abstract int getLayoutResId();

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
