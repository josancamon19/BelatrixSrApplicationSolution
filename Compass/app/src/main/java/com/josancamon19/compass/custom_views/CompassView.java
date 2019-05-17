package com.josancamon19.compass.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {

    private Paint mPaintCircle;
    private Paint mPaintLine;
    private float direction;


    public CompassView(Context context) {
        super(context);
        init(null);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CompassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet) {
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.GRAY);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(Color.RED);
        mPaintLine.setStrokeWidth(6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        int pl = getPaddingLeft();
        int pr = getPaddingRight();
        int pt = getPaddingTop();
        int pb = getPaddingBottom();

        int usableWidth = w - (pl + pr);
        int usableHeight = h - (pt + pb);

        int radius = Math.min(usableWidth, usableHeight) / 2;
        int cx = pl + (usableWidth / 2);
        int cy = pt + (usableHeight / 2);

        canvas.drawCircle(cx, cy, radius, mPaintCircle);

        int mw = getMeasuredWidth();
        int mh = getMeasuredHeight();
        canvas.drawLine(
                mw/2,
                mh/2,
                (float)(w/2 + radius * Math.sin(-direction)),
                (float)(h/2 - radius * Math.cos(-direction)),
                mPaintLine);

    }

    public void update(float dir){
        direction = dir;
        invalidate();
    }
}
