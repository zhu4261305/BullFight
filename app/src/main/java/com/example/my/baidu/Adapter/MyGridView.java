package com.example.my.baidu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.my.baidu.Bean.Quliao;
import com.example.my.baidu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4261305 on 2016/5/11.
 */
public class MyGridView extends BaseAdapter {
    private List<Quliao> quliaos = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView tvname;




    public MyGridView(Context context, List<Quliao> list) {
        this.context = context;
        this.quliaos = list;
        layoutInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return quliaos.size();
    }

    @Override
    public Object getItem(int position) {
        return quliaos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {


        converView = layoutInflater.inflate(R.layout.grid, null);
        tvname = (TextView) converView.findViewById(R.id.tv_name);


        return converView;


    }
}
