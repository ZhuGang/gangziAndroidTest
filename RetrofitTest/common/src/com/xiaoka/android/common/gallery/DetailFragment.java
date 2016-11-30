/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xiaoka.android.common.gallery.adapter.AlbumDetailAdapter;
import com.xiaoka.android.common.gallery.data.ImageItem;
import com.xiaoka.android.common.utils.XKToastUtil;
import com.xiaoka.android.common.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 详细的图片集合展示<br/>
 * 内部私有的,暂不对外暴露
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
@SuppressLint("ValidFragment")
class DetailFragment extends Fragment {
	public static final String TAG = "DetailFragment";
	private static final String IMAGE_DIRS = "image_dirs";
	private static final String EXIST_PATHS = "exist_paths";
	private static final String MAX_SELECT_NUM = "max_select_num";
	private GridView mDetailGridView;
	private AlbumDetailAdapter mAlbumDetailAdapter;
	private int mMaxSelectNum;

	public DetailFragment() {

	}

	/**
	 * 
	 * <p>
	 * <b>得到一个Fragment实例</b>
	 * </p>
	 * 
	 * @param dirs
	 *            路径
	 * @param existPaths
	 *            已经存在的图片路径集合
	 * @param maxSelectNum
	 *            最大的可选择数
	 * @return
	 */
	public static DetailFragment newInstance(Serializable dirs,
			ArrayList<String> existPaths, int maxSelectNum) {
		DetailFragment fragment = new DetailFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(IMAGE_DIRS, dirs);
		bundle.putStringArrayList(EXIST_PATHS, existPaths);
		bundle.putInt(MAX_SELECT_NUM, maxSelectNum);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_gallery_detail_item, container,
				false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mDetailGridView = (GridView) view.findViewById(R.id.gv_gallery_detail);
		mMaxSelectNum = getArguments()
				.getInt(MAX_SELECT_NUM, Integer.MAX_VALUE);
		ArrayList<String> existPaths = getArguments().getStringArrayList(
				EXIST_PATHS);
		if (null == existPaths)
			existPaths = new ArrayList<String>();
		mAlbumDetailAdapter = new AlbumDetailAdapter(
				(List<ImageItem>) getArguments().getSerializable(IMAGE_DIRS),
				getActivity(), existPaths);
		mDetailGridView.setAdapter(mAlbumDetailAdapter);
		mDetailGridView.setOnItemClickListener(new ItemClick());
	}

	public ArrayList<String> getExistPaths() {
		return mAlbumDetailAdapter.getExistPaths();
	}

	class ItemClick implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String path = mAlbumDetailAdapter.getPaths().get(position).imagePath;
			switch (mAlbumDetailAdapter.getItemViewType(position)) {
			case AlbumDetailAdapter.TYPE_SELECT:
				mAlbumDetailAdapter.getExistPaths().remove(path);
				break;
			case AlbumDetailAdapter.TYPE_UN_SELECT:
				if (mMaxSelectNum == mAlbumDetailAdapter.getExistPaths().size()) {
					String str = getResources().getString(
							R.string.gallery_max_select_num);
					XKToastUtil.showToast(String.format(str, mMaxSelectNum),
                            getActivity());
					return;
				}
				mAlbumDetailAdapter.getExistPaths().add(path);
				break;
			default:
				break;
			}
			mAlbumDetailAdapter.notifyDataSetChanged();
		}
	}
}
