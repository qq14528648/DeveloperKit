package com.mark0420.mk_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by admin on 2016/07/16.
 * 邮箱：mark14528648@yahoo.co.jp
 * 支持空视图及点击事件的RecyclerView,目前点击事件使用Adapter
 */

public class SupportRecyclerView extends RecyclerView {
    private View emptyView;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {

            Adapter adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {

                    emptyView.setVisibility(View.VISIBLE);
                    SupportRecyclerView.this.setVisibility(View.GONE);
                } else {

                    emptyView.setVisibility(View.GONE);
                    SupportRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public SupportRecyclerView(Context context) {
        super(context);
    }

    public SupportRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SupportRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null && emptyObserver != null) {
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        /**
         * 如果emptyView在布局中已经设置为GONE,此处可省略
         */
        emptyObserver.onChanged();
    }

    /**
     * set view when no content item
     * 同时支持切换emptyView
     *
     * @param emptyView visiable view when items is empty
     *
     *
     */
    public void setEmptyView(View emptyView) {
        if (this.emptyView != null)
            this.emptyView.setVisibility(View.GONE);
        this.emptyView = emptyView;
        emptyObserver.onChanged();
    }


    public View getEmptyView() {
        return emptyView;
    }

    /**
     * @param onItemClickListener clicklistener for recyclerview item
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * @param onItemLongClickListener longclicklistener for recyclerview item
     */
    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    /**
     * ViewHolder that support click and longclick event
     */
    public static class SupportViewHolder extends RecyclerView.ViewHolder {

        public SupportViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static abstract class SupportAdapter extends RecyclerView.Adapter<SupportViewHolder>
            implements View.OnClickListener, View.OnLongClickListener {

        private OnRecyclerViewItemClickListener onClickListener;
        private OnRecyclerViewItemLongClickListener onLongClickListener;

        protected Context mContext;

        private int itemPos = -1;

        public void setOnItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
            this.onClickListener = itemClickListener;
        }

        public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener itemLongClickListener) {
            this.onLongClickListener = itemLongClickListener;
        }

        public SupportAdapter(Context context) {
            this.mContext = context;
        }


        public SupportAdapter(Context context, OnRecyclerViewItemClickListener itemClickListener,
                              OnRecyclerViewItemLongClickListener itemLongClickListener) {
            this.mContext = context;
            this.onClickListener = itemClickListener;
            this.onLongClickListener = itemLongClickListener;
        }

        @Override
        public void onBindViewHolder(SupportViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
            itemPos = position;
            holder.itemView.setOnClickListener(new RecyclerItemClickListener(position, this.onClickListener));
            holder.itemView.setOnLongClickListener(new RecyclerItemLongClickListener(position, this.onLongClickListener));
        }


        @Override
        public void onClick(View v) {
            if (this.onClickListener != null) {
                this.onClickListener.onRecyclerViewItemClick(v, itemPos);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (this.onLongClickListener != null) {
                this.onLongClickListener.onRecyclerViewItemLongClick(v, itemPos);
            }
            return false;
        }
    }


    /**
     * on item click listener
     */
    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(View view, int pos);
    }

    /**
     * on item long click listener
     */
    public interface OnRecyclerViewItemLongClickListener {
        boolean onRecyclerViewItemLongClick(View view, int pos);
    }

    public static class RecyclerItemClickListener implements View.OnClickListener {

        private int mPos;
        private OnRecyclerViewItemClickListener mClickListener;

        public RecyclerItemClickListener(int position, OnRecyclerViewItemClickListener clickListener) {
            this.mPos = position;
            this.mClickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            if (this.mClickListener != null) {
                this.mClickListener.onRecyclerViewItemClick(v, this.mPos);
            }
        }
    }


    public static class RecyclerItemLongClickListener implements View.OnLongClickListener {
        private int mPos;
        private OnRecyclerViewItemLongClickListener mLongClickListener;

        public RecyclerItemLongClickListener(int position, OnRecyclerViewItemLongClickListener longClickListener) {
            this.mPos = position;
            this.mLongClickListener = longClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            if (this.mLongClickListener != null) {
                return this.mLongClickListener.onRecyclerViewItemLongClick(v, mPos);
            }
            return false;
        }
    }

}