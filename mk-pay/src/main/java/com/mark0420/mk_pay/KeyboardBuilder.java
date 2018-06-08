package com.mark0420.mk_pay;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

/**
 * Created by mark on 16/9/29.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 * 键盘界面
 */
public class KeyboardBuilder {
    /**
     * 事件接口
     */
    private final  static  int PASSWORD_LENG=6;

    public interface IKeyboardBuilder {
        int input(int num);
    }

    private Context mContext;
    private View contentView;
    private PopupWindow popupWindow;
    private GridView gridView;

    private GridAdapter mGridAdapter;
    private  IKeyboardBuilder mIKeyboardBuilder;

    public KeyboardBuilder(Context context) {
        mContext = context;
        contentView = LayoutInflater.from(mContext).inflate(
                R.layout.password_layout_keyboard, null);// 定义后退弹出框

        gridView = (GridView) contentView.findViewById(R.id.gridView);// 泡泡窗口的布局
        mGridAdapter= new GridAdapter(mContext);
        gridView.setAdapter(mGridAdapter);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.animation);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position<9){
                    mIKeyboardBuilder.input(position+1);
                }else if(position==9){

                } else if(position==10){
                    mIKeyboardBuilder.input(0);
                }
                else if(position==11){
                    mIKeyboardBuilder.input(-1);
                }
            }
        });
        gridView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.keyboard_enter));

        gridView.setOnKeyListener(new View.OnKeyListener() {//按下android回退物理键 PopipWindow消失解决
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        return true;
                    }

                }
                return false;
            }
        });
    }

    public void show() {
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }


    private void dismiss() {
        Animation animation= AnimationUtils.loadAnimation(mContext, R.anim.keyboard_out);
        gridView.startAnimation( animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                release();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void release() {
        contentView = null;
        mContext = null;
        popupWindow = null;
        gridView = null;
    }

}
