package com.example.my.baidu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.baidu.Adapter.BaseAdapterHelper;
import com.example.my.baidu.Adapter.QuickAdapter;
import com.example.my.baidu.Bean.Index;
import com.example.my.baidu.Bean.Lost;
import com.example.my.baidu.Bean.Quliao;
import com.example.my.baidu.Bean.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.my.baidu.R.id.tv_name;

/**
 * Created by 4261305 on 2016/3/16.
 */
public class XiangqingActivity extends Activity implements View.OnClickListener{

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_describe)
    private TextView tv_describe;
    @ViewInject(R.id.image_qiuchang)
    private ImageView image_qiuchang;
    @ViewInject(R.id.user_logo_navi)
    private ImageView imageView;
    private Lost lost  ;
    private Index indexs;
    private Quliao quliao;
    private ImageLoader imageLoad = ImageLoader.getInstance();
    @ViewInject(R.id.daohang)
    protected TextView daohang;
    @ViewInject(R.id.zuobiao)
    private static final TextView zuobiao = null;
    private String x;
    private int y;
    @ViewInject(R.id.mingzi)
    private TextView mingzi;
    @ViewInject(R.id.jiaru)
    private Button jiaru;
    private static String path="/sdcard/myHead/";//sd路径
    String imgpath =path+"/head.jpg" ;
    @ViewInject(R.id.wanjia)
    private GridView gridView;
    private List<Quliao> quliaos;
    private QuickAdapter<Quliao> quickAdapter;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiangqing);
        ViewUtils.inject(this);
       jiaru.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bund = intent.getBundleExtra("value");
        lost = (Lost) bund.getSerializable("Lost");

        initImage();
        iniData();
        initView();
        initWanjia();
    //    MyGridView myGridView = new MyGridView(XiangqingActivity.this, quliaos );
     //   gridView.setAdapter(myGridView);

    }

    private void initWanjia() {
        if (quickAdapter == null) {
            quickAdapter = new QuickAdapter<Quliao>(this, R.layout.grid) {
                @Override
                protected void convert(BaseAdapterHelper helper, Quliao item) {
                    helper.setText(tv_name, item.getName());

                }

            };
        } gridView.setAdapter(quickAdapter);
    queryLosts() ;
    }

    private void queryLosts() {
        BmobQuery<Quliao> query = new BmobQuery<Quliao>();
        query.findObjects(this, new FindListener<Quliao>() {
            @Override
            public void onSuccess(List<Quliao> list) {
                quickAdapter.clear();
                quickAdapter.addAll(list);
                gridView.setAdapter(quickAdapter);

                if (list == null || list.size() == 0) {

                    quickAdapter.notifyDataSetChanged();
                    return;
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    String name = "";

    private void initShuju() {
        User user = BmobUser.getCurrentUser(getApplicationContext(), User.class);
        name = user.getName();
        final Quliao quliao = new Quliao();
       String myname = quliao.getName();
       if(name == myname){
            Toast.makeText(XiangqingActivity.this,"请勿重复添加",Toast.LENGTH_SHORT).show();

       }else {

           quliao.setName(name);
           final BmobFile icon = new BmobFile(new File(imgpath));

           quliao.setPicFile(icon);
           icon.upload(this, new UploadFileListener() {

               @Override
               public void onSuccess() {
                   // TODO Auto-generated method stub


                   quliao.save(XiangqingActivity.this);


               }

               public void onFailure(int arg0, String arg1) {
                   // TODO Auto-generated method stub
                   Toast.makeText(XiangqingActivity.this, "", Toast.LENGTH_LONG).show();
               }
           });
           quliao.save(this, new SaveListener() {
               @Override
               public void onSuccess() {
                   Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onFailure(int i, String s) {

               }
           });
       }



       }


    private void initImage() {
        /*SharedPreferences preferences = getSharedPreferences("index", Activity.MODE_PRIVATE);

        x = preferences.getString("index", null);*/


   // Intent intent = getIntent();
  //  String x  = intent.getStringExtra("index");






      //  zuobiao.setText(x);
        String x = lost.getNum();

        int y=Integer.parseInt(x);
         switch (y){
            case 0:

                break;
             case 1:
                 zuobiao.setText("溪水弯21号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 2:

                 zuobiao.setText("溪水弯22号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 3:
                 zuobiao.setText("溪水弯23号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 4:
                 zuobiao.setText("溪水弯24号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 5:

                 zuobiao.setText("溪水弯25号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);

                 break;
             case 6:

                 zuobiao.setText("溪水弯26号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 7:

                 zuobiao.setText("溪水弯27号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 8:

                 zuobiao.setText("溪水弯28号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 9:

                 zuobiao.setText("溪水弯29号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;
             case 10:

                 zuobiao.setText("溪水弯30号海宁大道");
                 image_qiuchang.setImageResource(R.drawable.kebi);
                 break;


            default:
                break;
        }
    }


    private void initView() {
        BmobFile avatar = lost.getPicFile();
        if (avatar != null) {
            ImageLoader.getInstance()
                    .displayImage(avatar.getFileUrl(getApplication()), imageView,

                            new SimpleImageLoadingListener() {

                                @Override
                                public void onLoadingComplete(String imageUri, View view,
                                                              Bitmap loadedImage) {

                                    imageView.setImageBitmap(loadedImage);
                                }

                            });
        }
        User user = BmobUser.getCurrentUser(getApplicationContext(), User.class);
        if (user != null) {
            mingzi.setText(user.getName());
        }
    }

    private void iniData() {
        tv_describe.setText(lost.getDescribe());
        tv_title.setText(lost.getTitle());
        daohang.setText(lost.getPlace());


    }


    @Override
    public void onClick(View view) {
        if (view == jiaru){
            initShuju();
        }
    }
}
