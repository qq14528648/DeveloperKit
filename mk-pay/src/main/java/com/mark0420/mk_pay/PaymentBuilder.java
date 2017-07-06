package com.mark0420.mk_pay;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;


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
    private final  static  int PASSWORD_LENGTH=6;

    public interface IPaymentBuilder {

        void forgetPwd();

        void inputFinish(String pwd);
    }

    private Context mContext;
    private View contentView;
    private PopupWindow popupWindow;
    private GridView gridView;
    private IPaymentBuilder mPaymentBuilder;
    private GridAdapter mGridAdapter;

    private TextView mCloseTextView;
    private TextView mIllustrateTextView;
    private TextView mMoneyTextView;
    private GridPasswordView mPswView;
    private TextView mForgetPwdTextView;

    private Handler mHandler=new Handler();

    public PaymentBuilder(Context context) {
        mContext = context;
        contentView = LayoutInflater.from(mContext).inflate(
                R.layout.password_layout, null);// 定义后退弹出框

        gridView = (GridView) contentView.findViewById(R.id.gridView);// 泡泡窗口的布局
        mGridAdapter= new GridAdapter(mContext);
        gridView.setAdapter(mGridAdapter);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        mCloseTextView = (TextView) contentView.findViewById(R.id.closeTextView);
        mIllustrateTextView = (TextView) contentView.findViewById(R.id.illustrateTextView);
        mMoneyTextView = (TextView) contentView.findViewById(R.id.moneyTextView);
        mPswView = (GridPasswordView) contentView.findViewById(R.id.pswView);
        mForgetPwdTextView = (TextView) contentView.findViewById(R.id.forgetPwdTextView);
        mCloseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mPaymentBuilder != null)
                    mPaymentBuilder.inputFinish(null);
            }
        });
        mForgetPwdTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPaymentBuilder != null)
                    mPaymentBuilder.forgetPwd();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position<9||position==10){
                    if (mPswView.getPassWord().length()<PASSWORD_LENGTH){
                        mPswView.setPassword(mPswView.getPassWord()+mGridAdapter.getItem(position).key);

                    }
                }else if(position==9){
                    mPswView.setPassword("");
                }
                else if(position==11){
                    if (mPswView.getPassWord().length()>0){
                        mPswView.setPassword(mPswView.getPassWord().substring(0,mPswView.getPassWord().length()-1));
                    }
                }

                if (mPswView.getPassWord().length()==PASSWORD_LENGTH){
                    gridView.setEnabled(false);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                            if (mPaymentBuilder != null)
                                mPaymentBuilder.inputFinish(mPswView.getPassWord());
                        }
                    },100);


                }
            }
        });


    }

    public PaymentBuilder show() {
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        return this;
    }


    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        release();

    }

    private void release() {
        contentView = null;
        mContext = null;
        popupWindow = null;
        gridView = null;
    }
    /**
     * 设置金额去向
     *
     * @param text
     */

    public PaymentBuilder setMoneyWayText(String text) {
        mIllustrateTextView.setText(text);return this;
    }

    /**
     * 设置金额
     *
     * @param text
     */

    public PaymentBuilder setMoneyText(String text) {
        mMoneyTextView.setText(text);
        return this;
    }

    /**
     * 设置忘记密码事件
     *
     * @param paymentBuilder
     */
    public PaymentBuilder setPaymentBuilder(IPaymentBuilder paymentBuilder) {
        mPaymentBuilder = paymentBuilder;
        return this;

    }
}
