package com.mark0420.mk_view;

/**
 * Created by mark on 2017/7/6.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 * positions=new int[][]{{4,5},{8,9}}
 */

public class ItemDecorationIndent {

    int[][] positions;
    int left;//分割线缩进,
    int right;//分割线缩进,

    public ItemDecorationIndent(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public ItemDecorationIndent(int[][] positions, int left, int right) {
        this.positions = positions;
        this.left = left;
        this.right = right;
    }
}
