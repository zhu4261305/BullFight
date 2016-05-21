package com.example.my.baidu;

/**
 * Created by 4261305 on 2016/3/15.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.baidu.Adapter.BaseAdapterHelper;
import com.example.my.baidu.Adapter.QuickAdapter;
import com.example.my.baidu.Bean.Lost;
import com.example.my.baidu.Utils.LogUtils;
import com.example.my.baidu.View.MyListView;
import com.example.my.baidu.View.MyListView.OnRefreshListener;
import com.example.my.baidu.other.Constants;
import com.example.my.baidu.other.IPopupItemClick;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import static com.example.my.baidu.R.id.tv_describe;
import static com.example.my.baidu.R.id.tv_title;

public class QqActivity extends BaseActivity implements View.OnClickListener ,IPopupItemClick ,AdapterView.OnItemClickListener{
    public static final String KEY_DETAILS = "details";
    @ViewInject(R.id.list_lost)
    private MyListView mListView;
    protected QuickAdapter<Lost> LostAdapter;

    private Button btn_add;
    private Button ditu;
    private List<Lost> lostList = new ArrayList<>();
    @ViewInject(R.id.item_image)
    RelativeLayout progress;
    TextView tv_no;
    TextView tv_lost;
    LinearLayout layout_no;



    public void setContentView() {
        setContentView(R.layout.activity_main);
    }
    public void setScrollBar(){
        try {
            Field f = AbsListView.class.getDeclaredField("mFastScroller");
            f.setAccessible(true);
            Object o=f.get(mListView);
            f=f.getType().getDeclaredField("mThumbDrawable");
            f.setAccessible(true);
            Drawable drawable=(Drawable) f.get(o);
            drawable=getResources().getDrawable(R.drawable.ic_launcher);
            f.set(o,drawable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void qurryall(){
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.setLimit(20);
        query.findObjects(QqActivity.this, new FindListener<Lost>() {
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
    @Override
    public void initViews() {
        progress = (RelativeLayout) findViewById(R.id.progress);
        layout_no = (LinearLayout) findViewById(R.id.layout_no);
        tv_no = (TextView) findViewById(R.id.tv_no);
        tv_lost = (TextView) findViewById(R.id.tv_lost);
        tv_lost.setTag("Lost");
        mListView = (MyListView) findViewById(R.id.list_lost);
        btn_add = (Button) findViewById(R.id.btn_add);
        ditu = (Button) findViewById(R.id.ditu);
    }
    public void initListeners() {
        btn_add.setOnClickListener(this);
        ditu.setOnClickListener(this);
        mListView.setOnItemClickListener(this);

    }
    public void initData() {
        // TODO Auto-generated method stub
        if (LostAdapter == null) {
            LostAdapter = new QuickAdapter<Lost>(this, R.layout.item_list) {
                @Override
                protected void convert(BaseAdapterHelper helper, Lost lost) {
                    helper.setText(tv_title, lost.getTitle())
                            .setText(tv_describe, lost.getDescribe())
                           ;



                }
            };
        } mListView.setAdapter(LostAdapter);
        mListView.setonRefreshListener(new OnRefreshListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
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
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.order("-createdAt");// 按照时间降序
        query.findObjects(this, new FindListener<Lost>() {

            @Override
            public void onSuccess(List<Lost> losts) {
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


    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == btn_add) {
            BmobUser bmobUser = BmobUser.getCurrentUser(QqActivity.this);
            if (bmobUser != null){
                String name = bmobUser.getUsername();
                String email = bmobUser.getEmail();
                LogUtils.i(TAG,"username:"+name+",email:"+email);
                Toast.makeText(QqActivity.this, "登录中。",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, addActivity.class);
                intent.putExtra("from", tv_lost.getTag().toString());
                startActivityForResult(intent, Constants.REQUESTCODE_ADD);

            }else {
                Toast.makeText(QqActivity.this, "请先登录。",
                        Toast.LENGTH_SHORT).show();
//                  redictToActivity(mContext, RegisterAndLoginActivity.class, null);
                Intent intent = new Intent();
                intent.setClass(QqActivity.this, LoginActivity.class);
                startActivity(intent);
            }


        }
        if (v == ditu){
              startActivity(new Intent(this,dituActivity.class));
        }
    }


    @Override
    public void onEdit(View v) {
        // TODO Auto-generated method stub
        String tag = tv_lost.getTag().toString();
        Intent intent = new Intent(this, addActivity.class);
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



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent mIntent = new Intent(this,XiangqingActivity.class);
        Bundle bund = new Bundle();
        bund.putSerializable("Lost",lostList.get(position-1));
        mIntent.putExtra("value", bund);
        startActivity(mIntent);




    }
}


























