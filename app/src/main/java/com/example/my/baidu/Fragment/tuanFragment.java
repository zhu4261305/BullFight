package com.example.my.baidu.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.my.baidu.Adapter.GalleryAdapter;
import com.example.my.baidu.Adapter.MyGridAdapter;
import com.example.my.baidu.R;
import com.example.my.baidu.View.AndroidSegmentedControlView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by 4261305 on 2016/2/26.
 */
public class tuanFragment extends Fragment {
    @ViewInject(R.id.xiangmu)
    private GridView gridview;
    @ViewInject(R.id.ascv_sample_holder)
    private LinearLayout holder;

    @ViewInject(R.id.id_recyclerview_horizontal)
    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private List<Integer> mDatas ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.category_list,null)    ;
        ViewUtils.inject(this, view);
        initDatas();
        //得到控件
        initView();
        try {


            AndroidSegmentedControlView ascv = new AndroidSegmentedControlView(getActivity());
            ascv.setColors(Color.parseColor("#f82c22"),Color.parseColor("#FFFFFF"));
            ascv.setItems(new String[]{"特训营", "专辑"}, new String[]{"1", "2"});
            ascv.setDefaultSelection(0);
            holder.addView(ascv);




        }catch (Exception ex){
            ex.printStackTrace();
        }



        //设置布局管理器

        return view;
    }

    private void initView() {
        gridview.setAdapter(new MyGridAdapter(getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);

    }
    private void initDatas()
    {  mDatas = new ArrayList<Integer>(Arrays.asList(R.drawable.baoluo,
            R.drawable.weide, R.drawable.andogni, R.drawable.yaominga, R.drawable.kebikatong, R.drawable.jianeite, R.drawable.dengken, R.drawable.hadeng, R.drawable.luosi));

    }



}
