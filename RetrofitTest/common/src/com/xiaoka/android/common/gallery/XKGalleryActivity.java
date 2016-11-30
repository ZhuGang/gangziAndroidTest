/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xiaoka.android.common.gallery.adapter.AlbumAdapter;
import com.xiaoka.android.common.gallery.data.ImageBucket;
import com.xiaoka.android.common.gallery.data.ImageItem;
import com.xiaoka.android.common.gallery.data.XKAlbumHelper;
import com.xiaoka.android.common.R;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 相册容器<br/>
 * 它是一个基于<code>v4.FragmentActivity</code>的相册容器<br/>
 * 启动它需要以下一些参数(Intent):<br/>
 * {@link com.xiaoka.android.common.gallery.XKGalleryActivity#TAG_MAX_SELECT_NUM}<br/>
 * {@link com.xiaoka.android.common.gallery.XKGalleryActivity#TAG_EXIST_PATHS_TAG}<br/>
 * {@link com.xiaoka.android.common.gallery.XKGalleryActivity#TAG_DIRS_TAG}<br/>
 * 如果想得到此容器选中的一些图片信息,启动方需要实现{@link android.app.Activity#onActivityResult(int, int, android.content.Intent)}
 * 
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
public class XKGalleryActivity extends FragmentActivity {

	/**
	 * 允许最大的选择数量(类型int)
	 */
	public static final String TAG_MAX_SELECT_NUM = "max_select_num";

	/**
	 * 现有选中的数量(类型<code>ArrayList</code>),如果设定了此参数<br/>
	 * 会默认选中这些图片
	 */
	public static final String TAG_EXIST_PATHS_TAG = "exist_paths";

	/**
	 * 相册集的各个路径(必须是<code>ArrayList</code>),如果不指定此参数<br/>
	 * 那么会使用内置的数据展示
	 */
	public static final String TAG_DIRS_TAG = "dirs";

	public static final int REQUEST_CODE_GALLERY = 3;

	private TextView mCancelText;
	private TextView mCompleteText;
	private int mMaxNum;
	private ArrayList<String> mExistPaths;
	private ArrayList<String> mDirs;
	private GridView mGridView;
	private List<ImageBucket> mDatas;
	private DetailFragment mFragment;

	/**
	 * 
	 * <p>
	 * <b>跳转到相册</b>
	 * </p>
	 * 
	 * @param from
	 *            需要跳转的Activity
	 * @param maxPicNum
	 *            最大的图片数量
	 * @param picList
	 *            已有的图片集合
	 * @return {@link com.xiaoka.android.common.gallery.XKGalleryActivity#REQUEST_CODE_GALLERY}
	 */
	public static int jumpToGallery(Activity from, int maxPicNum,
			ArrayList<String> picList) {
		Intent intent = new Intent(from, XKGalleryActivity.class);
		intent.putExtra(XKGalleryActivity.TAG_MAX_SELECT_NUM, maxPicNum);
		intent.putExtra(XKGalleryActivity.TAG_EXIST_PATHS_TAG, picList);
		intent.putExtra(XKGalleryActivity.TAG_MAX_SELECT_NUM, maxPicNum);
		from.startActivityForResult(intent, REQUEST_CODE_GALLERY);
		return REQUEST_CODE_GALLERY;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mMaxNum = getIntent()
				.getIntExtra(TAG_MAX_SELECT_NUM, Integer.MAX_VALUE);
		mExistPaths = getIntent().getStringArrayListExtra(TAG_EXIST_PATHS_TAG);
		mDirs = getIntent().getStringArrayListExtra(TAG_DIRS_TAG);
		setContentView(R.layout.activity_gallery_layout);
		initDefaultData();
		initData();
		findUI();
		listener();
		adapter();
	}

	private void findUI() {
		mCancelText = (TextView) findViewById(R.id.tv_cancel);
		mGridView = (GridView) findViewById(R.id.gridview);
		mCompleteText = (TextView) findViewById(R.id.tv_complete);
	}

	private void listener() {
		mCancelText.setOnClickListener(new CancelClick());
		mGridView.setOnItemClickListener(new DetailItemClick());
		mCompleteText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent result = new Intent();
				result.putStringArrayListExtra(TAG_EXIST_PATHS_TAG,
						mFragment.getExistPaths());
				setResult(REQUEST_CODE_GALLERY, result);
				finish();
			}
		});
		getSupportFragmentManager().addOnBackStackChangedListener(
				new FragmentManager.OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged() {
						if (null == mFragment || !mFragment.isVisible()) {
							mCompleteText.setVisibility(View.GONE);
						}
					}
				});
	}

	private void adapter() {
		mGridView.setAdapter(new AlbumAdapter(mDatas, this));
	}

	private void initDefaultData() {
		if (null == mDirs || mDirs.size() == 0) {
			XKAlbumHelper.getHelper().init(this);
			mDatas = XKAlbumHelper.getHelper().getImagesBucketList(false);
		}
	}

	private void initData() {
		if (null != mDirs) {
			ListIterator<String> iterator = mDirs.listIterator();
			mDatas = new ArrayList<ImageBucket>();
			File file;
			File[] files;
			while (iterator.hasNext()) {
				String path = (String) iterator.next();
				file = new File(path);
				if (!file.exists() || !file.isDirectory()) {
					// 不存直接移除
					iterator.remove();
					continue;
				} else {
					files = file.listFiles(filefilter);
					if (null == files || files.length == 0) {
						// 内部可用数量为0时移除
						iterator.remove();
					} else {
						ImageBucket imageBucket = new ImageBucket();
						imageBucket.count = files.length;
						path = files[0].getAbsolutePath();
						imageBucket.bucketName = file.getName();
						imageBucket.imageList = new ArrayList<ImageItem>();
						for (File subFile : files) {
							ImageItem item = new ImageItem();
							item.imagePath = subFile.getAbsolutePath();
							item.thumbnailPath = item.imagePath;
							imageBucket.imageList.add(item);
						}
						mDatas.add(imageBucket);
					}
				}
			}
		}
	}

	/**
	 * 取消监听
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @author Shun
	 * 
	 */
	class CancelClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (null != mFragment && mFragment.isVisible()) {
				getSupportFragmentManager().popBackStack();
				return;
			} else {
				finish();
			}
		}
	}

	/**
	 * 相册点击
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @author Shun
	 * 
	 */
	class DetailItemClick implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			FragmentManager manager = getSupportFragmentManager();
			if (null != manager) {
				FragmentTransaction transaction = manager.beginTransaction();
				ArrayList<String> copy = new ArrayList<String>();
				copy.addAll(0, mExistPaths);
				mFragment = DetailFragment.newInstance(
						(Serializable) mDatas.get(position).imageList, copy,
						mMaxNum);
				transaction.add(R.id.fl_gallery_container, mFragment);
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack(DetailFragment.TAG);
				transaction.commit();
				mCompleteText.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * 文件过滤器(jpg,png)
	 */
	private FileFilter filefilter = new FileFilter() {
		public boolean accept(File file) {
			if (file.getName().endsWith(".jpg")
					|| file.getName().endsWith(".png")) {
				return true;
			}
			return false;
		}
	};

	protected void onDestroy() {
		super.onDestroy();
	};
}
