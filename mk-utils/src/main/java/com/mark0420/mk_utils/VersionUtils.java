package com.mark0420.mk_utils;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * Created by mark on 16/8/19.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 */

public class VersionUtils {
    /**
     * 获取软件版本号
     *
     * @param context
     * @return 版本号或-1
     */
    public static int versionCode(Context context) {
        if (context == null)
            return -1;

        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            return info.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return 版本号或-1
     */
    public static String versionName(Context context) {
        if (context == null)
            return "-1";

        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;

        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

}
