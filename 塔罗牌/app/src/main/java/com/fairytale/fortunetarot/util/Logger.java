package com.fairytale.fortunetarot.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Created by lizhen on 16/4/19. 日志打印
 */
public class Logger {

    private static boolean isDebug = false;

    public static void e(String tag, String log) {
        if (isDebug) {
            Log.e(tag,log);
        }
    }

    public static void i(String tag, String log) {
        if (isDebug) {
            Log.i(tag, log);
        }
    }

    public static void v(String tag, String log) {
        if (isDebug) {
            Log.v(tag, log);
        }
    }

    public static void d(String tag, String log) {
        if (isDebug) {
            Log.d(tag, log);
        }
    }

    public static void setIsDebug(Context context) {
        if (context != null) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                isDebug = ((applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
            }
        }
    }
}
