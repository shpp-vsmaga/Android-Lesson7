package com.shpp.sv.lesson7;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import java.util.ArrayList;


public class FallingLayout extends FrameLayout {
    private boolean animated = false;
    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;
    private ArrayList<View> views = new ArrayList<View>();
    private final static int PIVOT_Y = -600;
    private final static int ROTATE_DEGREE = 10;
    private final static long DURATION = 3000;
    private final static long RETURN_DURATION = 100;
    private final static int ROTATE_REPEAT_COUNT = 6;


    public FallingLayout(final Context context) {
        super(context);
        init();
    }

    public FallingLayout(final Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    private void init() {
        clickListener = createClickListener();
        longClickListener = createLongClickListener();
        this.setOnClickListener(clickListener);
        this.setOnLongClickListener(longClickListener);
    }

    private OnClickListener createClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                    onClickLayout();
            }
        };
    }

    private OnLongClickListener createLongClickListener() {
        return new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickLayout();
                return true;
            }
        };
    }


    private Animation.AnimationListener createAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setOnLongClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setOnLongClickListener(longClickListener);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }
    private void onClickLayout()  {
        if (!animated) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                views.add(view);
                animateFall(view);
                animateLikePendulum(view);
            }
        }
        animated = true;

    }

    private void animateLikePendulum(View view) {
        RotateAnimation rotateLeft = new RotateAnimation(0, -ROTATE_DEGREE,
                Animation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateLeft.setRepeatCount(ROTATE_REPEAT_COUNT - 1);

        RotateAnimation rotateRight = new RotateAnimation(ROTATE_DEGREE, 0,
                Animation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateRight.setRepeatCount(ROTATE_REPEAT_COUNT);

        AnimationSet set = new AnimationSet(true);
        set.setAnimationListener(createAnimationListener());
        set.addAnimation(rotateLeft);
        set.addAnimation(rotateRight);
        set.setDuration(DURATION / ROTATE_REPEAT_COUNT);
        set.setFillAfter(true);
        set.setRepeatMode(Animation.REVERSE);

        view.startAnimation(set);
    }

    private void onLongClickLayout() {
        if (animated) {
            for (View view : views) {
                animateBackHome(view);
            }
        }
        animated = false;
    }

    private void animateFall(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0,
                this.getHeight() - view.getBottom());

        animator.setDuration(DURATION);
        animator.start();
    }

    private void animateBackHome(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY",
                this.getHeight() - view.getBottom(), 0);
        animator.setDuration(RETURN_DURATION);
        animator.start();
    }

}
