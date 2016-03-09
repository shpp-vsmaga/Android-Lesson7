package com.shpp.sv.lesson7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.HandlerThread;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.LogRecord;

/**
 * Created by SV on 08.03.2016.
 */
public class BlinkButton extends Button {

    private static final String LOG_TAG = "svcom";
    private Handler handler = new Handler();
    private static final long REFRESH_INTERVAL = 2000;

    public BlinkButton(Context context) {
        super(context);
        log();
        createRefreshTask();
    }

    public BlinkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        log();
        createRefreshTask();
    }

    void createRefreshTask(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, REFRESH_INTERVAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        log();
        randomlyChangeColor(canvas);
    }

    private void randomlyChangeColor(Canvas canvas) {
        Random rnd = new Random();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        canvas.drawPaint(paint);
    }


    private void log() {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();

        if (trace.length > 1) {
            StackTraceElement element = trace[1];
            Log.d(LOG_TAG, this.getClass().getSimpleName() +
                    "  " + element.getMethodName() +
                    "() - executed!!!");
        }

    }
}