package com.test.gangzi.gangziandroidtest.app.base;//package com.example.lwp.mvp.app.base;
//
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.internal.view.menu.MenuBuilder;
//import android.support.v7.widget.ActionMenuPresenter;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.chediandian.business.app.ActivityManager;
//import com.xiaoka.android.common.annotation.ui.XKUIAnnotationParser;
//import com.chediandian.business.R;
//
///**
// * Fragment基类
// *
// * @author jianlan on 2015/6/10.
// * @version 1.0
// * @since 1.0
// */
//public class BaseFragment extends Fragment {
//    private Toolbar mToolbar;
//    protected View showView;
//    private TextView midTitle;
//    private ImageView ivRight;
//    private Dialog loadingDialog;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return showView = XKUIAnnotationParser.parserFragment(this, container);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        midTitle = (TextView) view.findViewById(R.id.title);
//
//        mToolbar = ((Toolbar) view.findViewById(R.id.toolbar));
//        init();
//
//    }
//
//    private void init() {
//        String title = getTitle();
//        if (null != midTitle && !TextUtils.isEmpty(title)) {
//            /**
//             *  title最多16个字节
//             * */
//            int length = 0; //字节长度
//            int index = 0; //当前索引
//            boolean outOfLength = false; //标记title的字节长度是否超过16
//            for (int i = 0; i < title.length(); i++) {
//                int ascii = Character.codePointAt(title, i);
//                if (ascii >= 0 && ascii <= 255) { //如果非中文，则算一个字节
//                    length++;
//                    if(length == 16) { //如果当前字节长度已经达到16，跳出循环，记录当前索引
//                        index = i;
//                        outOfLength = true;
//                        break;
//                    }
//                } else { //中文算两个字节
//                    length += 2;
//                    if(length > 16) { // 如果当前字节长度大于16，跳出循环，记录当前索引的前一位置
//                        index = i-1;
//                        outOfLength = true;
//                        break;
//                    } else if(length == 16) { //如果当前字节长度正好为16，则跳出循环，记录当前索引
//                        index = i;
//                        outOfLength = true;
//                        break;
//                    }
//                }
//            }
//            if (outOfLength && title.length() > index + 1) { //如果超出了规定的字符长度则截取
//                title = title.substring(0, index + 1) + "...";
//            }
//            midTitle.setText(title);
//            initMenu();
////            mToolbar.setTitle(title);
//
//            View view = LayoutInflater.from(getActivity()).inflate(R.layout.loadingdialog, null);
//            loadingDialog = new Dialog(getActivity(), R.style.selectorDialog);
//            loadingDialog.setContentView(view);
//            loadingDialog.setCancelable(false);
//        }
//    }
//
//    public void onRightImageViewClick() {
//
//    }
//
//    public void showRightImageView(int resId) {
//        ivRight = (ImageView) showView.findViewById(R.id.iv_right);
//        ivRight.setVisibility(View.VISIBLE);
//        ivRight.setImageResource(resId);
//
//        ivRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRightImageViewClick();
//            }
//        });
//    }
//
//    public String getTitle() {
//        return null;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (getActivity() != null) {
//            InputMethodManager imm = (InputMethodManager) getActivity()
//                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(showView.getWindowToken(), 0); // 强制隐藏键盘
//        }
//    }
//
//    public Toolbar getToolbar() {
//        return mToolbar;
//    }
//
//    public TextView getMidTitle() {
//        return midTitle;
//    }
//
//    protected void showNavigationIcon() {
//        mToolbar.setNavigationIcon(R.mipmap.ic_arrows_back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!onNavigationClickListener()) {
//                    if (getFragmentManager().getBackStackEntryCount() > 0) {
//                        getFragmentManager().popBackStack();
//                    } else {
//                        ActivityManager
//                                .getInstance().finishActivity();
//                    }
//                }
//            }
//        });
//    }
//
//    private void initMenu() {
//        MenuBuilder mb = new MenuBuilder(getActivity());
//        mb.setCallback(new MenuBuilder.Callback() {
//            @Override
//            public boolean onMenuItemSelected(MenuBuilder arg0, MenuItem arg1) {
//                return onToolBarOptionsItemSelected(arg1);
//            }
//
//            @Override
//            public void onMenuModeChange(MenuBuilder arg0) {
//            }
//        });
//        mToolbar.setMenu(mb, new ActionMenuPresenter(getActivity()));
//        onCreateToolBarOptionsMenu(mToolbar.getMenu(), new MenuInflater(
//                getActivity()));
//    }
//
//    /**
//     * 创建Menu时回调
//     *
//     * @param menu
//     * @param inflater
//     */
//    protected void onCreateToolBarOptionsMenu(Menu menu, MenuInflater inflater) {
//    }
//
//    /**
//     * 当点击了Toolbar上的menu则回调此接口
//     *
//     * @param item
//     * @return
//     */
//    public boolean onToolBarOptionsItemSelected(MenuItem item) {
//        return false;
//    }
//
//    protected boolean onNavigationClickListener() {
//        return false;
//    }
//
//
//    public void showLoading() {
//        if (loadingDialog != null && !loadingDialog.isShowing())
//            loadingDialog.show();
//
//    }
//
//    public void hideLoading() {
//        if (loadingDialog != null && loadingDialog.isShowing())
//            loadingDialog.dismiss();
//    }
//
//    public void setDialogCancelable(boolean flag) {
//        if (loadingDialog != null && loadingDialog.isShowing())
//            loadingDialog.setCancelable(flag);
//    }
//
//    public boolean dialogIsShowing() {
//        if(loadingDialog != null && loadingDialog.isShowing()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (null != loadingDialog) {
//            loadingDialog.cancel();
//            loadingDialog = null;
//        }
//    }
//
//
//
//}
//
//
