package com.example.my.baidu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my.baidu.Bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 4261305 on 2016/4/14.
 */
public class ZhuceActivity extends Activity implements View.OnClickListener{
    private Button regist;
    private User bean = new User();
    private EditText personal_sex_tips,personal_signature_tips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinxizhongxin);
        regist = (Button) findViewById(R.id.personal_commit);
        personal_sex_tips = (EditText) findViewById(R.id.xinxi_yonghu);
        personal_signature_tips = (EditText) findViewById(R.id.xinxi_qianming);
        regist.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personal_commit:


                bean = BmobUser.getCurrentUser(this, User.class);
                bean.setName(personal_sex_tips.getText().toString());
                bean.setSomething(personal_signature_tips.getText().toString());
                bean.update(this, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(ZhuceActivity.this, "保存成功", Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {

                    }
                });
                break;
        }

    }
}
