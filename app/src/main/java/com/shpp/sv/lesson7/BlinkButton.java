package com.shpp.sv.lesson7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Random;

/**
 * Created by SV on 08.03.2016.
 */
public class BlinkButton extends Button {

    private static final String LOG_TAG = "svcom";
    private Handler handler = new Handler();
    private Random rnd;
    private Paint paint;
    private static final long REFRESH_INTERVAL = 2000;
    private static final long STROKE_WIDTH = 20;

    public BlinkButton(Context context) {
        super(context);
        init();
    }

    public BlinkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, REFRESH_INTERVAL);

        rnd = new Random();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        redrawButton(canvas);
    }

    private void redrawButton(Canvas canvas) {
        String text = super.getText().toString();

        /*Fill canvas with white color*/
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        /*Draw edging*/
        paint.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        /*Draw label*/
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(super.getTextSize());
        canvas.drawText(text, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
    }

}