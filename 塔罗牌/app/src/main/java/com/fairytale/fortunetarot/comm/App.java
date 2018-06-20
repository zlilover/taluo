package com.fairytale.fortunetarot.comm;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.fairytale.fortunetarot.http.request.BaseConfigRequest;
import com.fairytale.fortunetarot.util.CardUtil;
import com.fairytale.fortunetarot.util.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lizhen on 2018/1/9.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance () {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setIsDebug(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        instance = this;
        if (getCurProcessName().equals(getPackageName())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CardUtil.getCardsInfoByType(App.this,"allnames.txt");
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CardUtil.getDivinationItemEntityFromAssets(App.this);
                }
            }).start();
//            initParams();
        }
    }

//    private void initParams() {
//        new BaseConfigRequest().getBaseConfig(this);
//    }

    private String getCurProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
