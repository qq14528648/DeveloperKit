package com.mark0420.mk_pay;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by mark on 16/10/13.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 */


public class ListAdapter extends BaseAdapter {

    private List<Inflow> inflows ;

    private Context context;

    private int layout_height;
    private int layout_width;


    public ListAdapter(Context context, List<Inflow> inflows) {
        this.context = context;
        layout_height = dip2px(context, 32);
        layout_width = dip2px(context, 32);
        this.inflows=inflows;
    }


    @Override
    public int getCount() {
        return inflows.size();
    }


    @Override
    public Inflow getItem(int position) {
        return inflows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.inflow_layout_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mLayout = (LinearLayout) convertView.findViewById(R.id.layout);
            viewHolder.mIconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            viewHolder.mTitleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.mTipTextView = (TextView) convertView.findViewById(R.id.tipTextView);
            viewHolder. mGuidingTextView = (TextView) convertView.findViewById(R.id.guidingTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(viewHolder.mIconImageView.getContext().getApplicationContext())
                .load(inflows.get(position).icon)
                .override(layout_width, layout_height)
                .into(viewHolder.mIconImageView);
        viewHolder.mTitleTextView.setText(inflows.get(position).title);

        if (TextUtils.isEmpty(inflows.get(position).describe)){

            viewHolder.mTipTextView.setVisibility(View.GONE);
        }else{
            viewHolder.mTipTextView.setVisibility(View.VISIBLE);
            viewHolder.mTipTextView.setText(inflows.get(position).describe);

        }
        if (inflows.get(position).enable) {
            viewHolder.mTitleTextView.setTextColor(Color.parseColor("#DE000000"));
            viewHolder.mGuidingTextView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTitleTextView.setTextColor(Color.parseColor("#90000000"));
            viewHolder.mGuidingTextView.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public LinearLayout mLayout;
        public ImageView mIconImageView;
        public TextView mTitleTextView;
        public TextView mTipTextView;
        private TextView mGuidingTextView;


    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}