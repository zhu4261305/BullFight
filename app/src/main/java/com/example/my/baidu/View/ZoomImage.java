package com.example.my.baidu.View;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;


/**
 * Created by 4261305 on 2016/3/3.
 */
public class ZoomImage extends ImageView implements OnGlobalLayoutListener,OnScaleGestureListener,OnTouchListener {
    private boolean mOnce;
    //初始化时，缩放的值
    private float mInitScale;
    //双击放大时到达的值
    private float mMidScale;
    //放大的最大 值
    private float mMaxScale;
    private Matrix mScaleMatirx;
    private ScaleGestureDetector scaleGestureDetector;
    //自由移动
    //记录上一次多点触控的数量
    private int mLastPointerCount;
    //记录最后中心点的位置
    private float mLastX, mLastY;
    //拿系统默认值
    private int mTouchSlop;
    private boolean isCanDrag;

    private final float[] matrixValues = new float[9];

    public ZoomImage(Context context) {
        this(context, null);
    }

    public ZoomImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleMatirx = new Matrix();
        setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            //得到控件宽高
            int widht = getWidth();
            int hright = getHeight();
            Drawable d = getDrawable();
            if (d == null)
                return;
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            //如果图片的宽大于控件宽，但是高小于控件，缩小
            if (dw > widht && dh < hright) {
                scale = widht * 1.0f / dw;
            }
            if (dh > hright && dw < widht) {
                scale = hright * 1.0f / dh;
            }
            if ((dw > widht && dh > hright) || (dw < widht && dh < hright)) {
                scale = Math.min(widht * 1.0f / dw, hright * 1.0f / dw);
            }
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;
            //移动图片
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;
            mScaleMatirx.postTranslate(dx, dy);
            mScaleMatirx.postScale(mInitScale, mInitScale, widht / 2, hright / 2);
            setImageMatrix(mScaleMatirx);


            mOnce = true;
        }

    }

    //获取当前图片的缩放值
    public final float getScale() {
        mScaleMatirx.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    } 

    @Override
    public boolean onScale(ScaleGestureDetector Detector) {
        float scale = getScale();

        float scaleFactor = Detector.getScaleFactor();
        if (getDrawable() == null)
            return true;
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            mScaleMatirx.postScale(scaleFactor, scaleFactor, Detector.getFocusX(), Detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatirx);

        }
        return true;
    }

    //获得图片放大缩小以后的
    private RectF getMatrixRectf() {
        Matrix matrix = mScaleMatirx;
        RectF rectf = new RectF();
        Drawable D = getDrawable();
        if (D != null) {
            rectf.set(0, 0, D.getIntrinsicWidth(), D.getIntrinsicHeight());
            matrix.mapRect(rectf);


        }
        return rectf;
    }

    //在缩放的时候进行控制
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectf();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        //如果宽度或者高度小于控件的宽高，让其居中
        if (rect.width() < width) {
            deltaX = width / 2f - rect.right + rect.width() / 2f;
        }
        if (rect.height() < height) {
            deltaY = height / 2f - rect.bottom + rect.height() / 2f;
        }
        mScaleMatirx.postTranslate(deltaX, deltaY);

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent Event) {
        scaleGestureDetector.onTouchEvent(Event);
        float x = 0;
        float y = 0;
        //拿到多点触控的数量
        int pointerCount = Event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += Event.getX(i);
            y += Event.getY(i);

        }
        x /= pointerCount;
        y /= pointerCount;
        if (mLastPointerCount != pointerCount) {
            mLastX = x;
            mLastY = y;

        }
        mLastPointerCount = pointerCount;
        switch (Event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag){
                    isCanDrag = isMoveAction(dx, dy);

                }
                if (isCanDrag) {
               RectF rectF = getMatrixRectf();
                    if (getDrawable() != null){
                        if (rectF.width()<getWidth()){
                            dx = 0;

                        }
                        if (rectF.height()<getHeight()){
                            dy = 0;
                        }
                        mScaleMatirx.postTranslate(dx,dy);
                        checkBorderWhenTranscale();
                        setImageMatrix(mScaleMatirx);
                    }
                }
                mLastY = y;
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0 ;
                break;

        }
        return true;
    }

    private void checkBorderWhenTranscale() {
    }

    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx*dx + dy*dy)>mTouchSlop;
    }



}

