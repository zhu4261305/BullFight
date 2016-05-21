package com.example.my.baidu.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.my.baidu.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4261305 on 2016/2/26.
 */
public class HomeFragment  extends Fragment {
    @ViewInject(R.id.xinwen)
    private  RadioButton xinwen;
    @ViewInject(R.id.zixun)
    private TextView zixun;
    @ViewInject(R.id.tuku)
    private TextView tuku;
    @ViewInject(R.id.zhaoche)
    private TextView zhaoche;
    @ViewInject(R.id.jiangjia)
    private TextView jiangjia;
    @ViewInject(R.id.vp_pager)
    private ViewPager mViewPager;
    @ViewInject(R.id.indicator1)
    private TextView indicator1;
    @ViewInject(R.id.indicator2)
    private TextView indicator2;
    @ViewInject(R.id.indicator3)
    private TextView indicator3;
    @ViewInject(R.id.indicator4)
    private TextView indicator4;
    @ViewInject(R.id.indicator5)
    private TextView indicator5;

    private xinwenFragment mxinwenFragment;
    private zixunFragment  mzixunFragment;
    private jiangjiaFragment mjiangjiaFragment;
    private goucheFragment  mgoucheFragment;
    private tukuFragment mtukuFragment;
    @ViewInject(R.id.rd_main)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.home_tab)
    private RadioButton mRadioButton;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private FragmentManager fragmentManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.part0,null);
        ViewUtils.inject(this, view);
        initParams();


      xinwen.setChecked(true);

        return view;
    }



    protected int getLayoutId(){
        return R.layout.xinwen;
    }
    protected void initParams(){
        mxinwenFragment = new xinwenFragment();
        mzixunFragment = new zixunFragment();
        mtukuFragment = new tukuFragment();
        mgoucheFragment = new goucheFragment();
        mjiangjiaFragment = new jiangjiaFragment();
        fragmentList.add(mxinwenFragment);
        fragmentList.add(mzixunFragment);
        fragmentList.add(mjiangjiaFragment);
        fragmentList.add(mgoucheFragment);
        fragmentList.add(mtukuFragment);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new DefineOnPageChangeListener());



    }
    @OnClick({R.id.xinwen,R.id.zixun,R.id.tuku,R.id.zhaoche,R.id.jiangjia})
    public void Click(View view){
        switch (view.getId()){
            case R.id.xinwen:
                mViewPager.setCurrentItem(0);

                break;
            case R.id.zixun:
                mViewPager.setCurrentItem(1);

                break;
            case R.id.tuku:
                mViewPager.setCurrentItem(2);

                break;
            case R.id.zhaoche:
                mViewPager.setCurrentItem(3);

                break;
            case R.id.jiangjia:
                mViewPager.setCurrentItem(4);

                break;
        }

    }

    public class DefineOnPageChangeListener implements ViewPager.OnPageChangeListener{

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        public void onPageSelected(int arg0) {
            switch (arg0){
                case 0:
                    xinwen.setTextColor(Color.WHITE);
                    zixun.setTextColor(Color.GRAY);
                    tuku.setTextColor(Color.GRAY);
                    zhaoche.setTextColor(Color.GRAY);
                    jiangjia.setTextColor(Color.GRAY);
                    indicator1.setVisibility(View.VISIBLE);
                    indicator2.setVisibility(View.INVISIBLE);
                    indicator3.setVisibility(View.INVISIBLE);
                    indicator4.setVisibility(View.INVISIBLE);
                    indicator5.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    xinwen.setTextColor(Color.GRAY);
                    zixun.setTextColor(Color.WHITE);
                    tuku.setTextColor(Color.GRAY);
                    zhaoche.setTextColor(Color.GRAY);
                    jiangjia.setTextColor(Color.GRAY);
                    indicator1.setVisibility(View.INVISIBLE);
                    indicator2.setVisibility(View.VISIBLE);
                    indicator3.setVisibility(View.INVISIBLE);
                    indicator4.setVisibility(View.INVISIBLE);
                    indicator5.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    xinwen.setTextColor(Color.GRAY);
                    zixun.setTextColor(Color.GRAY);
                    tuku.setTextColor(Color.WHITE);
                    zhaoche.setTextColor(Color.GRAY);
                    jiangjia.setTextColor(Color.GRAY);
                    indicator1.setVisibility(View.INVISIBLE);
                    indicator2.setVisibility(View.INVISIBLE);
                    indicator3.setVisibility(View.VISIBLE);
                    indicator4.setVisibility(View.INVISIBLE);
                    indicator5.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    xinwen.setTextColor(Color.GRAY);
                    zixun.setTextColor(Color.GRAY);
                    tuku.setTextColor(Color.GRAY);
                    zhaoche.setTextColor(Color.WHITE);
                    jiangjia.setTextColor(Color.GRAY);
                    indicator1.setVisibility(View.INVISIBLE);
                    indicator2.setVisibility(View.INVISIBLE);
                    indicator3.setVisibility(View.INVISIBLE);
                    indicator4.setVisibility(View.VISIBLE);
                    indicator5.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    xinwen.setTextColor(Color.GRAY);
                    zixun.setTextColor(Color.GRAY);
                    tuku.setTextColor(Color.GRAY);
                    zhaoche.setTextColor(Color.GRAY);
                    jiangjia.setTextColor(Color.WHITE);
                    indicator1.setVisibility(View.INVISIBLE);
                    indicator2.setVisibility(View.INVISIBLE);
                    indicator3.setVisibility(View.INVISIBLE);
                    indicator4.setVisibility(View.INVISIBLE);
                    indicator5.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;

            }

        }


        public void onPageScrollStateChanged(int state) {

        }
    }





    }


