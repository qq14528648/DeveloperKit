package com.mark0420.mk_utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Created by mark on 16/8/19.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 */

public class APKUtils {

    public static void install(Context context, File apkFile)  throws  Exception{

        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities= context.getApplicationContext().getPackageName() + ".fileProvider";
            Uri uriForFile = FileProvider.getUriForFile(context, authorities, apkFile);

            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
        }else{
            var2.setDataAndType(Uri.fromFile(apkFile), getMIMEType(apkFile));
        }
        context.startActivity(var2);

//
//        try {
//            context.startActivity(var2);
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            ToastUtils.makeText(context, "没有找到打开此类文件的程序");
//        }
    }
    public static  String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

}
