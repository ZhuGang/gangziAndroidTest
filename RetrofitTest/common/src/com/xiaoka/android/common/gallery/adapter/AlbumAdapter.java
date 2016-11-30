/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.gallery.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoka.android.common.gallery.data.ImageBucket;
import com.xiaoka.android.common.image.XKDefaultImageManager;
import com.xiaoka.android.common.R;

import java.util.List;

/**
 * 相册封面适配器
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
public class AlbumAdapter extends BaseAdapter {

	private List<ImageBucket> mPaths;
	private LayoutInflater inflater;

	public AlbumAdapter(List<ImageBucket> paths, Context context) {
		mPaths = paths;
		inflater = ((Activity) context).getLayoutInflater();
	}

	@Override
	public int getCount() {
		return null == mPaths ? 0 : mPaths.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.list_gallery_album_item,
					null);
			holder.iv = (ImageView) convertView
					.findViewById(R.id.iv_gallery_item_album);
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_gallery_item_name);
			holder.num = (TextView) convertView
					.findViewById(R.id.tv_gallery_item_num);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		String path = getImagePath(position);
		// 因为是同一张图片,这里小图起了另一个缓存KEY
		// 这样大图的时候就不会拿到小图的缓存了
		XKDefaultImageManager.getInstance().loadImage(path, holder.iv);
		holder.name.setText(mPaths.get(position).bucketName);
		holder.num
				.setText(String.valueOf(mPaths.get(position).imageList.size()));
		return convertView;
	}

	private String getImagePath(int position) {
		String path = mPaths.get(position).imageList.get(0).thumbnailPath;
		// TODO 缩略图可能为空,那么实现原始图片
		if (TextUtils.isEmpty(path)) {
			path = mPaths.get(position).imageList.get(0).imagePath;
		}
		return path;
	}

	static class Holder {
		private ImageView iv;
		private TextView name;
		private TextView num;
	}
}
