package com.mark0420.mk_pay;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jungly.gridpasswordview.GridPasswordView;

import java.util.List;


/**
 * Created by mark on 16/9/29.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 * 支付界面
 */
public class PaymentBuilder {
    /**
     * 事件接口
     */
    private final static int PASSWORD_LENGTH = 6;

    public interface ICallBack {

        void close();

        void forgetPassword();

        void inputFinish(String pwd, int inflowPosition);
    }

    private Context mContext;

    private View mContentView;
    private PopupWindow mPopupWindow;
    private GridView mGridView;
    private ICallBack mICallBack;
    private GridAdapter mGridAdapter;

    private TextView mCloseTextView;
    private TextView mMoneyTextView;
    private GridPasswordView mPswView;
    private TextView mForgetPwdTextView;
    private TextView mFlowsTextView;

    private LinearLayout mInflowLinearLayout;
    private ImageView mIconImageView;
    private TextView mInflowTextView;

    private int mInflowPosition = -1;//金额流入方式的位置（多种流入方式时，输入完密码时返回。，否则为-1）

    private List<Inflow> mInflows;//金额流入方式集合（多种流入方式时，有值，否则为null）

    private Handler mHandler = new Handler();

    private boolean mPrimaryPopupWindowHide = false;

    private  boolean mSecondaryPopupWindowHide = true;

    /**
     * @param context
     * @param inflows 金额流入方式集合,没有则直接传null
     */

    public PaymentBuilder(Context context, List<Inflow> inflows) {

        mInflows = inflows;

        init(context);
    }

    private void init(Context context) {

        mContext = context;
        mContentView = LayoutInflater.from(mContext).inflate(
                R.layout.password_layout, null);// 定义后退弹出框

        mGridView = (GridView) mContentView.findViewById(R.id.gridView);// 泡泡窗口的布局
        mGridAdapter = new GridAdapter(mContext);
        mGridView.setAdapter(mGridAdapter);
        mPopupWindow = new PopupWindow(mContentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,true);

        mCloseTextView = (TextView) mContentView.findViewById(R.id.closeTextView);
        mFlowsTextView = (TextView) mContentView.findViewById(R.id.flowsTextView);
        mMoneyTextView = (TextView) mContentView.findViewById(R.id.moneyTextView);
        mPswView = (GridPasswordView) mContentView.findViewById(R.id.pswView);

        mInflowLinearLayout = (LinearLayout) mContentView.findViewById(R.id.inflowLinearLayout);
        mIconImageView = (ImageView) mContentView.findViewById(R.id.iconImageView);
        mInflowTextView = (TextView) mContentView.findViewById(R.id.inflowTextView);

        mPswView.setClickable(false);
        mPswView.setFocusable(false);
        mPswView.setFocusableInTouchMode(false);
        mPswView.clearFocus();
        mForgetPwdTextView = (TextView) mContentView.findViewById(R.id.forgetPwdTextView);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                if (!mPrimaryPopupWindowHide) {

                    if (mICallBack != null){
                        mICallBack.close();
                        release();
                    }

                }
            }
        });

        mCloseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                if (mICallBack != null)
//                    mICallBack.close();
            }
        });
        mForgetPwdTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mICallBack != null)
                    mICallBack.forgetPassword();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 9 || position == 10) {
                    if (mPswView.getPassWord().length() < PASSWORD_LENGTH) {
                        mPswView.setPassword(mPswView.getPassWord() + mGridAdapter.getItem(position).key);

                    }
                } else if (position == 9) {
                    mPswView.setPassword("");
                } else if (position == 11) {
                    if (mPswView.getPassWord().length() > 0) {
                        mPswView.setPassword(mPswView.getPassWord().substring(0, mPswView.getPassWord().length() - 1));
                    }
                }

                if (mPswView.getPassWord().length() == PASSWORD_LENGTH) {
                    mGridView.setEnabled(false);
                    mForgetPwdTextView.setEnabled(false);
                    mCloseTextView.setEnabled(false);
                    mPswView.setEnabled(false);
                    mContentView.setEnabled(false);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                            if (mICallBack != null)
                                mICallBack.inputFinish(mPswView.getPassWord(), mInflowPosition);
                        }
                    }, 100);


                }
            }
        });

        if (mInflows != null) {


            setInflowList();
        }
    }

    public PaymentBuilder show() {
        mPrimaryPopupWindowHide = false;
        mPopupWindow.showAtLocation(mContentView, Gravity.BOTTOM, 0, 0);
        return this;
    }


    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        release();

    }

    private void release() {
        mContext = null;
        mContentView = null;
        mPopupWindow = null;
        mGridView = null;
    }

    /**
     * 设置金额去向
     *
     * @param text
     */

    public PaymentBuilder setFlowsText(String text) {
        mFlowsTextView.setText(text);
        return this;
    }

    /**
     * 设置金额
     *
     * @param money
     */


    public PaymentBuilder setMoney(double money) {
        mMoneyTextView.setText("￥" + money);
        return this;
    }

    /**
     * 设置下面的流入信息
     *
     * @param inflows
     * @return
     */
    private int layout_height;
    private int layout_width;


    public void setInflowList() {

        layout_height = ListAdapter.dip2px(mContext, 24);
        layout_width = ListAdapter.dip2px(mContext, 24);

        mInflowLinearLayout.setVisibility(View.VISIBLE);


        mInflowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPrimaryPopupWindowHide = true;

                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();

                    setInflowPopupWindow();

                } else {
                    Toast.makeText(mContext, "弹窗错误!", Toast.LENGTH_SHORT);
                }
            }
        });

        for (int i = 0; i < mInflows.size(); i++) {

            if (mInflows.get(i).enable)
                mInflowPosition = i;
            setInflowText(mInflows.get(i).icon, Inflow.getInflowText(mInflows.get(mInflowPosition).title));
            break;
        }

    }


    public void setInflowText(String icon, String title) {

        Glide.with(mContext)
                .load(icon)
                .override(layout_width, layout_height)
                .into(mIconImageView);

        mInflowTextView.setText(title);
    }


    public void setInflowPopupWindow() {


        View v = LayoutInflater.from(mContext).inflate(
                R.layout.inflow_layout, null);// 定义后退弹出框
        final PopupWindow popupWindow = new PopupWindow(v,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,true);


        TextView returnTextView = (TextView) v.findViewById(R.id.returnTextView);

        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mSecondaryPopupWindowHide=true;

                if (mPopupWindow == null || mContentView == null || mGridView == null) {

                    init(mContext);
                }

//                setInflowText(mInflows.get(mInflowPosition).icon, Inflow.getInflowText(mInflows.get(mInflowPosition).title));

                show();

                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

            }
        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                if (!mSecondaryPopupWindowHide){

                    if (mICallBack != null){
                        mICallBack.close();

                        release();

                    }

                }

            }
        });

        ListView listView = (ListView) v.findViewById(R.id.listView);

        listView.setAdapter(new ListAdapter(mContext, mInflows));

        mSecondaryPopupWindowHide=false;

        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!mInflows.get(i).enable) {
                    return;
                }


                mSecondaryPopupWindowHide=true;

                mInflowPosition = i;

                if (mPopupWindow == null || mContentView == null || mGridView == null) {

                    init(mContext);
                }


                setInflowText(mInflows.get(mInflowPosition).icon, Inflow.getInflowText(mInflows.get(mInflowPosition).title));

                show();


                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

            }
        });

    }


    /**
     * 设置忘记密码事件
     *
     * @param iCallBack
     */
    public PaymentBuilder setCallBack(ICallBack iCallBack) {
        mICallBack = iCallBack;
        return this;

    }

}
