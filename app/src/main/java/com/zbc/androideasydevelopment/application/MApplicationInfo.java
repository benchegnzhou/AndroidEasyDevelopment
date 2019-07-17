package com.zbc.androideasydevelopment.application;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.tencent.bugly.crashreport.CrashReport;
import com.youth.banner.loader.ImageLoader;
import com.zbc.androideasydevelopment.BuildConfig;
import com.zbc.androideasydevelopment.R;
import com.zbc.androideasydevelopment.helper.TrustAllCerts;
import com.zbc.androideasydevelopment.ui.activity.WelcomeActivity;
import com.ztsc.commonutils.CommonUtil;
import com.ztsc.commonutils.Util;
import com.ztsc.commonutils.devicemessage.DeviceMessageUtils;
import com.ztsc.commonutils.utilconfig.Config;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import java.io.File;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 全局静态方法和变量的初始化类，用于初始全局的上下文，volley 的实体对象呢
 * 包过多的解决办法： 在这里  http://blog.csdn.net/gulihui890411/article/details/48551551
 */
public class MApplicationInfo {
    public static Application sApplication;
    public static Context sAppContext;
    public static OkHttpClient sOkHttpClient;


    //屏幕的宽高信息
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static float density = 0;


    //    环信
/*    private UserDao userDao;
    private String username = "";
    private Map<String, EaseUser> contactList;
    private DaoSession daoSession;
    private OSSClient oss;*/


    private MApplicationInfo() {

    }

    private static class getInstance2 {
        private static MApplicationInfo info = new MApplicationInfo();
    }


    /**
     * 创建全局单例的对象
     *
     * @return
     */
    public static MApplicationInfo getInstance() {
        return getInstance2.info;
    }

    public void init(Application application, Context context) {

        CommonUtil.getInstance().init(application, new Config()
                .setLogOpen(true)
                .setLogTag("ZBC_DEMO")
                .setToastOpen(true)
                .setACacheFname("zbc_demo"));

        //获取全局上下文
        sAppContext = context;
        sApplication = application;
        initJPush();
    }

    public void otherInit() {
//        initTypeface();

//        initEasenob();
//        initImageLoader();
        SDKINIT();
        initLeakCanary();
        initBugly();
        initFilePrivider();
        initPicasso();
        initDreenDao();
//        aliOSSInit();
        // initCrash();
    }


    /**
     * 崩溃初始化
     */
    private void initCrash() {
        Recovery.getInstance()
                .debug(true)    //页面显示日志
                .recoverInBackground(true)
                .recoverStack(true)
                .mainPage(WelcomeActivity.class)
                .recoverEnabled(true)
                .callback(new MyCrashCallback())
                //设置启用沉默启动和沉默启动的方式
                .silent(true, Recovery.SilentMode.RESTART)
                .skip(WelcomeActivity.class)
                .init(sAppContext);

        MyCrashHandler.register();
    }

    /**
     * 阿里对象存储服务初始化
     */
    /*public void aliOSSInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //   demo 中使用这个     String endpoint = "http://img-cn-beijing.aliyuncs.com";
                String tokenHost = BuildConfig.HOST_SERVICE_OSSTOKEN;
                //   String tokenHost = "http://192.168.1.120:7080";
                OSSAuthCredentialsProvider credentialProvider = new OSSAuthCredentialsProvider(tokenHost);
                // OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ConstantValue.StsToken.AccessKeyId, ConstantValue.StsToken.SecretKeyId, ConstantValue.StsToken.SecurityToken);

                // 配置类如果不设置，会有默认配置。
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
                conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
                oss = new OSSClient(sAppContext, ConstantValue.AliOSSconfig.endpoint, credentialProvider, conf);

                OSSLog.enableLog();  //调用此方法即可开启日志
            }
        }).run();

    }*/


    /**
     * 获取Oss
     *
     * @return
     */
//    public OSSClient getOss() {
//        return oss;
//    }


    /**
     * greendao数据库初始化
     */
    private void initDreenDao() {
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(sAppContext, "zhengtu_public_app.db");
//        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
    }


