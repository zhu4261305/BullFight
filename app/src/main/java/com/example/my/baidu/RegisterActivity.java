package com.example.my.baidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my.baidu.Bean.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 4261305 on 2016/3/24.
 */
public class RegisterActivity extends Activity  implements View.OnClickListener{

    private Button btn_register;
    private EditText et_name, et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re);

        btn_register = (Button) findViewById(R.id.btn_register);
        et_name = (EditText) findViewById(R.id.et_name);
        et_pass = (EditText) findViewById(R.id.et_pass);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        User bean = new User();
        switch (view.getId()){
            case R.id.btn_register:


        bean.setUsername(et_name.getText().toString());
        bean.setPassword(et_pass.getText().toString());
        bean.signUp(RegisterActivity.this, new SaveListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册成功",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this,ZhuceActivity.class));

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Toast.makeText(RegisterActivity.this, "注册失败",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

    }
