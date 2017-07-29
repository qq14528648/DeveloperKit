package com.mark0420.mk_view;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/07/08
 * 邮箱：mark14528648@yahoo.co.jp
 * 适配器基类{@link  }
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected List<T> beans = new ArrayList();


    public SparseBooleanArray enables = new SparseBooleanArray();

    public BaseRecyclerAdapter() {
        initEnables(true);
    }

    public void setEnable(int position) {
        enables.put(position, false);
        notifyDataSetChanged();

    }

    // 此方法一定要在数据加载之后调用
    public void initEnables(boolean initEnable) {
        for (int i = 0; i < getItems().size(); i++) {
            enables.put(i, initEnable);
        }
    }

    /**
     * 添加数据集,清空并自动更新数据
     *
     * @param beans
     */
    public BaseRecyclerAdapter addItems(List<T> beans) {
        addItems(beans, true, true);
        return this;
    }

    /**
     * 添加数据集,清空并自动更新数据
     *
     * @param beans
     */
    public BaseRecyclerAdapter addItems(List<T> beans, boolean clear, boolean notify) {
        if (clear) this.beans.clear();
        this.beans.addAll(beans);
        if (notify) notifyDataSetChanged();
        return this;
    }

    /**
     * 移除数据集,自动更新数据
     */
    public BaseRecyclerAdapter removeItems(List<T> beans, boolean notify) {
        this.beans.removeAll(beans);
        if (notify) notifyDataSetChanged();
        return this;
    }

    /**
     * 移除数据,自动更新数据
     */
    public BaseRecyclerAdapter removeItem(int p, boolean notify) {
        this.beans.remove(p);
        if (notify) notifyItemChanged(p);
        return this;
    }


    public BaseRecyclerAdapter replaceItem(int p, T bean) {

        this.beans.remove(p);
        this.beans.add(p, bean);
        notifyItemChanged(p);
        return this;
    }

    /**
     * 清空数据源
     */
    public void clear() {
        this.beans.clear();
    }

    /**
     * 添加单条数据 ,不清空且不自动更新数据
     *
     * @param bean
     */

    public void addItem(T bean) {
        this.beans.add(bean);
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
