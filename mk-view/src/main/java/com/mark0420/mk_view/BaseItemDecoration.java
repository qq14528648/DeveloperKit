package com.mark0420.mk_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mark on 16/7/7.
 * 邮箱：mark14528648@yahoo.co.jp
 * RecycleView的分隔线
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {

    public static final int DEFAULT_DIVIDER_HEIGHT = 1;// 默认为1

    private ItemDecorationIndent itemDecorationIndent;

    private List<Integer> positions;

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL


    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public BaseItemDecoration(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
        mDivider = ContextCompat.getDrawable(context, R.drawable.recycler_divider);

    }


    public BaseItemDecoration(Context context, int dividerHeight, int dividerColor, ItemDecorationIndent itemDecorationIndent) {
        this(context, LinearLayoutManager.HORIZONTAL);
        mDividerHeight = dividerHeight;
        this.itemDecorationIndent = itemDecorationIndent;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);//Color.parseColor("#87CEFA")
        mPaint.setStyle(Paint.Style.FILL);

        if (itemDecorationIndent.positions != null) {

            positions = new ArrayList<>();

            for (int i = 0; i < itemDecorationIndent.positions.length; i++) {

                int[] pos = itemDecorationIndent.positions[i];

                int min = pos[0];
                int max = pos[1];
                for (int j = min; j <= max; j++) {//<=包含最后坐标，<则不包含

                    positions.add(j);
                }

            }

        }

    }


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

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();

        int visibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        /////////////////////////////////////////////绘制第0个item的线///////////////////////////////////////////////////////////////////
//        {
//            if (childSize != 0) {
//                final View child = parent.getChildAt(0);
//                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
//                final int top = child.getTop() + layoutParams.topMargin;
//                final int bottom = top + mDividerHeight;
//
//                int indentLeft = 0;//分割线缩进,
//                int indentRight = 0;//分割线缩进,
//
//                if (itemDecorationIndent != null) {
//
//                    if (positions != null) {
//
//                        if (positions.contains(0 + visibleItemPosition)) {
//                            indentLeft = itemDecorationIndent.left;
//                            indentRight = itemDecorationIndent.right;
//                        }
//
//                    } else {
//
//                        indentLeft = itemDecorationIndent.left;
//                        indentRight = itemDecorationIndent.right;
//                    }
//
//
//                }
//
//                if (mDivider != null) {
//
//                    mDivider.setBounds(left + indentLeft, top, right - indentRight, bottom);
//
//                    mDivider.draw(canvas);
//                }
//                if (mPaint != null) {
//                    canvas.drawRect(left + indentLeft, top, right - indentRight, bottom, mPaint);
//                }
//
//            }
//        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < childSize; i++) {


            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;

            int indentLeft = 0;//分割线缩进,
            int indentRight = 0;//分割线缩进,

            if (itemDecorationIndent != null) {

                if (positions!= null) {

                   if (positions.contains(i+visibleItemPosition)){
                       indentLeft = itemDecorationIndent.left;
                       indentRight = itemDecorationIndent.right;

                   }


                } else {

                    indentLeft = itemDecorationIndent.left;
                    indentRight = itemDecorationIndent.right;
                }


            }

            if (mDivider != null) {

                mDivider.setBounds(left + indentLeft, top, right - indentRight, bottom);

                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left + indentLeft, top, right - indentRight, bottom, mPaint);
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


}