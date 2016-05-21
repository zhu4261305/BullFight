package com.example.my.baidu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.baidu.Adapter.SortAdapter;
import com.example.my.baidu.Adapter.SubAdapter;
import com.example.my.baidu.R;
import com.example.my.baidu.Utils.CharacterParser;
import com.example.my.baidu.Utils.PinyinComparator;
import com.example.my.baidu.Utils.SortModel;
import com.example.my.baidu.View.SiderBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 4261305 on 2016/2/26.
 */
public class goucheFragment extends Fragment implements AbsListView.OnScrollListener{
    @ViewInject(R.id.country_lvcountry)
    private ListView sortListView;
    @ViewInject(R.id.child)
    private ListView childView;
    @ViewInject(R.id.siderbar)
    private SiderBar siderBar;


    private SortAdapter adapter;
    private SubAdapter subAdapter;


    /**
     * 汉字转换成拼音的类
     */
    private List<SortModel> SourceDateList;
    private CharacterParser characterParser;


    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part1, null);
        ViewUtils.inject(this, view);
        initViews();
        iniData();


        return view;
    }

    private void iniData() {
        final String cities[][] = new String[][] {// 子菜单 - 二维数组。
                new String[] { "乔治.保罗", "克里斯特玛斯.拉金", "劳森.泰", "希尔.乔丹", "希尔.所罗门", "希尔.乔治","惠廷顿.肖恩","杨.乔","特纳.迈勒斯" },
                new String[] { "人物", "花鸟", "山水", "宗教", "其他", "" },
                new String[] { "篆书", "楷书", "隶书", "行书", "草书", "其他" },
                new String[] { "人物", "花鸟", "山水", "宗教", "其他", "" },
                new String[] { "篆书", "楷书", "隶书", "行书", "草书", "其他" },
                new String[] { "人物", "花鸟", "山水", "宗教", "其他", "" },

        };
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                subAdapter = new SubAdapter(getContext(), cities,
                        position);
                childView.setAdapter(subAdapter);
                childView.setVisibility(View.VISIBLE);
                siderBar.setVisibility(View.GONE);
            }
        });
        sortListView.setOnScrollListener(this);
    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getContext(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        SourceDateList = new ArrayList<SortModel>();




        SourceDateList = filledData(getResources().getStringArray(R.array.date));

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(getContext(), SourceDateList);
        sortListView.setAdapter(adapter);

    }
    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_TOUCH_SCROLL:
                childView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
