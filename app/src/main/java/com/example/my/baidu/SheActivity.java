package com.example.my.baidu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.baidu.Bean.User;
import com.example.my.baidu.Utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 4261305 on 2016/4/13.
 */
public class SheActivity extends Activity implements View.OnClickListener{
  private static final String TAG = "bmob";
    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径
    String imgpath =path+"/head.jpg" ;
    private User bean = new User();

    @ViewInject(R.id.mysel)
    private TextView mysel;
    @ViewInject(R.id.user_nick)
    private LinearLayout user_nick;
    @ViewInject(R.id.user_nick_text)
    private TextView user_nick_text;

    Dialog dialog;
    @ViewInject(R.id.user_icon)
    private RelativeLayout user_icon;
    @ViewInject(R.id.user_icon_image)
    private ImageView user_icon_image;
    @ViewInject(R.id.bt1)
    private Button mBt1;//拍照
    @ViewInject(R.id.bt2)
    private Button mBt2;//相册
    @ViewInject(R.id.back)
    private Button back;
    @ViewInject(R.id.user_logout)
    private TextView user_logout;
    private String[] companyList = null;
    private boolean[] defSel={false,false,false,false,false,false};
    String weizhi = "";










    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

         ViewUtils.inject(this);
        mysel.setOnClickListener(new OnClickListenerImp());
        companyList=SheActivity.this.getResources().getStringArray(R.array.languages);

         initView();




    }


    public class OnClickListenerImp implements View.OnClickListener {

        public void onClick(View arg0) {
            SheActivity.this.mysel.setText("");


            Dialog dialog=new AlertDialog.Builder(SheActivity.this)
                    .setTitle("选择位置")
                    .setIcon(R.drawable.ic_launcher)

                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setMultiChoiceItems(R.array.languages, defSel, new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                           for (int i = 0 ; i<companyList.length; i++){
                               if (i == which && isChecked){
                                   mysel.setText(companyList[which]);
                                   weizhi = mysel.getText().toString();
                               }
                           }
                        }
                    })
                    .create();
            dialog.show();
        }
    }


    private void initView() {
        user_icon.setOnClickListener(this);
        user_nick.setOnClickListener(this);
        BmobUser bmobUser = BmobUser.getCurrentUser(SheActivity.this);
        if (bmobUser != null) {
            String name = bmobUser.getUsername();
            String email = bmobUser.getEmail();
            LogUtils.i(TAG, "username:" + name + ",email:" + email);
            user_nick_text.setText("已登录");
        }else {
            user_nick_text.setText("请登录");
        }

            Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
            if (bt != null) {
                @SuppressWarnings("deprecation")
                Drawable drawable = new BitmapDrawable(bt);//转换成drawable
                user_icon_image.setImageDrawable(drawable);
            } else {
                /**
                 *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
                 *
                 */
            }


        }
    public void user_logout(View v){
        showName();

        final BmobFile icon = new BmobFile(new File(imgpath));
        icon.upload(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                User person = new User();
                person.setPicFile(icon);
                person.save(SheActivity.this);

                toast("上传文件成功:" + icon.getFileUrl(SheActivity.this));

            }

            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(SheActivity.this,"上传失败",Toast.LENGTH_LONG).show();
            }
        });

    finish();
    }

    private void toast(String s) {
    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_icon:
                showDialog();
                break;

            case R.id.user_nick:
                startActivity(new Intent(SheActivity.this, ChangActivity.class));

            default:
                break;



        }

    }

    private void showName() {
        bean = BmobUser.getCurrentUser(this, User.class);

        bean.setWeizhi(weizhi);
        bean.update(this, new UpdateListener() {

            @Override
            public void onSuccess() {


            }

            @Override
            public void onFailure(int arg0, String arg1) {

            }
        });
    }


    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
                null);
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void on_click(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));
                startActivityForResult(intent2, 2);//采用ForResult打开

                break;
            case R.id.bt2:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                break;
            case R.id.back:
                dialog.cancel();
                break;
            default:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        user_icon_image.setImageBitmap(head);//用ImageView显示出来
                        dialog.cancel();
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    };
    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}


