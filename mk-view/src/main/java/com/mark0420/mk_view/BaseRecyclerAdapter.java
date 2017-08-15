package com.mark0420.mk_view;


import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/07/08
 * 邮箱：mark14528648@yahoo.co.jp
 * 适配器基类{@link  }
 * additem，additems 比较特殊，会 clear且notifyDataSetChanged
 * 其它 insertItems
 * removeItems
 * replaceItems， 。更改复数数据时（即 s），会notifyDataSetChanged，
 * 更改单个数据 时，不会会notifyDataSetChanged
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected List<T> beans = new ArrayList();


    /**
     * 添加数据集, 自动更新数据
     *
     * @param items
     */
    public BaseRecyclerAdapter addItems(List<T> items) {
        beans.clear();
        beans.addAll(items);
        notifyDataSetChanged();
        return this;
    }

//    public BaseRecyclerAdapter addItems2(List<T> items) {
//        beans.clear();
//        beans.addAll(items);
//        beans.addAll(items);
//        notifyDataSetChanged();
//        return this;
//    }

    /**
     * 添加单条数据 , 自动更新数据,
     *
     * @param item
     */

    public BaseRecyclerAdapter addItem(T item) {
        beans.clear();
        beans.add(item);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 插入数据集 自动更新数据
     *
     * @param items
     */
    public BaseRecyclerAdapter insertItems(int p, List<T> items) {

        beans.addAll(p, items);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 插入单条数据 ,
     *
     * @param item
     */

    public BaseRecyclerAdapter insertItem(int p, T item) {
        beans.add(p, item);
        return this;
    }


    /**
     * 移除数据集,自动更新数据
     */
    public BaseRecyclerAdapter removeItems(List<T> items) {
        beans.removeAll(items);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 移除数据,
     */
    public BaseRecyclerAdapter removeItem(int p) {
        beans.remove(p);
        return this;
    }


    public BaseRecyclerAdapter replaceItems(List<T> items) {
        beans.removeAll(items);
        beans.addAll(items);
        notifyDataSetChanged();
        return this;
    }


    public BaseRecyclerAdapter replaceItem(int p, T bean) {
        beans.remove(p);
        beans.add(p, bean);
//        notifyItemInserted(p);
//        notifyItemChanged(p);
        return this;
    }

    /**
     * 清空数据源
     */
    public void clear() {
        this.beans.clear();
    }


    public T getItem(int p) {
        return beans.get(p);
    }

    /**
     * 数据集
     */

    public List<T> getItems() {
        return beans;
    }


    /**
     * 数据集大小
     * @return
     */

    public int getItemsSize() {
        return beans.size();
    }

    /**
     * 外部尽量少用此方法，含有 footer 时，需要自己计算此值，推荐使用上面的方法
     * @return
     */
    @Override
    public int getItemCount() {
        return beans.size();
    }


    public interface OnRecyclerViewListener {
        void onItemClick(int position);
    }


    public  interface OnRecyclerViewLongListener {

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public OnRecyclerViewLongListener onRecyclerViewLongListener;

    public BaseRecyclerAdapter setOnRecyclerViewListener(OnRecyclerViewListener listener) {
        this.onRecyclerViewListener = listener;
        return this;
    }

    public BaseRecyclerAdapter setOnRecyclerViewLongListener(OnRecyclerViewLongListener listener) {
        onRecyclerViewLongListener = listener;
        return this;
    }
    /**
     * 需要子类重写,不能去除super
     */
    @Override
    @CallSuper
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseRecyclerHolder h = (BaseRecyclerHolder) holder;
        h.position = position;
    }

    /**
     * 可点击的需要子类去继承
     */
    public class BaseRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnLongClickListener {

        public int position;

        /**
         * 默认只支持单按，不支持长按
         * @param v
         */
        public BaseRecyclerHolder(View v) {
            this(v, true,false);
        }

        public BaseRecyclerHolder(View v, boolean onClick,boolean onLongClick) {
            super(v);
            if (onClick)
                itemView.setOnClickListener(this);
            if (onLongClick)
                itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (null != onRecyclerViewLongListener) {
                return onRecyclerViewLongListener.onItemLongClick(position);
            }
            return false;
        }
    }

//    private void setTypeFace(Context c, TextView v) {
//        AssetManager mgr = c.getAssets();//得到AssetManager
//        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto-Black.ttf");//根据路径得到Typeface
//        v.setTypeface(tf);//设置字体
//        v.getPaint().setFakeBoldText(true);
//    }


}
