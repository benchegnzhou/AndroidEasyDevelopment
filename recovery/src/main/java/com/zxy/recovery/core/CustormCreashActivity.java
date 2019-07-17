package com.zxy.recovery.core;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zxy.recovery.R;
import com.zxy.recovery.tools.RecoverySharedPrefsUtil;
import com.zxy.recovery.tools.RecoveryUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by benchengzhou on 2019/4/2  10:20 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 自定义崩溃界面
 * 类    名： CustromCreashActivity
 * 备    注：
 */

public class CustormCreashActivity extends AppCompatActivity {


    public static final String RECOVERY_MODE_ACTIVE = "recovery_mode_active";

    private static final String DEFAULT_CRASH_FILE_DIR_NAME = "recovery_crash";


    /**
     * 回复界面数据
     */
    public static final String MODE_RECOVERY_GENERAL = "mode_recovery_general";
    /**
     * 尝试重启应用
     */
    public static final String MODE_RECOVERY_RESTRAT = "mode_recovery_restart";

    /**
     * 清除缓存并重启应用
     */
    public static final String MODE_RECOVERY_CLEAR = "mode_recovery_clear";

    private RecoveryStore.ExceptionData mExceptionData;

    private Toolbar mToolbar;

    private String mStackTrace;

    private String mCause;

    private View mMainLayout;

    @SuppressLint("HandlerLeak")
    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setupEvent(MODE_RECOVERY_RESTRAT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custrom_creash);
        setupToolbar();
        initView();
        initData();
        handler.sendEmptyMessageDelayed(200, 500);
//        setupEvent(MODE_RECOVERY_RESTRAT);
    }

    private void setupToolbar() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//        mToolbar.setTitle(RecoveryUtil.getAppName(this));
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void initView() {
        mMainLayout = findViewById(R.id.creash_layout);

    }

    private void initData() {

        mExceptionData = getExceptionData();
        mCause = getCause();
        mStackTrace = getStackTrace();
    }

    private void setupEvent(String opertion) {

        switch (opertion) {
            case MODE_RECOVERY_GENERAL:
                boolean restart = RecoverySharedPrefsUtil.shouldRestartApp();
                if (restart) {
                    RecoverySharedPrefsUtil.clear();
                    restart();
                    return;
                }
                if (isRecoverStack()) {
                    recoverActivityStack();
                } else {
                    recoverTopActivity();
                }
                break;
            case MODE_RECOVERY_RESTRAT:
                boolean restart2 = RecoverySharedPrefsUtil.shouldRestartApp();
                if (restart2) {
                    RecoverySharedPrefsUtil.clear();
                }
                restart();
                break;
            case MODE_RECOVERY_CLEAR:
                RecoveryUtil.clearApplicationData();
                restart();
                break;
            default:
        }

    }

    private boolean isDebugMode() {
        return getIntent().getBooleanExtra(RecoveryStore.IS_DEBUG, false);
    }

    private RecoveryStore.ExceptionData getExceptionData() {
        return getIntent().getParcelableExtra(RecoveryStore.EXCEPTION_DATA);
    }

    private String getCause() {
        return getIntent().getStringExtra(RecoveryStore.EXCEPTION_CAUSE);
    }

    private String getStackTrace() {
        return getIntent().getStringExtra(RecoveryStore.STACK_TRACE);
    }

    private void restart() {
        Intent launchIntent = getApplication().getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launchIntent);
            overridePendingTransition(0, 0);
        }
        finish();
    }

    private void recoverTopActivity() {
        Intent intent = getRecoveryIntent();
        if (intent != null && RecoveryUtil.isIntentAvailable(this, intent)) {
            intent.setExtrasClassLoader(getClassLoader());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(RECOVERY_MODE_ACTIVE, true);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
            return;
        }
        restart();
    }

    private boolean isRecoverStack() {
        boolean hasRecoverStack = getIntent().hasExtra(RecoveryStore.RECOVERY_STACK);
        return !hasRecoverStack || getIntent().getBooleanExtra(RecoveryStore.RECOVERY_STACK, true);
    }

    private void recoverActivityStack() {
        ArrayList<Intent> intents = getRecoveryIntents();
        if (intents != null && !intents.isEmpty()) {
            ArrayList<Intent> availableIntents = new ArrayList<>();
            for (Intent tmp : intents) {
                if (tmp != null && RecoveryUtil.isIntentAvailable(this, tmp)) {
                    tmp.setExtrasClassLoader(getClassLoader());
                    availableIntents.add(tmp);
                }
            }
            if (!availableIntents.isEmpty()) {
                availableIntents.get(0).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                availableIntents.get(availableIntents.size() - 1).putExtra(RECOVERY_MODE_ACTIVE, true);
                startActivities(availableIntents.toArray(new Intent[availableIntents.size()]));
                overridePendingTransition(0, 0);
                finish();
                return;
            }
        }
        restart();
    }

    private Intent getRecoveryIntent() {
        boolean hasRecoverIntent = getIntent().hasExtra(RecoveryStore.RECOVERY_INTENT);
        if (!hasRecoverIntent) {
            return null;
        }
        return getIntent().getParcelableExtra(RecoveryStore.RECOVERY_INTENT);
    }

    private ArrayList<Intent> getRecoveryIntents() {
        boolean hasRecoveryIntents = getIntent().hasExtra(RecoveryStore.RECOVERY_INTENTS);
        if (!hasRecoveryIntents) {
            return null;
        }
        return getIntent().getParcelableArrayListExtra(RecoveryStore.RECOVERY_INTENTS);
    }

    private boolean saveCrashData() {
        String date = RecoveryUtil.getDateFormat().format(new Date(System.currentTimeMillis()));
        File dir = new File(getExternalFilesDir(null) + File.separator + DEFAULT_CRASH_FILE_DIR_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, String.valueOf(date) + ".txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write("\nException:\n" + (mExceptionData == null ? null : mExceptionData.toString()) + "\n\n");
            writer.write("Cause:\n" + mCause + "\n\n");
            writer.write("StackTrace:\n" + mStackTrace + "\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        killProcess();
    }

}
