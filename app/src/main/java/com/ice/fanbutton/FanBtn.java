package com.ice.fanbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ice on 2016/3/26.
 */
public class FanBtn extends View {

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 档位
     */
    private int level;
    /**
     * 基本显示颜色
     */
    private int indicatorColor;
    /**
     * 内圆盘半径
     */
    private int radius;

    private int levelFlag = 1;


    private Paint paint;
    private OnDownActionListener mDown = null;

    public FanBtn(Context context) {
        this(context, null);
    }

    public FanBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FanBtn, defStyleAttr, 0);

        level = a.getInt(R.styleable.FanBtn_level, 1);//默认1档
        indicatorColor = a.getColor(R.styleable.FanBtn_indicatorColor, Color.RED);
        radius = (int) a.getDimension(R.styleable.FanBtn_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        Log.e("log", "level   " + level + "indicatorColor " + indicatorColor + "  radius  " + radius);

        a.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        /**
         * 画最外层的大圆环
         */
        paint.reset();
        int center = getWidth() / 2; //获取圆心的x坐标
        paint.setColor(Color.WHITE); //设置圆环的颜色
//        paint.setStyle(Paint.Style.STROKE); //设置空心
//        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(center, center, radius, paint); //画出圆环

        /**
         * 画圆弧 ，画圆环的进度
         */
        int margin = 10 * getResources().getDisplayMetrics().densityDpi / 160;
        Log.e("log", "margin   " + margin);
        paint.setStrokeWidth(8); //设置圆环的宽度

        RectF oval = new RectF(center - radius - margin, center - radius - margin, center
                + radius + margin, center + radius + margin);  //用于定义的圆弧的形状和大小的界限

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 120, 300, false, paint);  //根据进度画圆弧
        paint.setColor(indicatorColor);  //设置进度的颜色
        canvas.drawArc(oval, 120, 100 * level, false, paint);  //根据进度画圆弧

        /**
         * 画圆点
         */
        int point_x = (int) (center + (radius - margin) * Math.cos(Math.PI * 4 / 3 - Math.PI * level * 5 / 9));
        int point_y = (int) (center - (radius - margin) * Math.sin(Math.PI * 4 / 3 - Math.PI * level * 5 / 9));

        canvas.drawPoint(point_x, point_y, paint);

    }


    //    private int xDown, xUp;
// 为每个接口设置监听器
    public void setOnDownActionListener(OnDownActionListener down) {
        mDown = down;
    }


    // 定义三个接口
    public interface OnDownActionListener {
        public void OnDown();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:

                level = level + levelFlag;
                if (level >= 3)
                    levelFlag = -1;
                if (level <= 1)
                    levelFlag = 1;
                //  postInvalidate();  //重绘
                invalidate();
                mDown.OnDown();

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }

        return true;
    }
}
