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
 *
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected List<T> beans = new ArrayList();


    /**
     * 添加数据集,清空并自动更新数据
     *
     * @param items
     */
    public BaseRecyclerAdapter addItems(List<T> items) {
        beans.clear();
        beans.addAll(items);
        notifyDataSetChanged();
        return this;
    }
    /**
     * 添加单条数据 ,不清空且不自动更新数据,
     *
     * @param  item
     */

    public BaseRecyclerAdapter addItem(T item) {
        beans.clear();
        beans.add(item);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 插入数据集,清空并自动更新数据
     *
     * @param items
     */
    public BaseRecyclerAdapter insertItems(int p,List<T> items) {

        beans.addAll(p,items);
        notifyDataSetChanged();
        return this;
    }
    /**
     * 插入单条数据 ,不清空且不自动更新数据
     *
     * @param  item
     */

    public BaseRecyclerAdapter insertItem(int p,T item) {
        beans.add(p,item);
        return this;
    }


    /**
     * 移除数据集,自动更新数据
     */
    public BaseRecyclerAdapter removeItems(List<T> items) {
        this.beans.removeAll(items);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 移除数据,自动更新数据
     */
    public BaseRecyclerAdapter removeItem(int p) {
        this.beans.remove(p);
        return this;
    }



    public BaseRecyclerAdapter replaceItems(List<T> items) {
        this.beans.removeAll(items);
        this.beans.addAll(items);
        notifyDataSetChanged();
        return this;
    }



    public BaseRecyclerAdapter replaceItem(int p, T bean) {
        this.beans.remove(p);
        this.beans.add(p, bean);
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


    public List<T> getItems() {
        return beans;
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }


    public interface OnRecyclerViewListener {
        void onItemClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public BaseRecyclerAdapter setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
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
    public class BaseRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;


        public BaseRecyclerHolder(View v) {
            this(v, true);
        }

        public BaseRecyclerHolder(View v, boolean onClick) {
            super(v);
            if (onClick)
                itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }
    }

//    private void setTypeFace(Context c, TextView v) {
//        AssetManager mgr = c.getAssets();//得到AssetManager
//        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto-Black.ttf");//根据路径得到Typeface
//        v.setTypeface(tf);//设置字体
//        v.getPaint().setFakeBoldText(true);
//    }


}
