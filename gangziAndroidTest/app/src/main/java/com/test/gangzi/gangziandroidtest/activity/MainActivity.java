package com.test.gangzi.gangziandroidtest.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.test.gangzi.gangziandroidtest.AppComponent;
import com.test.gangzi.gangziandroidtest.R;
import com.test.gangzi.gangziandroidtest.app.base.BaseActivity;
import com.test.gangzi.gangziandroidtest.activity.component.DaggerMainActivityComponent;
import com.test.gangzi.gangziandroidtest.activity.module.MainActivityModule;
import com.test.gangzi.gangziandroidtest.activity.presenter.MainActivityPresenter;
import com.xiaoka.android.common.annotation.ui.XKLayout;
import com.xiaoka.android.common.annotation.ui.XKView;

import javax.inject.Inject;


@XKLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @XKView(R.id.tv)
    TextView textView;

    @Inject
    MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.showUserName();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);

    }

    public void setTextView(String username) {
        textView.setText(username);
    }

}
