package com.example.my.baidu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.my.baidu.Bean.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 4261305 on 2016/5/18.
 */
public class ChangActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.use_login)
    private EditText use_logins;
    @ViewInject(R.id.user_logout)
    private Button user_login;
    @ViewInject(R.id.user_quxiao)
    private Button quxiao;
    private User bean = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chang);
        ViewUtils.inject(this);

        user_login.setOnClickListener(this);
        quxiao.setOnClickListener(this);
    }
    private void showName() {
        bean = BmobUser.getCurrentUser(this, User.class);


        bean.setName(use_logins.getText().toString());

        bean.update(this, new UpdateListener() {

            @Override
            public void onSuccess() {


            }

            @Override
            public void onFailure(int arg0, String arg1) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_logout:
                showName();
                finish();
                break;
            case R.id.user_quxiao:
                finish();
        }
    }
}
