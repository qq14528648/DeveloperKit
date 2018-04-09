
package com.mark0420.mk_pie;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mark0420.mk_pie.manager.PieManager;
import com.mark0420.mk_pie.render.PieChartRender;
import com.mark0420.mk_pie.utils.UIUtil;

/**
 * Created by 大灯泡 on 2017/11/7.
 * <p>
 * 好吃的甜甜圈？请问要什么口味呢
 */

public class AnimatedPieView extends View implements IPieView {
    protected final String TAG = this.getClass().getSimpleName();

    private AnimatedPieViewConfig mConfig;
    private PieChartRender mPieChartRender;
    private PieManager mPieManager;


    public AnimatedPieView(Context context) {
        this(context, null);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimatedPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPieManager = new PieManager(this);
        mPieChartRender = new PieChartRender(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(UIUtil.dip2px(getContext(), 300f), widthMeasureSpec),
                getSize(UIUtil.dip2px(getContext(), 300f), heightMeasureSpec));
    }

    private int getSize(int defaultSize, int measureSpec) {
        int result = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPieChartRender.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPieManager.setChartContentRect(getWidth(), getHeight(), getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    public AnimatedPieView applyConfig(AnimatedPieViewConfig config) {
        this.mConfig = config;
        return this;
    }

    public void start() {
        start(mConfig);
    }

    public void start(AnimatedPieViewConfig config) {
        applyConfig(config);
        if (mConfig == null) {
            throw new NullPointerException("config must not be null");
        }
        mPieChartRender.prepare();
    }

    //-----------------------------------------touch-----------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mPieChartRender.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public PieManager getManager() {
        return mPieManager;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public AnimatedPieViewConfig getConfig() {
        return mConfig;
    }

    @Override
    public View getPieView() {
        return this;
    }

    @Override
    public void onCallInvalidate() {
        if (isMainThread()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
