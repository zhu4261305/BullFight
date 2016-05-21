package com.example.my.baidu;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.my.baidu.View.ZoomImage;

/**
 * Created by 4261305 on 2016/5/3.
 */
public class NewActivity extends Activity {
    private ZoomImage zoomImage;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zixun);
        zoomImage = (ZoomImage) findViewById(R.id.zoom);


        String imagePath = getIntent().getStringExtra("image_path");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        zoomImage.setImageBitmap(bitmap);

        final ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(700);
        zoomImage.startAnimation(animation);


    }

    protected void onDestroy() {
        super.onDestroy();
        // 将Bitmap对象回收掉
        if (bitmap != null) {
            bitmap.recycle();
        }
    }



}
