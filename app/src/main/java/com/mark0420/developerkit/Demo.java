package com.mark0420.developerkit;

import android.content.Context;

import com.mark0420.mk_pay.PaymentBuilder;
import com.mark0420.mk_utils.APKUtils;
import com.mark0420.mk_view.SupportRecyclerView;

import java.io.File;

/**
 * Created by mark on 2017/6/21.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 */

public class Demo {
    public static void text(Context context, File apkFile) {

        APKUtils.install(context, apkFile);

    }
}