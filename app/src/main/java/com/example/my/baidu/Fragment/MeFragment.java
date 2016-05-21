package com.example.my.baidu.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my.baidu.Bean.User;
import com.example.my.baidu.LoginActivity;
import com.example.my.baidu.R;
import com.example.my.baidu.SheActivity;
import com.example.my.baidu.Utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;

/**
 * Created by 4261305 on 2016/2/26.
 */
public class MeFragment extends Fragment implements View.OnClickListener{
    @ViewInject(R.id.user_name_navi)
    private TextView user_name_navi;
    @ViewInject(R.id.tv_add)
    private TextView tv_add;

    @ViewInject(R.id.shezhi)
    private LinearLayout shezhi;
    @ViewInject(R.id.user_logo_navi)
    private ImageView user_logo_navi;

    private static String path="/sdcard/myHead/";//sd路径
    private static final String TAG = "bmob";
    @ViewInject(R.id.one)
    private ImageView one;

    private Bitmap bitmap = null;









    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part3, null);


        ViewUtils.inject(this, view);


        intData();




        return view;


    }









    private void intData() {
        tv_add.setOnClickListener(this);
        shezhi.setOnClickListener(this);
        Bitmap bitmap = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bitmap != null) {
           user_logo_navi.setImageBitmap(bitmap);
        }
        User user = BmobUser.getCurrentUser(getContext(), User.class);
        if (user != null){
            user_name_navi.setText(user.getName());
        }
        BmobUser bmobUser = BmobUser.getCurrentUser(getContext());
        if (bmobUser != null) {
            String name = bmobUser.getUsername();
            String email = bmobUser.getEmail();
            LogUtils.i(TAG, "username:" + name + ",email:" + email);
            tv_add.setVisibility(View.GONE);

        }


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.shezhi:
                startActivityForResult(new Intent(getActivity(), SheActivity.class), 1);
                break;
            default:
                break;

        }
    }



}
