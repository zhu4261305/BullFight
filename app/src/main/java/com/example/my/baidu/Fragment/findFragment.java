package com.example.my.baidu.Fragment;

/**
 * Created by 4261305 on 2016/2/26.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.baidu.Adapter.BaseAdapterHelper;
import com.example.my.baidu.Adapter.QuickAdapter;
import com.example.my.baidu.Adapter.XuanAdapter;
import com.example.my.baidu.Bean.Lost;
import com.example.my.baidu.LoginActivity;
import com.example.my.baidu.R;
import com.example.my.baidu.Utils.LogUtils;
import com.example.my.baidu.View.CircleButton;
import com.example.my.baidu.View.MyListView;
import com.example.my.baidu.XiangqingActivity;
import com.example.my.baidu.addActivity;
import com.example.my.baidu.dituActivity;
import com.example.my.baidu.other.Constants;
import com.example.my.baidu.other.IPopupItemClick;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import static com.example.my.baidu.R.id.item_image;
import static com.example.my.baidu.R.id.tv_describe;
import static com.example.my.baidu.R.id.tv_place;
import static com.example.my.baidu.R.id.tv_time;
import static com.example.my.baidu.R.id.tv_title;




public class findFragment extends Fragment implements View.OnClickListener,IPopupItemClick,AdapterView.OnItemClickListener {
    public static final String KEY_DETAILS = "details";
    @ViewInject(R.id.list_lost)
    private MyListView mListView;
    protected QuickAdapter<Lost> LostAdapter;
    @ViewInject(R.id.btn_add)
    private CircleButton btn_add;
    @ViewInject(R.id.ditu)
    private CircleButton ditu;
    private List<Lost> lostList = new ArrayList<>();
    @ViewInject(R.id.progress)
    RelativeLayout progress;
    @ViewInject(R.id.tv_no)
    TextView tv_no;
    @ViewInject(R.id.tv_lost)
    TextView tv_lost;
    @ViewInject(R.id.layout_no)
    LinearLayout layout_no;
     String m ="";
    @ViewInject(R.id.id_recyclerview_horizontal)
    private RecyclerView mRecyclerView;
    private XuanAdapter mAdapter;
    private List<Integer> data

     = new ArrayList<Integer>(Arrays.asList(R.drawable.touxiang,
            R.drawable.touxiangf, R.drawable.touxianga, R.drawable.touxiangi, R.drawable.touxiangn, R.drawable.touxiangs, R.drawable.touxiangt));



    public static final String TAG = "bmob";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);
        ViewUtils.inject(this, view);
        initListeners();
        setScrollBar();
        qurryall();
        initData();
        initView();

        return view;
    }
    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new XuanAdapter(getActivity(), data);
        mRecyclerView.setAdapter(mAdapter);

    }






    public void setScrollBar() {
        try {
            Field f = AbsListView.class.getDeclaredField("mFastScroller");
            f.setAccessible(true);
            Object o = f.get(mListView);
            f = f.getType().getDeclaredField("mThumbDrawable");
            f.setAccessible(true);
            Drawable drawable = (Drawable) f.get(o);
            drawable = getResources().getDrawable(R.drawable.ic_launcher);
            f.set(o, drawable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void qurryall() {
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
        btn_add.setOnClickListener(this);
        ditu.setOnClickListener(this);

        mListView.setOnItemClickListener(this);

    }

    public void initData() {
        // TODO Auto-generated method stub
        if (LostAdapter == null) {
            LostAdapter = new QuickAdapter<Lost>(getActivity(), R.layout.item_list) {

                @Override
                protected void convert(final BaseAdapterHelper helper, Lost lost) {
                    helper.setText(tv_title, lost.getTitle())
                            .setText(tv_describe, lost.getDescribe()).setText(tv_time, lost.getTime()).
                            setText(tv_place, lost.getPlace());

                    if (lost != null) {

                        m = lost.getWeizhi();
                       // Toast.makeText(getActivity(),m,Toast.LENGTH_SHORT).show();
                        switch (m) {
                            case "pg":
                                helper.setImageResource(item_image, R.drawable.timg);
                                break;
                            case "sg":
                                helper.setImageResource(item_image, R.drawable.sanjing);
                                break;
                            case "sf":
                                helper.setImageResource(item_image, R.drawable.liu);
                                break;
                            case "pf":
                                helper.setImageResource(item_image, R.drawable.yingmua);
                                break;
                            case "c":
                                helper.setImageResource(item_image, R.drawable.vhimu);
                                break;
                            default:
                                break;
                        }
                    }else {
                        helper.setImageResource(item_image, R.drawable.anxi);
                    }
                }









            };
        }
        mListView.setAdapter(LostAdapter);
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
        if (resultCode != getActivity().RESULT_OK) {
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
        query.findObjects(getActivity(), new FindListener<Lost>() {

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
        tv_lost.setTag("Lost");
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == btn_add) {
            BmobUser bmobUser = BmobUser.getCurrentUser(getActivity());
            if (bmobUser != null) {
                String name = bmobUser.getUsername();
                String email = bmobUser.getEmail();
                LogUtils.i(TAG, "username:" + name + ",email:" + email);
                Toast.makeText(getActivity(), "登录中。",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), addActivity.class);
                intent.putExtra("from", tv_lost.getTag().toString());
                startActivityForResult(intent, Constants.REQUESTCODE_ADD);

            } else {
                Toast.makeText(getActivity(), "请先登录。",
                        Toast.LENGTH_SHORT).show();
//                  redictToActivity(mContext, RegisterAndLoginActivity.class, null);
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }


        }
        if (v == ditu) {
            startActivity(new Intent(getActivity(), dituActivity.class));
        }
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
        Intent mIntent = new Intent(getActivity(), XiangqingActivity.class);
        Bundle bund = new Bundle();
        bund.putSerializable("Lost", lostList.get(position - 1));
        mIntent.putExtra("value", bund);
        startActivity(mIntent);


    }
}