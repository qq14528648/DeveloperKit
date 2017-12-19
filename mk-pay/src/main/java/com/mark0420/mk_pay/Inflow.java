package com.mark0420.mk_pay;


/**
 * Created by mark on 17/12/18.
 */

public class Inflow {


    public static final String SPACE = "   ";

    public String icon;//图标
    public String title;//标题
    public String describe;//描述
    public boolean enable;//可用


    public Inflow(String icon, String title, String describe, boolean enable) {
        this.icon = icon;
        this.title = title;
        this.describe = describe;
        this.enable = enable;
    }

    public static String getInflowText(String title) {
//        String[] strings = title.split(Inflow.SPACE);
//
//        if (strings.length == 3) {
//
//            return strings[0] + strings[2];
//        } else {
//
//           return title.replace(Inflow.SPACE, "");
//
//        }

        return title;
    }
}
