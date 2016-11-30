/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/**
 * 全屏的Dialog
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public class XKFullscreenDialog extends XKDialog {

    public XKFullscreenDialog(Context context, int theme) {
        super(context, theme);
    }

    public XKFullscreenDialog(Context context) {
        super(context);
    }

    @Override
    public void setContentView(int layoutResID) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setBackgroundColor(Color.TRANSPARENT);
        super.setContentView(layoutResID);
        setContentLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public void setContentView(View view) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setBackgroundColor(Color.TRANSPARENT);
        super.setContentView(view);
        setContentLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void setContentView(View view,
                               android.view.ViewGroup.LayoutParams params) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setBackgroundColor(Color.TRANSPARENT);
        super.setContentView(view, params);
        setContentLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}
