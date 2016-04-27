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

import com.xiaoka.android.common.gallery.data.ImageItem;
import com.xiaoka.android.common.image.XKDefaultImageManager;
import com.xiaoka.android.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册详情适配器
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public class AlbumDetailAdapter extends BaseAdapter {
    public static final int TYPE_UN_SELECT = 0;
    public static final int TYPE_SELECT = 1;
    private List<ImageItem> mPaths;
    private LayoutInflater inflater;
    private ArrayList<String> mExistPaths;

    public AlbumDetailAdapter(List<ImageItem> paths, Context context,
                              ArrayList<String> existPaths) {
        mPaths = paths;
        mExistPaths = existPaths;
        inflater = ((Activity) context).getLayoutInflater();
    }

    public ArrayList<String> getExistPaths() {
        return mExistPaths;
    }

    public List<ImageItem> getPaths() {
        return mPaths;
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
            convertView = inflater.inflate(
                    R.layout.list_gallery_album_detail_item, null);
            holder.iv = (ImageView) convertView
                    .findViewById(R.id.iv_gallery_detail_item);
            holder.border = convertView
                    .findViewById(R.id.v_gallery_detail_item_border);
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.iv_gallery_detail_item_select_icon);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        String path = getImagePath(position);
        XKDefaultImageManager.getInstance().loadImage(path, holder.iv);

        switch (getItemViewType(position)) {
            case TYPE_UN_SELECT:
                holder.border.setBackgroundColor(0);
                holder.icon.setImageBitmap(null);
                break;
            case TYPE_SELECT:
                holder.border
                        .setBackgroundResource(R.drawable.gallery_detail_item_select_border_bg);
                holder.icon
                        .setImageResource(R.drawable.gallery_detail_item_select_icon);
                break;
            default:
                break;
        }
        return convertView;
    }

    private String getImagePath(int position) {
        String path = mPaths.get(position).thumbnailPath;
        // TODO 缩略图可能为空,那么实现原始图片
        if (TextUtils.isEmpty(path)) {
            path = mPaths.get(position).imagePath;
        }
        return path;
    }

    @Override
    public int getItemViewType(int position) {
        return mExistPaths.contains(mPaths.get(position).imagePath) ? TYPE_SELECT
                : TYPE_UN_SELECT;
    }

    static class Holder {
        private ImageView iv;
        private View border;
        private ImageView icon;
    }
}
