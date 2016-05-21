package com.example.my.baidu.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 4261305 on 2016/2/26.
 */
public class SiderBar extends View {
    public SiderBar(Context context) {
        super(context);
    }

    public SiderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private Paint paint  = new Paint();
    private static String[] sideBar ={"热门","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V"};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setTextSize(40);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        int height = getHeight();
        int widht = getWidth();
        int each_height = height/sideBar.length;
        for (int i =0 ; i<sideBar.length ; i++){
            float x = widht/2 -paint.measureText(sideBar[i])/2;
            float y =(1+i)*each_height;
            canvas.drawText(sideBar[i],x,y,paint);
        }
    }
}

