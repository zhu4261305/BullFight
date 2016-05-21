package com.example.my.baidu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.my.baidu.Fragment.HomeFragment;
import com.example.my.baidu.Fragment.MeFragment;
import com.example.my.baidu.Fragment.findFragment;
import com.example.my.baidu.Fragment.tuanFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rd_main)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.home_tab)
    private RadioButton mRadioButton;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_content);
        ViewUtils.inject(this);
        fragmentManager =getSupportFragmentManager();
        mRadioButton.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(this);
        checkFragment(new HomeFragment(), false);

    }





    public void checkFragment(Fragment fragment , boolean isFirst) {
        FragmentTransaction tran =fragmentManager.beginTransaction();
        tran.replace(R.id.vp_container_main, fragment);
        if (!isFirst){
            tran.addToBackStack(null);
        }
        tran.commit();

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        switch (checkId){
            case R.id.home_tab:
                checkFragment(new HomeFragment(),true);
                break;
            case R.id.jieba_tab:
                checkFragment(new tuanFragment(),true);
                break;
            case R.id.find_tab:
                checkFragment(new findFragment(),true);
                break;
            case R.id.me_tab:
                checkFragment(new MeFragment(),true);
                break;
            default:
                break;
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            dialog.setTitle("警告");
            dialog.setMessage("你确定要退出当前程序？");
            dialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            dialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
