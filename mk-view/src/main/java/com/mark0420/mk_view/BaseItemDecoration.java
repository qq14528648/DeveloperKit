package com.mark0420.mk_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mark0420.mk_utils.DensityUtils;


/**
 * Created by mark on 16/7/7.
 * 邮箱：mark14528648@yahoo.co.jp
 *  RecycleView的分隔线
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {
    private static  final  float DEFAULT_INDENT=0;// 默认为0dip
    private int indentLeft;//分割线缩进,
    private int indentRight;//分割线缩进,

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 1;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
 private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线：高度为2px，颜色为透明
     *
     * @param context
     * @param orientation 列表方向
     */
    private BaseItemDecoration(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("参数错误,请输入正确的参数！");
        }
        this.indentLeft=  DensityUtils.dip2px(context,DEFAULT_INDENT);
        this.indentRight= this.indentLeft;
        mOrientation = orientation;

      final TypedArray a = context.obtainStyledAttributes(ATTRS);
      mDivider = a.getDrawable(0);
        //new ColorDrawable(context.getResources().getColor(android.R.color.transparent));
      a.recycle() ;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(android.R.color.transparent));
        mPaint.setStyle(Paint.Style.FILL);


    }

//    /**
//     * 自定义分割线
//     *
//     * @param context
//     * @param orientation 列表方向
//     * @param drawableId  分割线图片
//     */
//    private BaseItemDecoration(Context context, int orientation, int drawableId) {
//        this(context, orientation);
//        this.indentLeft=  DensityUtil.dip2px(context,DEFAULT_INDENT);
//        this.indentRight= this.indentLeft;
//        mDivider = ContextCompat.getDrawable(context, drawableId);
//        mDividerHeight = mDivider.getIntrinsicHeight();
//
//    }



    /**
     * 自定义分割线
     *高线间隔
     * @param context
     *  orientation   列表方向
     *   dividerHeight 分割线高度
     * dividerColor  分割线颜色
     * 所有分割线统一为背景色
     */
    public BaseItemDecoration(Context context) {
        this(context, LinearLayoutManager.HORIZONTAL);
        mDividerHeight = DensityUtils.dip2px(context,8);
        this.indentLeft=  DensityUtils.dip2px(context,DEFAULT_INDENT);
        this.indentRight= this.indentLeft;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getBackgroundColor(context));
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 自定义分割线
     *低线间隔
     * @param context
     * orientation   列表方向
     *   dividerHeight 分割线高度
     *  dividerColor  分割线颜色
     * @param indentLeft  缩进单位是dp
     * @param indentLRight  缩进单位是dp
     */

    public BaseItemDecoration(Context context, float indentLeft, float indentLRight ) {
        this(context, LinearLayoutManager.HORIZONTAL);
        //mDividerHeight = 1;
        this.indentLeft=  DensityUtils.dip2px(context,indentLeft);
        this.indentRight=  DensityUtils.dip2px(context,indentLRight);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(R.color.recycler_divider));
        mPaint.setStyle(Paint.Style.FILL);
    }

//    /**
//     * 自定义分割线
//     *
//     * @param context
//     * @param orientation   列表方向
//     * @param dividerHeight 分割线高度
//     * @param dividerColor  分割线颜色
//     * @param indent  缩进单位是dp
//     */
//    public BaseItemDecoration(Context context, int orientation, int dividerHeight, int dividerColor,int indent) {
//
//        this(context,orientation,dividerHeight,dividerColor);
//        this.indentLeft=  DensityUtil.dip2px(context,indent);
//        this.indentRight= this.indentLeft;
//
//
//    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left+indentLeft, top, right-indentRight, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left+indentLeft, top, right-indentRight, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
    public int getBackgroundColor(Context context) {
        TypedArray array =context.getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
        });
        int backgroundColor = array.getColor(0, 0xFF00FF);
        array.recycle();
        return backgroundColor;
    }
}