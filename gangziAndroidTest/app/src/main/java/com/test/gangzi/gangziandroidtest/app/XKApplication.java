package com.test.gangzi.gangziandroidtest.app;//package com.example.lwp.mvp.app;
//
//import android.content.Context;
//import android.support.multidex.MultiDex;
//
//import com.chediandian.business.BuildConfig;
//import com.chediandian.business.R;
//import com.chediandian.business.StethoSettings;
//import com.chediandian.business.config.SDCardConstant;
//import com.chediandian.business.dagger.components.ApplicationComponent;
//import com.chediandian.business.dagger.components.BaseComponent;
//import com.chediandian.business.dagger.components.DaggerApplicationComponent;
//import com.chediandian.business.dagger.components.DaggerBaseComponent;
//import com.chediandian.business.dagger.modules.ApplicationModule;
//import com.chediandian.business.gallary.listener.GlidePauseOnScrollListener;
//import com.chediandian.business.gallary.loader.GlideImageLoader;
//import com.igexin.sdk.PushManager;
//import com.xiaoka.android.common.image.ImageManager;
//import com.xiaoka.android.common.image.XKDefaultImageManager;
//import com.xiaoka.android.common.image.cache.local.name.MD5LocalName;
//import com.xiaoka.android.common.log.XKLog;
//import com.xiaoka.android.common.utils.XKToastUtil;
//
//import cn.finalteam.galleryfinal.CoreConfig;
//import cn.finalteam.galleryfinal.FunctionConfig;
//import cn.finalteam.galleryfinal.GalleryFinal;
//import cn.finalteam.galleryfinal.ThemeConfig;
//import xiaoka.chat.ChatApplication;
//
//
///**
// * Created by Administrator on 2015/6/10.
// */
//public class XKApplication extends ChatApplication {
//
//
//    private static BaseComponent sBaseComponent;
//
//    private static ApplicationComponent sApplicationComponent;
//
//    private static XKApplication instatnce;
//
//    public static boolean mEnableHttps = true;
//    public static FunctionConfig functionConfig;
//
//    public static BaseComponent getBaseComponent(){
//        return sBaseComponent;
//    }
//
//    public static ApplicationComponent getApplicationComponent(){
//        return sApplicationComponent;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        instatnce = this;
//        StethoSettings.initStetho(this);
//        sApplicationComponent = DaggerApplicationComponent
//                .builder()
//                .applicationModule(new ApplicationModule(this))
//                .build();
//        sBaseComponent = DaggerBaseComponent.builder()
//                .applicationComponent(sApplicationComponent)
//                .build();
//        //检测内存泄露
////        LeakCanary.install(this);
//        PushManager.getInstance().initialize(this);
//        initGalleryFinal();
//        initImageClient();
//        createResDir();
//        XKLog.init(SDCardConstant.DEBUG_LOG.getAbsolutePath());
//        CrashHandler.getInstance().init(this);
//        XKToastUtil.setApplicationContext(this);//由于有些模块功能引用了common库的功能，而common库使用了common库中的XKToashUtil
//
//    }
//
//    private void initGalleryFinal() {
//        //设置主题
////        ThemeConfig theme = ThemeConfig.CYAN
//        ThemeConfig theme = new ThemeConfig.Builder()
//                .build();
//        //配置功能
//        functionConfig = new FunctionConfig.Builder()
//                .setEnableCamera(true)
//                .setEnableEdit(false)
//                .setMutiSelectMaxSize(8)
//                .setEnableCrop(false)
//                .setEnableRotate(false)
//                .setCropSquare(true)
//                .setEnablePreview(true)
//                .build();
//        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
//                .setDebug(BuildConfig.DEBUG)
//                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
//                .build();
//        GalleryFinal.init(coreConfig);
//    }
//
//    public static XKApplication getInstance() {
//        return instatnce;
//    }
//
//    /**
//     * 初始化图片组件
//     */
//    private void initImageClient() {
//        ImageManager.Config config = ImageManager.Config.newInstance().setLocalDirs(SDCardConstant.IMG_CACHE.getAbsolutePath()).setLocalName(new MD5LocalName()).setDefaultRes(R.drawable.default_image);
//        XKDefaultImageManager.getInstance().init(this, config);
//    }
//
//    /**
//     * 退出App
//     */
//    public static void exitApp() {
//        mEnableHttps = true;
//        ActivityManager.getInstance().finishAll();
//        XKDefaultImageManager.getInstance().cancelRequest();
//    }
//
//    /**
//     * 创建SD资源目录
//     */
//    private void createResDir() {
//        if (!SDCardConstant.ROOT.exists()) {
//            SDCardConstant.ROOT.mkdir();
//        }
//        if (!SDCardConstant.IMG_CACHE.exists()) {
//            SDCardConstant.IMG_CACHE.mkdir();
//        }
//        if (!SDCardConstant.VOICE_CACHE.exists()) {
//            SDCardConstant.VOICE_CACHE.mkdir();
//        }
//        if (!SDCardConstant.IMG_USER_PIC_CACHE.exists()) {
//            SDCardConstant.IMG_USER_PIC_CACHE.mkdir();
//        }
//        if (!SDCardConstant.ADVERTISING_CACHE.exists()) {
//            SDCardConstant.ADVERTISING_CACHE.mkdir();
//        }
//        if (!SDCardConstant.TEMP_CACHE.exists()) {
//            SDCardConstant.TEMP_CACHE.mkdir();
//        }
//        if (!SDCardConstant.LOG.exists()) {
//            SDCardConstant.LOG.mkdir();
//        }
//
//        if (!SDCardConstant.DEBUG_LOG.exists()) {
//            SDCardConstant.DEBUG_LOG.mkdir();
//        }
//    }
//    protected void attachBaseContext(Context base) {
//
//        super.attachBaseContext(base);
//        MultiDex.install(base);
//    }
//
//}
