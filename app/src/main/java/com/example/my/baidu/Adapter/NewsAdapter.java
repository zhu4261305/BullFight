package com.example.my.baidu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my.baidu.Bean.NewsBean;
import com.example.my.baidu.R;
import com.example.my.baidu.View.LoadImage;

import java.util.List;

/**
 * Created by 4261305 on 2016/3/23.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private List<NewsBean> mList;
    private LayoutInflater layoutInflater;
    private LoadImage mImgLoader;
    private int mStart;
    private int mEnd;
    public static String[] URLS;


    private boolean mFirstIn;

    public NewsAdapter(Context context ,List<NewsBean> data, ListView listView ){
        layoutInflater = LayoutInflater.from(context);
        mImgLoader = new LoadImage(listView);
        mList = data;
        URLS = new String[data.size()];
        for(int i = 0; i < data.size(); i++)
        {
            URLS[i] = data.get(i).newsIconUrl;
        }
        listView.setOnScrollListener(this);
        mFirstIn = true;

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (converView == null){
            viewHolder = new ViewHolder();
            converView = layoutInflater.inflate(R.layout.list_item,null);
            viewHolder.ivIcon = (ImageView) converView.findViewById(R.id.icon_tv);
            viewHolder.tvTitle = (TextView) converView.findViewById(R.id.title_tv);
            viewHolder.tvContent = (TextView) converView.findViewById(R.id.content_tv);
            converView .setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).newsIconUrl;
        viewHolder.ivIcon.setTag(url);
        // new ImageLoad().showImageFromAsyncTsk(viewHolder.ivIcon, newsBean.newsIconUrl);
        mImgLoader.showImgByAysncTask(viewHolder.ivIcon, url);
        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);
        return converView;
    }




    class ViewHolder{
        public TextView tvTitle,tvContent;
        public ImageView ivIcon;
    }
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        //处于停止状态
        if(scrollState == SCROLL_STATE_IDLE)
        {
            //加载可见项
            mImgLoader.loadImages(mStart, mEnd);
        }
        else
        {
            //停止加载
            mImgLoader.cancelAllTasks();
        }
    }


    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;

        //首次加载时 使用的,
        if(mFirstIn == true && visibleItemCount > 0)
        {
            mImgLoader.loadImages(mStart, mEnd);
            mFirstIn = false;
        }
    }
}
