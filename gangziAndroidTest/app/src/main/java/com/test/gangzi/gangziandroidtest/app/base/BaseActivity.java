package com.test.gangzi.gangziandroidtest.app.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.gangzi.gangziandroidtest.AppApplication;
import com.test.gangzi.gangziandroidtest.AppComponent;
import com.test.gangzi.gangziandroidtest.R;
import com.test.gangzi.gangziandroidtest.app.ActivityManager;
import com.xiaoka.android.common.annotation.ui.XKUIAnnotationParser;

/**
 * Created by gangzi on 2015/6/10.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActivityComponent(AppApplication.get(this).getAppComponent());
        ActivityManager.getInstance().pushActivity(this);

        XKUIAnnotationParser.parserActivity(this);
        init();
    }


    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.mipmap.ic_arrows_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.getInstance().finishActivity();
                    onNavigationOnClick();
                }
            });
            mToolbar.setTitle("");
            mToolbar.setTitleTextColor(R.color.black);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.loadingdialog, null);
        loadingDialog = new Dialog(this, R.style.selectorDialog);
        loadingDialog.setContentView(view);
        loadingDialog.setCancelable(false);
    }

    public void onNavigationOnClick() {
    }

    /**
     * 得到Toolbar
     *
     * @return
     */
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != loadingDialog) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }

    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing())
            loadingDialog.show();
    }

    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    public void setDialogCancelable(boolean flag) {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.setCancelable(flag);
    }

    public boolean dialogIsShowing() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void dealError(int errorCode, String errorMsg) {
        switch (errorCode) {
//                    case ErrorCode.PHONE_OTHER_LOGIN:
//                    case ErrorCode.USER_TOKEN_OUT_TIME:
//                    case ErrorCode.USER_INFO_MODIFY:
//                    case ErrorCode.USER_TOKEN_INVALID:
//                    case ErrorCode.USER_LOGIN_OTHER:
//                        if (ActivityManager.getInstance().getSize() > 0 && ActivityManager.getInstance().getCurrentActivity() instanceof LoginActivity) {
//                            return;
//                        }
//                        XKToastUtil.showToast(errorMsg, false);
//                        StorageController.getInstance().logout();
//                        LoginActivity.launch(XKApplication.getInstance());
//                        ActivityManager.getInstance().finishOther(LoginActivity.class.getSimpleName());
//                        break;
//                    case ErrorCode.NETWORK_ERROR:
//                        if (NetWorkUtil.getNetworkState(XKApplication.getInstance()) == NetWorkUtil.NETWORN_NONE) {
//                            XKToastUtil.showToast("网络未连接", false);
//                        } else {
//                            XKToastUtil.showToast("服务器连接错误", false);
//                        }
//                        break;
//                    default:
//                        XKToastUtil.showToast(errorMsg, false);
        }
    }

//            @Override
//            public void onBackPressed() {
//                super.onBackPressed();
//                ActivityManager.getInstance()
//                        .finishActivity();
//            }


    /**
     * 展示活动的dialog
     */
//    private void showActivityDialog(final String webUrl) {
//                if(Utils.isRunningForeground(XKApplication.getInstance())) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityManager.getInstance().getCurrentActivity());
//                    builder.setTitle("典典养车").setMessage("你有新的报名活动可以参加").setPositiveButton("查看", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (!TextUtils.isEmpty(webUrl)) {
//                                NotificationManager nm = (NotificationManager) XKApplication.getInstance().getSystemService(XKApplication.getInstance().NOTIFICATION_SERVICE);
//                                nm.cancel(15556); //取消下拉菜单中的推送
//                                H5Activity.launch(ActivityManager.getInstance().getCurrentActivity(), webUrl, XKConstant.H5PAGE_BEHAVIOR);
//                            }
//                        }
//                    }).setNegativeButton("取消", null);
//                    builder.show();
//                }
//            }
//
//    public void error(Throwable t) {
//                KLog.d(t.getMessage());
//                XKToastUtil.showToast(t.getMessage(), false);
//    }
//
//    public void finallayExcute() {
//                hideLoading();
//            }
    protected abstract void setupActivityComponent(AppComponent appComponent);
}
