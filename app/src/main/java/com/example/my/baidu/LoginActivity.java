package com.example.my.baidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.baidu.Bean.User;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText user_name_input,user_password_input;
    private Button register;
    private TextView tv_etpass,tv_xiugai,register_menu;
    private User bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part4);


        Bmob.initialize(this, "a156fa8c5e588f25aef68fc27fe38fb2");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this, "a156fa8c5e588f25aef68fc27fe38fb2");
        iniData();
    }

    private void iniData() {

        user_name_input = (EditText) findViewById(R.id.yonghuming);
        user_password_input = (EditText) findViewById(R.id.mima);
        register = (Button) findViewById(R.id.register);

        tv_etpass = (TextView) findViewById(R.id.tv_etpass);

        register_menu = (TextView) findViewById(R.id.register_menu);

        tv_xiugai = (TextView) findViewById(R.id.tv_xiugai);
        register.setOnClickListener(this);
        tv_etpass.setOnClickListener(this);
        tv_etpass.setOnClickListener(this);
        register_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_menu:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.register:
                bean = new User();
                bean.setUsername(user_name_input.getText().toString());
                bean.setPassword(user_password_input.getText().toString());
                bean.login(this, new SaveListener() {

                    @Override
                    public void onSuccess() {

                            Toast.makeText(LoginActivity.this, "登录成功",
                                    Toast.LENGTH_LONG).show();
                        finish();



                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        Toast.makeText(LoginActivity.this, "账号或密码错误",
                                Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.tv_xiugai:
                // Bmob在登录成功后会缓存
                bean = BmobUser.getCurrentUser(this, User.class);
                bean.setName("");
                bean.update(this, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this, "修改成功", Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {

                    }
                });
                break;

            case R.id.tv_etpass:
                String email = "601896769@qq.com";
                BmobUser.resetPasswordByEmail(this, email,
                        new ResetPasswordByEmailListener() {

                            @Override
                            public void onSuccess() {
                                Toast.makeText(LoginActivity.this, "验证邮箱已发送",
                                        Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int arg0, String arg1) {
                                Toast.makeText(LoginActivity.this, "更改失败",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                break;
        }
    }
}
