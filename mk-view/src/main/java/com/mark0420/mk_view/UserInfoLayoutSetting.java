package com.mark0420.mk_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/**
 * copy from origin  https://github.com/florent37/DiagonalLayout.git (fetch)
 * Created by wujianxing on 17/1/9.
 * wujianxing
 * 490187140@qq.com
 * 酱紫好么？
 */

public class UserInfoLayoutSetting {
    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int BOTTOM = 4;
    public final static int TOP = 8;
    private float angle = 15;
    private boolean handleMargins;
    private boolean isRight = false;
    private boolean isTop = false;
    private float elevation;

    UserInfoLayoutSetting(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DiagonalLayout, 0, 0);
        angle = styledAttributes.getInt(R.styleable.DiagonalLayout_diagonal_angle, 0);

        int gravity = styledAttributes.getInt(R.styleable.DiagonalLayout_diagonal_gravity, 0);
        isRight = (gravity & RIGHT) == RIGHT;
        isTop = (gravity & TOP) == TOP;
        handleMargins = styledAttributes.getBoolean(R.styleable.DiagonalLayout_diagonal_handleMargins, false);

        styledAttributes.recycle();
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public boolean isGravityLeft() {
        return !isRight;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isHandleMargins() {
        return handleMargins;
    }

    public void setHandleMargins(boolean handleMargins) {
        this.handleMargins = handleMargins;
    }

    public boolean isBottom(){
        return !isTop;
    }
}
