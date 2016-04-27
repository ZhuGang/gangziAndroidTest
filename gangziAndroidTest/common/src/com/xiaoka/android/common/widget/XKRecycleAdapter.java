/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 封装了回收策略的适配器
 * 
 * @version 2.2
 * @since 2.2
 * @author Shun
 *
 * @param <T>
 *            任意Bean
 * @param <VH>
 *            子类必须实现它
 */
public abstract class XKRecycleAdapter<T, VH extends XKRecycleAdapter.ViewHolder> extends
		BaseAdapter {
	protected List<T> list;
	protected Context context;

	public XKRecycleAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;
	}

	protected int getItemCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public int getCount() {
		return getItemCount();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VH holder;
		if (convertView == null) {
			holder = onCreateViewHolder(parent, position);
			convertView = holder.getView();
			convertView.setTag(holder);
		} else {
			holder = (VH) convertView.getTag();
		}
		onBindViewHolder(holder, position);
		return holder.getView();
	}

	/**
	 * 创建Holder 布局文件
	 *
	 * @param viewGroup
	 * @param position
	 * @return
	 */
	public abstract VH onCreateViewHolder(ViewGroup viewGroup, int position);

	/**
	 * 绑定ViewHolder<br/>
	 * 在此接口设置显示数据
	 *
	 * @param viewHolder
	 * @param position
	 */
	public abstract void onBindViewHolder(VH viewHolder, int position);

	public static class ViewHolder {
		private View v;

		public ViewHolder(View v) {
			this.v = v;
		}

		public View getView() {
			return v;
		}
	}

}