    /**
     * 获取 DaoSession
     *
     * @return
     */
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }


    /**
     * 极光推送初始化
     */
    private void initJPush() {
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(sApplication);            // 初始化 JPush
    }

   /* private void initPicasso() {

//        Picasso.setSingletonInstance(new Picasso.Builder(sAppContext). downloader(new OkHttpDownloader(PicassoLoader2.getMUnsafeOkHttpClient())) .build());

       *//*  //第一种方式
      OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
        Picasso.Builder builder = new Picasso.Builder(sAppContext);
        builder.downloader( new ImageDownLoader(client));
*//*

     *//* Picasso.Builder picassoBuilder = new Picasso.Builder(sAppContext);

        // Picasso.Builder creates the Picasso object to do the actual requests
        // Picasso picasso = picassoBuilder.build();

        OkHttpClient client = new OkHttpClient();
        picassoBuilder.downloader(
                new ImageDownLoader(client)
        );*//*

     *//* OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
        Picasso.setSingletonInstance(new Picasso.Builder(this).
                downloader(new ImageDownLoader(client))
                .build());*//*

        OkHttpClient client = new OkHttpClient.Builder()
//                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
        Picasso.setSingletonInstance(new Picasso.Builder(this).
                downloader( new ImageDownLoader(client))
                .build());


    }
*/

    private void initPicasso() {

        //初始化okhttp
      /*  val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)
        val okHttpClient = OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);*/


        // Picasso.Builder creates the Picasso object to do the actual requests
        // Picasso picasso = picassoBuilder.build();


        //初始化picasso 可以加载https的图片 ,这个方法必须调用在with方法之前，不然会抛出异常
        /*try {
            Picasso.setSingletonInstance(new Picasso.Builder(sAppContext).downloader(new ImageDownLoader(getSOkHttpClient())).loggingEnabled(true)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }


    /**
     * 初始化7.0机器文件获取权限
     */
    private void initFilePrivider() {
        //在 7.0 版本以上的机器上面，从Android 7.0开始，一个应用提供自身文件给其它应用使用时，
        // 如果给出一个file://格式的URI的话，应用会抛出FileUriExposedException。这是由于谷歌认为目标app可能不具有文件权限，会造成潜在的问题。所以让这一行为快速失败。详见这里。这里讨论两种解决方式。
        //下面的解决办法是配置忽略这种安全检查
        //参考链接：http://www.jianshu.com/p/68a4e8132fcd
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }


    /**
     * 全局字体初始化
     */
   /* private void initTypeface() {


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        //.setDefaultFontPath("fonts/bigboldblack.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                        .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                        .build()
        );
    }*/

    /**
     * 初始化bugly
     */
    private void initBugly() {

        CrashReport.initCrashReport(sAppContext);
        Context context = sAppContext;
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = Util.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        //----------------需要动态设置渠道版本号和包名使用下面这句话--------------
        try {
            strategy.setAppChannel(Util.getChannel(sAppContext, ""));  //设置渠道
            strategy.setAppVersion(Util.getVersion(sAppContext));      //App的版本
            String deviceId = DeviceMessageUtils.getIMEI(sAppContext);
            strategy.setDeviceID(TextUtils.isEmpty(deviceId) ? "123456" : deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }


        strategy.setAppPackageName(packageName);  //App的包名
        //----------------需要动态设置渠道版本号和包名使用上面这句话--------------
        // 初始化Bugly
        CrashReport.initCrashReport(context, "75095c1153", true, strategy);

    }


    /**
     * 参考链接：  https://www.liaohuqiu.net/cn/posts/leak-canary-read-me/
     * 内存检测工具
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(sAppContext)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(sApplication);
        // Normal app init code...

    }
/*
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(sAppContext);
        ImageLoader.getInstance().init(configuration);

    }*/

    /**
     * 创建全局OkHttpClient对象
     * <p>
     * OkHttpClient 用于管理所有的请求，内部支持并发，
     * 所以我们不必每次请求都创建一个 OkHttpClient 对象，这是非常耗费资源的。接下来就是创建一个 Request 对象了
     *
     * @return
     */
    public OkHttpClient getSOkHttpClient() {
        //创建okhttp的请求对象 参考地址  http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html


        if (sOkHttpClient == null) {
            sOkHttpClient =
                    new OkHttpClient.Builder()
                            .readTimeout(20000, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(20000, TimeUnit.SECONDS)//设置写的超时时间
                            .connectTimeout(20000, TimeUnit.SECONDS)//设置连接超时时间
                            .sslSocketFactory(createSSLSocketFactory())    //添加信任所有证书
                            .hostnameVerifier(new HostnameVerifier() {     //信任规则全部信任
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            .cache(new Cache(new File(sAppContext.getExternalCacheDir(), "zbc_demo_cache"), 50 * 1024 * 1024))
                            .build();
        }
        return sOkHttpClient;
    }


    /**
     * 三方sdk初始化
     */
    private void SDKINIT() {


        //日志调试工具的初始化
//        SmartToolCore.getInstance().init(this);


        //无论推送是否开启都需要调用此方法   友盟推送的初始化
       /* PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override       //通过设备的唯一标示识别用户身份   device token
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e("myToken:----"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e("myToken获取失败："+s + "---"+s1);
            }
        });*/


    }


    /**
     * 设置初始化环信
     */
    /*private void initEasenob() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);

        options.setAutoLogin(false);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(sAppContext.getPackageName())) {
            Log.e("TAG", "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMClient.getInstance().init(sAppContext, options);
        EMClient.getInstance().setDebugMode(true);
        //注册环信监听
        EMMessageHelper.getInstance().registeEMMessager();
    }*/

   /* public void setCurrentUserName(String username) {
        this.username = username;
        Myinfo.getInstance(sApplication).setUserInfo(Constant.KEY_USERNAME, username);
    }

    public String getCurrentUserName() {
        if (TextUtils.isEmpty(username)) {
            username = Myinfo.getInstance(sApplication).getUserInfo(Constant.KEY_USERNAME);

        }
        return username;
    }*/

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) sApplication.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = sAppContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /*public Map<String, EaseUser> getContactList() {

        if (contactList == null) {
            contactList = userDao.getContactList();
        }
        return contactList;
    }

    public void setContactList(Map<String, EaseUser> contactList) {
        this.contactList = contactList;
        userDao.saveContactList(new ArrayList<EaseUser>(contactList.values()));

    }*/


    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            Log.e("zxy", "exceptionMessage:" + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            Log.e("zxy", "cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            Log.e("zxy", "exceptionClassName:" + exceptionType);
            Log.e("zxy", "throwClassName:" + throwClassName);
            Log.e("zxy", "throwMethodName:" + throwMethodName);
            Log.e("zxy", "throwLineNumber:" + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }


    /**
     * 测试环境https添加全部信任
     * okhttp的配置
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }


}

