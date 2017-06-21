package com.mark0420.mk_utils;

import android.util.Log;


/**
 * Created by mark on 16/7/11.
 * 邮箱：mark14528648@yahoo.co.jp
 * 默认显示打印 LOG
 */
public class LogUtils {

    private static int LOGLEVEL = 0;// 默认显示打印 LOG

    private final static int LOGLEVEL_RELEASE = 6;//发布时LOGLEVEL＝6;
    private final static int LOGLEVEL_DEBUG = 0;//开发时LOGLEVEL＝0;

    private final static int VERBOSE = 5;
    private final static int DEBUG = 4;
    private final static int INFO = 3;
    private final static int WARN = 2;
    private final static int ERROR = 1;


    public static void setLOGLEVEL(int LOGLEVEL) {
        LogUtils.LOGLEVEL = LOGLEVEL;
    }

    public static void v(String tag, String msg) {
        if (LOGLEVEL < VERBOSE) {
            Log.v(tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if (LOGLEVEL < DEBUG) {
            Log.d(tag, msg);
        }

    }

    public static void i(String tag, String msg) {
        if (LOGLEVEL < INFO) {
            Log.i(tag, msg);
        }
    }


    public static void w(String tag, String msg) {
        if (LOGLEVEL < WARN) {
            Log.w(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (LOGLEVEL < ERROR) {
            Log.e(tag, msg);
        }
    }
}
