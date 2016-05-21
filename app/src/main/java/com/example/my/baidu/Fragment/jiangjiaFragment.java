package com.example.my.baidu.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.my.baidu.Adapter.BaseAdapterHelper;
import com.example.my.baidu.Adapter.QuickAdapter;
import com.example.my.baidu.Bean.Jieba;
import com.example.my.baidu.Bean.Lost;
import com.example.my.baidu.R;
import com.example.my.baidu.View.MyListView;
import com.example.my.baidu.XiangqingActivity;
import com.example.my.baidu.addActivity;
import com.example.my.baidu.other.Constants;
import com.example.my.baidu.other.IPopupItemClick;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

import static com.example.my.baidu.R.id.tv_describe;
import static com.example.my.baidu.R.id.tv_title;

/**
 * Created by 4261305 on 2016/2/26.
 */
public class
        jiangjiaFragment extends Fragment implements IPopupItemClick,AdapterView.OnItemClickListener{
    public static final String KEY_DETAILS = "details";
    @ViewInject(R.id.list_lost)
    private MyListView mListView;
    protected QuickAdapter<Jieba> LostAdapter;
    @ViewInject(R.id.item_image)
    private ImageView image;

    private List<Lost> lostList = new ArrayList<>();
    @ViewInject(R.id.progress)
    RelativeLayout progress;
    @ViewInject(R.id.tv_no)
    TextView tv_no;
    @ViewInject(R.id.tv_lost)
    TextView tv_lost;
    @ViewInject(R.id.layout_no)
    LinearLayout layout_no;
    private Jieba lost = new Jieba();


    public static final String TAG = "bmob";



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.jiebaa,null)    ;
        ViewUtils.inject(this, view);
        initListeners();

        qurryall();
        initData();
        initView();
        Bmob.initialize(getActivity(), "a156fa8c5e588f25aef68fc27fe38fb2");

        return view;
    }
    private void initView() {
        BmobFile avatar = lost.getPicFile();
        if (avatar != null) {
            ImageLoader.getInstance()
                    .displayImage(avatar.getFileUrl(getContext()), image,

                            new SimpleImageLoadingListener() {

                                @Override
                                public void onLoadingComplete(String imageUri, View view,
                                                              Bitmap loadedImage) {

                                    image.setImageBitmap(loadedImage);
                                }

                            });
        }
    }

    public void qurryall(){
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.setLimit(20);
        query.findObjects(getActivity(), new FindListener<Lost>() {
            @Override
            public void onSuccess(List<Lost> list) {
                for (int m = list.size() - 1; m >= 0; m--) {
                    lostList.add(list.get(m));

                }


            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }


    public void initListeners() {

        mListView.setOnItemClickListener(this);

    }
    public void initData() {
        // TODO Auto-generated method stub
        if (LostAdapter == null) {
            LostAdapter = new QuickAdapter<Jieba>(getActivity(), R.layout.jiebaya) {

                @Override
                protected void convert(BaseAdapterHelper helper, Jieba item) {
                    helper.setText(tv_title,item.getTitle())
                            .setText(tv_describe, item.getDescribe())
                    ;
                }






            };
        } mListView.setAdapter(LostAdapter);
        mListView.setonRefreshListener(new MyListView.OnRefreshListener() {
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        queryLosts();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        LostAdapter.notifyDataSetChanged();
                        mListView.onRefreshComplete();
                    }

                }.execute();
            }
        });

        queryLosts();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode !=getActivity(). RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.REQUESTCODE_ADD:
                String tag = tv_lost.getTag().toString();
                if (tag.equals("Lost")) {
                    queryLosts();

                }
                break;
        }
    }

    private void queryLosts() {
        showView();
        BmobQuery<Jieba> query = new BmobQuery<Jieba>();
        query.order("-createdAt");// 按照时间降序
        query.findObjects(getActivity(), new FindListener<Jieba>() {

            @Override
            public void onSuccess(List<Jieba> losts) {
                // TODO Auto-generated method stub
                LostAdapter.clear();

                if (losts == null || losts.size() == 0) {
                    showErrorView(0);
                    LostAdapter.notifyDataSetChanged();
                    return;
                }
                progress.setVisibility(View.GONE);
                LostAdapter.addAll(losts);
                mListView.setAdapter(LostAdapter);
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                showErrorView(0);
            }
        });
    }
    private void showErrorView(int tag) {
        progress.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        layout_no.setVisibility(View.VISIBLE);
        if (tag == 0) {
            tv_no.setText(getResources().getText(R.string.list_no_data_lost));
        } else {
            tv_no.setText(getResources().getText(R.string.list_no_data_found));
        }
    }
    int position;

    private void showView() {
        mListView.setVisibility(View.VISIBLE);
        layout_no.setVisibility(View.GONE);
    }





    public void onEdit(View v) {
        // TODO Auto-generated method stub
        String tag = tv_lost.getTag().toString();
        Intent intent = new Intent(getActivity(), addActivity.class);
        String title = "";
        String describe = "";
        String phone = "";
        if (tag.equals("Lost")) {
            title = LostAdapter.getItem(position).getTitle();
            describe = LostAdapter.getItem(position).getDescribe();

        }
        intent.putExtra("describe", describe);
        intent.putExtra("phone", phone);
        intent.putExtra("title", title);
        intent.putExtra("from", tag);
        startActivityForResult(intent, Constants.REQUESTCODE_ADD);
    }




    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent mIntent = new Intent(getActivity(),XiangqingActivity.class);
        Bundle bund = new Bundle();
        bund.putSerializable("Lost",lostList.get(position-1));
        mIntent.putExtra("value", bund);
        startActivity(mIntent);




    }



}
