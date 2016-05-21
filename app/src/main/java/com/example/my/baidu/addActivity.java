package com.example.my.baidu;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.baidu.Bean.Lost;
import com.example.my.baidu.Bean.User;
import com.example.my.baidu.View.DateTimePickDialogUtil;
import com.example.my.baidu.choose.LoopView;
import com.example.my.baidu.choose.OnItemSelectedListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 4261305 on 2016/3/15.
 */
public class addActivity extends Activity  implements View.OnClickListener{
    Button btn_back, btn_true;
    private RelativeLayout rootview;
    private RelativeLayout.LayoutParams layoutParams;
    private TextView choose;
    @ViewInject(R.id.inputDate)
    private EditText startDateTime;


    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间

    EditText edit_title, edit_photo, edit_describe,inputDate;

    private static String path="/sdcard/myHead/";//sd路径
    String imgpath =path+"/head.jpg" ;

    String from = "";
    String old_title = "";
    String old_describe = "";
    String old_phone = "";


    ArrayList<String> lists = new ArrayList<>();
    private String w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ViewUtils.inject(this);
        Bmob.initialize(this, "a156fa8c5e588f25aef68fc27fe38fb2");
        startDateTime.setText(initStartDateTime);

        initTime();
        initViews();
        initListeners();
        initData();
        initChoose();
        User user = BmobUser.getCurrentUser(addActivity.this, User.class);
        if (user != null) {
            weizhi = user.getWeizhi();

        }
    }

    private void initTime() {
        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        addActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });


    }



    private void initChoose() {
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        rootview = (RelativeLayout) findViewById(R.id.rootview);


        LoopView loopView = new LoopView(this);


        lists.add("三中球场" );
        lists.add("工大球场" );
        lists.add("二中球场" );
        lists.add("四中球场" );
        lists.add("路口球场" );
        lists.add("西门球场" );
        lists.add("金水湾球场" );
        lists.add("港新球场" );
        lists.add("干部局球场" );
        lists.add("新街口球场");


        //设置是否循环播放
        //loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                choose.setText(lists.get(index));
                rootview.setVisibility(View.GONE);

           /*    SharedPreferences sp = getSharedPreferences("index",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("index", String.valueOf(index));
                editor.commit();*/
                num = String.valueOf(index);
             /*   Index indexs = new Index();
                indexs.setNum(lists.get(index));
               Intent intent = new Intent(addActivity.this,XiangqingActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("index",indexs);
                intent.putExtras(mBundle);
                startActivity(intent);*/
             /*   Intent intent = new Intent(addActivity.this,XiangqingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("index",lists.get(index));
                intent.putExtras(bundle);
                startActivity(intent);*/
             /*   Intent myIntent = new Intent();
                myIntent.putExtra("index", lists.get(index));
                myIntent.setClass(addActivity.this, XiangqingActivity.class);
                startActivity(myIntent);*/

            }
        });
        //设置原始数据
        loopView.setItems(lists);
        //设置初始位置
        loopView.setInitPosition(5);
        //设置字体大小
        loopView.setTextSize(30);
        rootview.addView(loopView, layoutParams);




    }


    //设置数据
    private void initData() {
        from = getIntent().getStringExtra("from");
        old_title = getIntent().getStringExtra("title");
        old_phone = getIntent().getStringExtra("phone");
        old_describe = getIntent().getStringExtra("describe");
        time = getIntent().getStringExtra("time");
        edit_title.setText(old_title);
        edit_describe.setText(old_describe);
        edit_photo.setText(old_phone);



    }
     //为确认与返回设置点击事件
    private void initListeners() {
        btn_back.setOnClickListener(this);
        btn_true.setOnClickListener(this);
        choose.setOnClickListener(this);

    }


    //实例化各个按钮控件
    private void initViews() {
        choose= (TextView) findViewById(R.id.choose);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_true = (Button) findViewById(R.id.btn_true);
        edit_photo = (EditText) findViewById(R.id.edit_photo);
        edit_describe = (EditText) findViewById(R.id.edit_describe);
        edit_title = (EditText) findViewById(R.id.edit_title);
        inputDate = (EditText) findViewById(R.id.inputDate);

    }

    @Override
    public void onClick(View view) {
        if (view == btn_true) {

            addByType();




            } else if (view == btn_back)
        {
           finish();
        }else if( view == choose){
            rootview.setVisibility(View.VISIBLE);
        }

    }

    private void toast(String s) {
    }
    String num = "";
    String place = "";
    String title = "";
    String describe = "";
    String photo="";
    BmobFile image = null;
    String time = "";
    Toast mToast;
   String weizhi = "";
    public void ShowToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }
    private void addByType(){
        title = edit_title.getText().toString();
        describe = edit_describe.getText().toString();
        photo = edit_photo.getText().toString();
        place = choose.getText().toString();
        time = inputDate.getText().toString();

        if(TextUtils.isEmpty(title)){
            ShowToast("请输入！");
            return;
        }
        if(TextUtils.isEmpty(describe)){
            ShowToast("喂你的号码呢");
            return;
        }
        if(TextUtils.isEmpty(photo)){
            ShowToast("要不要这么简单呀");
            return;
        }

        addLost();


    }



    private void addLost(){
        final Lost lost = new Lost();
        lost.setDescribe(describe);
        lost.setPlace(place);
        lost.setNum(num);
        lost.setPicFile(image);
       lost.setTitle(title);
        lost.setTime(time);
        lost.setWeizhi(weizhi);
        final BmobFile icon = new BmobFile(new File(imgpath));

        lost.setPicFile(icon);
        icon.upload(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub


                lost.save(addActivity.this);

                toast("" + icon.getFileUrl(addActivity.this));

            }

            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(addActivity.this, "", Toast.LENGTH_LONG).show();
            }
        });
        lost.save(this, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(addActivity.this, "成功", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                // updata(imgpath);
                finish();
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub
                setResult(RESULT_OK);

                finish();
            }
        });


    }
    public void queryAll(View view){
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.findObjects(addActivity.this, new FindListener<Lost>() {
            @Override
            public void onSuccess(List<Lost> feedBacks) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addActivity.this);
                builder.setTitle("query");
                String str = "";
                for (Lost feedBack :feedBacks){
                    str += feedBack.getTitle() + ":" +"\n";
                }
                builder.setMessage(str);
                builder.create().show();

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}




