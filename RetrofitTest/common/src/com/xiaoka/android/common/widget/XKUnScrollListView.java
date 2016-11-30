package com.xiaoka.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class XKUnScrollListView extends ListView {
	public XKUnScrollListView(Context context) {
		super(context);
	}

	public XKUnScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XKUnScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}