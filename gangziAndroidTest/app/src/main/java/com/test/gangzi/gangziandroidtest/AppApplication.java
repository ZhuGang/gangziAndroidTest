package com.test.gangzi.gangziandroidtest;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.test.gangzi.gangziandroidtest.app.ActivityManager;
import com.test.gangzi.gangziandroidtest.app.CrashHandler;
import com.test.gangzi.gangziandroidtest.config.SDCardConstant;
import com.test.gangzi.gangziandroidtest.config.XKConstant;
import com.test.gangzi.gangziandroidtest.data.AppServiceModule;
import com.test.gangzi.gangziandroidtest.data.api.ApiServiceModule;
import com.test.gangzi.gangziandroidtest.protocol.sign.Signer;
import com.xiaoka.android.common.http.XKRetrofitClient;
import com.xiaoka.android.common.image.ImageManager;
import com.xiaoka.android.common.image.XKDefaultImageManager;
import com.xiaoka.android.common.image.cache.local.name.MD5LocalName;
import com.xiaoka.android.common.log.XKLog;
import com.xiaoka.android.common.utils.XKToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gangzi on 2015/6/9.
 */
public class AppApplication  extends Application {

    private AppComponent appComponent;

    //private static BaseComponent sBaseComponent;

    private static AppApplication instatnce;

    private static XKRetrofitClient restClient;

    public final static String KEY_META_V = "version";
    public final static String KEY_META_V_CODE = "version_code";

    private static Map<String,String> headers;

    public static Map<String, String> getHeaders() {
        return headers;
    }

   // public static BaseComponent getBaseComponent(){
//        return sBaseComponent;
//    }



    public static AppApplication get(Context context){
        return (AppApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .appServiceModule(new AppServiceModule())
                .build();
        instatnce = this;

        initImageClient();
        createResDir();
        XKLog.init(SDCardConstant.DEBUG_LOG.getAbsolutePath());
        CrashHandler.getInstance().init(this);
        headers = new HashMap<String, String>();
        
//        try {
//            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//            int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
//            headers.put(KEY_META_V, versionName);
//            headers.put(KEY_META_V_CODE, String.valueOf(versionCode));
//            headers.put("os", XKConstant.OS);
//            headers.put("appType", "1"); //1代表是养车，告诉服务端用养车的签名
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

//        restClient = new XKRetrofitClient.Build()
//                .setHeaders(headers)
//                .setHost(XKDevelopEnvironmentConfig.HOST)
//                .setLogLevel(1)
//                .setSign(new Signer())
//                .create();
        XKToastUtil.setApplicationContext(this);//由于有些模块功能引用了common库的功能，而common库使用了common库中的XKToashUtil

    }

    public static AppApplication getInstance() {
        return instatnce;
    }
    public static XKRetrofitClient getRestClient() {
        return restClient;
    }

    /**
     * 初始化图片组件
     */
    private void initImageClient() {
        ImageManager.Config config = ImageManager.Config.newInstance().setLocalDirs(SDCardConstant.IMG_CACHE.getAbsolutePath()).setLocalName(new MD5LocalName()).setDefaultRes(R.drawable.default_image);
        XKDefaultImageManager.getInstance().init(this, config);
    }

    /**
     * 退出App
     */
    public static void exitApp() {
        ActivityManager.getInstance().finishAll();
        XKDefaultImageManager.getInstance().cancelRequest();
    }

    /**
     * 创建SD资源目录
     */
    private void createResDir() {
        if (!SDCardConstant.ROOT.exists()) {
            SDCardConstant.ROOT.mkdir();
        }
        if (!SDCardConstant.IMG_CACHE.exists()) {
            SDCardConstant.IMG_CACHE.mkdir();
        }
        if (!SDCardConstant.VOICE_CACHE.exists()) {
            SDCardConstant.VOICE_CACHE.mkdir();
        }
        if (!SDCardConstant.IMG_USER_PIC_CACHE.exists()) {
            SDCardConstant.IMG_USER_PIC_CACHE.mkdir();
        }
        if (!SDCardConstant.ADVERTISING_CACHE.exists()) {
            SDCardConstant.ADVERTISING_CACHE.mkdir();
        }
        if (!SDCardConstant.TEMP_CACHE.exists()) {
            SDCardConstant.TEMP_CACHE.mkdir();
        }
        if (!SDCardConstant.LOG.exists()) {
            SDCardConstant.LOG.mkdir();
        }

        if (!SDCardConstant.DEBUG_LOG.exists()) {
            SDCardConstant.DEBUG_LOG.mkdir();
        }
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }
}
