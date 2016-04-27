/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xiaoka.android.common.R;

/**
 * <p>
 * <b>轻轻轻量级的抽屉组件</b>
 * </p>
 * 就是需要轻 :)
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 *
 */
public class XKDrawerLayout extends ViewGroup {

	private ViewDragHelper mDragHelper;
	private View mLeftPanle;
	private boolean mEdgeEnabled;
	private int mLeftId;
	private float mMinDrawerMargin;

	public XKDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public XKDrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}

	public XKDrawerLayout(Context context) {
		super(context);
		init(context, null);
	}

	/**
	 * 
	 *<p><b>初始化</b></p>
	 * @param context
	 * @param attrs
	 */
	private void init(Context context, AttributeSet attrs) {
		final float density = getResources().getDisplayMetrics().density;
		final float minVel = 400 * density;
		mMinDrawerMargin = (10 * density + 0.5f);
		mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
		mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
		mDragHelper.setMinVelocity(minVel);
		mEdgeEnabled = true;
		loadAttrs(context, attrs);
	}

	/**
	 * 
	 * <p>
	 * <b>加载属性</b>
	 * </p>
	 * 
	 * @param context
	 * @param attrs
	 */
	private void loadAttrs(Context context, AttributeSet attrs) {
		if (null != attrs) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.XKDrawerLayout);
			mLeftId = a.getResourceId(R.styleable.XKDrawerLayout_leftPanel, 0);
			a.recycle();
		}
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LinearLayout.LayoutParams(getContext(), attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return p instanceof LayoutParams ? new LayoutParams((LayoutParams) p)
				: p instanceof MarginLayoutParams ? new LayoutParams(
						(MarginLayoutParams) p) : new LayoutParams(p);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		// 检查Layout是否合法
		return p instanceof LayoutParams && super.checkLayoutParams(p);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 通过资源Id设置LeftView的引用
		if (mLeftId != 0) {
			mLeftPanle = findViewById(mLeftId);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int count = getChildCount();
		// 计算子类的大小
		for (int i = 0; i < count; i++) {
			View v = getChildAt(i);
			if (v.getVisibility() == View.GONE)
				continue;
			int widthSpec = 0;
			int heightSpec = 0;
			LayoutParams params = (LayoutParams) v.getLayoutParams();
			if (params.width > 0) {
				widthSpec = MeasureSpec.makeMeasureSpec(params.width,
						MeasureSpec.EXACTLY);
			} else if (params.width == -1) {
				widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
						MeasureSpec.EXACTLY);
			} else if (params.width == -2) {
				widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
						MeasureSpec.AT_MOST);
			}

			if (params.height > 0) {
				heightSpec = MeasureSpec.makeMeasureSpec(params.height,
						MeasureSpec.EXACTLY);
			} else if (params.height == -1) {
				heightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
						MeasureSpec.EXACTLY);
			} else if (params.height == -2) {
				heightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
						MeasureSpec.AT_MOST);
			}
			v.measure(widthSpec, heightSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int size = getChildCount();
		// 满高度的存在,不解释..
		for (int i = 0; i < size; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() == View.GONE) {
				continue;
			}
			// 只关注左侧边距
			if (child == mLeftPanle) {
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				child.layout(lp.leftMargin, lp.topMargin,
						lp.leftMargin + child.getMeasuredWidth(), lp.topMargin
								+ child.getMeasuredHeight());
			}
		}
	}

	/**
	 * 
	 * <p>
	 * <b>设置左侧面板</b>
	 * </p>
	 * 
	 * @param panel
	 */
	public void setLeftPanel(View panel) {
		mLeftPanle = panel;
		addView(panel);
	}

	/**
	 * 
	 * <p>
	 * <b>设置左侧面板</b>
	 * </p>
	 * 
	 * @param layoutId
	 */
	public void setLeftPanel(int layoutId) {
		setLeftPanel(LayoutInflater.from(getContext()).inflate(layoutId, this,
				false));
	}

	@Override
	public void computeScroll() {
		if (mDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	/**
	 * 
	 * <p>
	 * <b>显示</b>
	 * </p>
	 */
	public void show() {
		smoothSlideTo(0f);
	}

	/**
	 * 
	 * <p>
	 * <b>隐藏</b>
	 * </p>
	 */
	public void hide() {
		smoothSlideTo(-1f);
	}

	private boolean smoothSlideTo(float slideOffset) {
		if (mDragHelper.smoothSlideViewTo(mLeftPanle, (int) slideOffset
				* mLeftPanle.getWidth(), mLeftPanle.getTop())) {
			ViewCompat.postInvalidateOnAnimation(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = MotionEventCompat.getActionMasked(ev);
		if (action == MotionEvent.ACTION_CANCEL
				|| action == MotionEvent.ACTION_UP) {
			mDragHelper.cancel();
			return false;
		}
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mDragHelper.processTouchEvent(ev);
		int x = (int) ev.getX();
		// int y = (int) ev.getY();
		boolean isViewUnder = mDragHelper.isViewUnder(mLeftPanle,
				(int) ev.getX(), (int) ev.getY());
		boolean isNeed = false;
		switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if (!isViewUnder && x < mMinDrawerMargin) {
				isNeed = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (Math.abs(mLeftPanle.getLeft()) < mLeftPanle.getWidth() * 0.4) {
				show();
				mEdgeEnabled = false;
			} else {
				hide();
				mEdgeEnabled = true;
			}
			mDragHelper.cancel();
			break;
		default:
			break;
		}
		return isViewUnder || isNeed;
	}

	/**
	 * <p>
	 * <b>拖拽回调</b>
	 * </p>
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @author Shun
	 *
	 */
	private class DragHelperCallback extends ViewDragHelper.Callback {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child == mLeftPanle;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {

			float r = 1 - Math.abs((float) left / mLeftPanle.getWidth());
			setBackgroundColor(Color.argb((int) (r * 204), 0, 0, 0));
			invalidate();
		}

		@Override
		public void onEdgeTouched(int edgeFlags, int pointerId) {
			super.onEdgeTouched(edgeFlags, pointerId);
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			if (mEdgeEnabled)
				mDragHelper.captureChildView(mLeftPanle, pointerId);
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (left > 0) {
				left = 0;
			} else if (left < -mLeftPanle.getWidth()) {
				left = -mLeftPanle.getWidth();
			}
			return left;
		}
	}

	/**
	 * <p>
	 * <b>使用的是MarginLayoutParams</b>
	 * </p>
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @author Shun
	 *
	 */
	public static class LayoutParams extends MarginLayoutParams {

		public LayoutParams(ViewGroup.LayoutParams p) {
			super(p);
		}

		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public LayoutParams(int w, int h) {
			super(w, h);
		}
	}
}
