package com.example.my.baidu.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.my.baidu.Adapter.NewsAdapter;
import com.example.my.baidu.Bean.NewsBean;
import com.example.my.baidu.NewsActivity;
import com.example.my.baidu.R;
import com.example.my.baidu.View.SlideShow;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4261305 on 2016/2/26.
 */
public class xinwenFragment extends Fragment implements  AdapterView.OnItemClickListener {
    @ViewInject(R.id.slideshowView)
    private ViewPager viewPager;
    @ViewInject(R.id.list_view)
    private ListView mListView;
    @ViewInject(R.id.slideshowView)
    private SlideShow slideShow;


    private static String URL ="http://www.imooc.com/api/teacher?type=4&num=30";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.xinwen,null);


        ViewUtils.inject(this, view);
        setScrollBar();



       // mListView.addHeaderView(slideShow);
        new NewsAsyncTask().execute(URL);
        mListView.setOnItemClickListener(this);



        return view;


    }
    private void setScrollBar(){
        try {
            Field f = AbsListView.class.getDeclaredField("mFastScroller");
            f.setAccessible(true);
            Object o=f.get(mListView);
            f=f.getType().getDeclaredField("mThumbDrawable");
            f.setAccessible(true);
            Drawable drawable=(Drawable) f.get(o);
            drawable=getResources().getDrawable(R.drawable.nbatubiao);
            f.set(o,drawable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private String readStream(InputStream is){
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line =br.readLine())!=null){
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Intent intent = new Intent(getActivity(),NewsActivity.class);
        intent.putExtra("url","http://bbs.hupu.com/16131389.html");
        startActivity(intent);


    }






    class NewsAsyncTask extends AsyncTask<String ,Void , List<NewsBean>> {


        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonDate(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);
            NewsAdapter newsAdapter = new NewsAdapter(getActivity(), newsBeans, mListView);

            mListView.setAdapter(newsAdapter);



            }
        }

        private List<NewsBean> getJsonDate(String url) {
            List<NewsBean> newsBeanList = new ArrayList<>();
            try {
                String jsonString = readStream(new URL(url).openStream());
                JSONObject jsonObject;
                NewsBean newsBean;
                try {
                    jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        newsBean = new NewsBean();
                        newsBean.newsIconUrl = jsonObject.getString("picSmall");

                        newsBean.newsTitle = jsonObject.getString("name");
                        newsBean.newsContent = jsonObject.getString("description");
                        newsBeanList.add(newsBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsBeanList;
        }


    }

