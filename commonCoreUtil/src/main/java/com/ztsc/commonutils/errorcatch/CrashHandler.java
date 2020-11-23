package com.ztsc.commonutils.errorcatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.ztsc.commonutils.CommonUtil;
import com.ztsc.commonutils.apputil.ApkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.lang.reflect.Field;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by benchengzhou on 2019/4/1  10:11 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 收集未保存的错误日志并且将未保存的错误日志保存本地，等待用户连接wifi将日志上传云服务
 * 类    名： CrashHandler
 * 备    注：
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {


    private static CrashHandler sInstance = null;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    // 保存手机信息和异常信息
    private Map<String, String> mMessage = new HashMap<>();

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    synchronized (CrashHandler.class) {
                        sInstance = new CrashHandler();
                    }
                }
            }
        }
        return sInstance;
    }

    private CrashHandler() {
    }

    /**
     * 初始化默认异常捕获
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        // 获取默认异常处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将此类设为默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e)) {
            // 未经过人为处理,则调用系统默认处理异常,弹出系统强制关闭的对话框
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            }
        } else {
            // 已经人为处理,系统自己退出


            try {
                new Thread() {
                    @Override
                    public void run() {

                        Intent intent = new Intent();
                        intent.setAction("com.ztsc.house.WelcomeActivity"); //添加一些特性，具体可以查看Intent文档，相关属性的介绍
                        intent.addCategory("WelcomeActivity");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent restartIntent = PendingIntent.getActivity(
                                mContext, 3010, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        //退出程序
                        //参考 https://www.jianshu.com/p/b39f9e79ac4f
                        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            mgr.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime(), restartIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            //4.4之后，6.0之前， SDK API >= 19, SDK API < 23
                            mgr.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime(), restartIntent);
                        } else {
                            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                                    restartIntent); // 1秒钟后重启应用
                        }
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }.start();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

//


//            System.exit(1);
        }

    }

    /**
     * 是否人为捕获异常
     *
     * @param e Throwable
     * @return true:已处理 false:未处理
     */
    private boolean handleException(Throwable e) {
        if (e == null) {// 异常是否为空
            return false;
        }
        new Thread() {// 在主线程中弹出提示
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "别紧张，我还是可以抢救一下的！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        collectErrorMessages();
        saveErrorMessages(e);
        return false;
    }

    /**
     * 1.收集错误信息
     */
    private void collectErrorMessages() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = TextUtils.isEmpty(pi.versionName) ? "null" : pi.versionName;
                String versionCode = "" + pi.versionCode;
                mMessage.put("versionName", versionName);
                mMessage.put("versionCode", versionCode);
            }
            // 通过反射拿到错误信息
            Field[] fields = Build.class.getFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        mMessage.put(field.getName(), field.get(null).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.保存错误信息
     *
     * @param e Throwable
     */
    private void saveErrorMessages(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mMessage.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        Throwable cause = e.getCause();
        // 循环取出Cause
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = e.getCause();
        }
        pw.close();
        String result = writer.toString();
        sb.append(result);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
        String fileName = "crash-" + time + "-" + System.currentTimeMillis() + ".log";

        String packageName = ApkUtils.getPackageName(CommonUtil.getInstance().getApplicationContext());

        // 有无SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + "/" + packageName + "/crash/";
            if (!TextUtils.isEmpty(CommonUtil.getInstance().getConfig().localLogDir)) {
                path = CommonUtil.getInstance().getConfig().localLogDir + "/";
            }
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

}
