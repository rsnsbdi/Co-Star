package com.costar.talkwithidol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class SoundLevelView extends View {
    private Drawable mGreen;
    private Drawable mRed;
    private Paint mBackgroundPaint;

    private int mHeight;
    private int mWidth;

    private int mThreshold = 0;
    private int mVol = 0;


    public SoundLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGreen = context.getResources().getDrawable(R.drawable.soundblue);
        mRed = context.getResources().getDrawable(R.drawable.soundblue);

        mWidth = mGreen.getIntrinsicWidth();
        setMinimumWidth(mWidth);

        mHeight = mGreen.getIntrinsicHeight();
        setMinimumHeight(mHeight);

        //Used to paint canvas background color
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.BLACK);

    }

    public void setLevel(int volume, int threshold) {
        if (volume == mVol && threshold == mThreshold) return;
        mVol = volume;
        mThreshold = threshold;

        // invalidate Call onDraw method and draw voice points
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Drawable bar =mGreen;

        canvas.drawPaint(mBackgroundPaint);


        for (int i = 0; i <= mVol; i++) {

            bar.setBounds((8-i) * mWidth, 0, (8 - i + 1) * mWidth, mHeight); //
           // bar.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            bar.draw(canvas);
        }
    }
}
