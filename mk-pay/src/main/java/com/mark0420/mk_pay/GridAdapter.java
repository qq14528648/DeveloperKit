package com.mark0420.mk_pay;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mark on 16/10/13.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 */


public class GridAdapter extends BaseAdapter {

    private ArrayList<Key> numbers = new ArrayList<>();

    private Context context;

    public GridAdapter(Context context) {
        this.context = context;
        initData();
    }

    private static final String[] letters = {null,null, "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};

    /**
     * 加载模拟键盘上数据的代码
     */
    private void initData() {
        /* 初始化按钮上应该显示的数字 */
        for (int i = 1; i < 10; i++) {
            numbers.add(new Key(String.valueOf(i), letters[i]));
        }
        numbers.add(new Key(null));
        numbers.add(new Key(String.valueOf("0")));
        numbers.add(new Key(String.valueOf("×")));
    }

    @Override
    public int getCount() {
        return numbers.size();
    }


    @Override
    public Key getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.password_layout_keyboard_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.keyTextView = (TextView) convertView
                    .findViewById(R.id.keyTextView);
            viewHolder.letterTextView = (TextView) convertView
                    .findViewById(R.id.letterTextView);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.keyTextView.setText(numbers.get(position).key);
        viewHolder.letterTextView.setText(numbers.get(position).letter);
        if (position == 9 || position == 11) {
            viewHolder.layout.setBackgroundDrawable(Utils.getStateListDrawable(context));
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public LinearLayout layout;
        public TextView keyTextView;
        public TextView letterTextView;
    }
}