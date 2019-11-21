package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class FakeKeyView extends View {

    private Paint paint;


    int orientation;

    private float smallCenterX = 120, smallCenterY = 120, smallCenterR = 5;
    private float BigCenterX = 120, BigCenterY = 120, BigCenterR = 40;


    public FakeKeyView(Context context, int screenWidth, int screenHeight) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        smallCenterR = BigCenterR / 2;
        getRad(BigCenterX, BigCenterY, 80, 120);
        getRad(BigCenterX, BigCenterY, 160, 120);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(BigCenterX, BigCenterY, BigCenterR, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(smallCenterX, smallCenterY, smallCenterR, paint);
    }

    //计算弧长
    double getRad(float px1, float py1, float px2, float py2) {
        //得到两点X的距离
        float dx = px2 - px1;
        //得到两点Y的距离
        float dy = py1 - py2;
        float c = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        float cosAngle = dx / c;
        float rad = (float) Math.acos(cosAngle);

        /*if (py2 < py1) {
            rad = -rad;
        }*/
        return rad;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("view", "rad:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                smallCenterX = 120;
                smallCenterY = 120;
                orientation = 0;
                break;
            default:
                double red = getRad(BigCenterX, BigCenterY, event.getX(), event.getY());
                if (red < 3.1415927 / 4.0) {
                    smallCenterX = 160;
                    smallCenterY = 120;
                    orientation = 3;
                } else if (red < (3.1415927 / 4.0) * 3) {
                    smallCenterX = 120;
                    if (event.getY() > BigCenterY) {
                        smallCenterY = 160;
                        orientation = 0;
                    } else {
                        smallCenterY = 80;
                        orientation = 2;
                    }
                } else {
                    smallCenterX = 80;
                    smallCenterY = 120;
                    orientation = 1;
                }
                break;
        }
        invalidate();
        return true;
    }
}
