package com.mark0420.mk_utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * Created by admin on 2016/07/07.
 * 邮箱：mark14528648@yahoo.co.jp
 * 自定义的toast
 */

public class ToastUtils {

    public synchronized static void makeText(Context c, @NonNull String text) {

        if (c == null) return;

        if (TextUtils.isEmpty(text)) text = c.getClass().getSimpleName() + ":空字符串错误";

        Toast.makeText(c, text,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 功能待开发
     */
    public synchronized static void textFunction(Context context) {
        makeText(context, context.getString(R.string.toast_function_error));
    }

    /**
     * 网络错误
     */
    public synchronized static void makeNetErrorText(Context context) {
        makeText(context, context.getString(R.string.toast_network_error));
    }

    /**
     * 服务器错误
     */
    public synchronized static void makeSeverErrorText(Context context) {
        makeText(context, context.getString(R.string.toast_sever_error));

    }

    /**
     * 数据错误
     */
    public synchronized static void makeDataErrorText(Context context) {
        makeText(context, context.getString(R.string.toast_data_error));
    }
}
