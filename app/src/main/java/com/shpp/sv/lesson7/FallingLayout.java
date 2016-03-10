package com.shpp.sv.lesson7;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.HashMap;


public class FallingLayout extends FrameLayout {
    private boolean animated = false;
    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;
    private ArrayList<View> views = new ArrayList<View>();
    private RotateAnimation rotateLeft;
    private RotateAnimation rotateRight;
    private AnimationSet set;
    private final static int PIVOT_Y = -600;
    private final static int ROTATE_DEGREE = 10;
    private final static long DURATION = 3000;
    private final static long RETURN_DURATION = 100;
    private final static int ROTATE_REPEAT_COUNT = 6;
    private HashMap<Integer, ObjectAnimator> animatorFallMap;
    private HashMap<Integer, ObjectAnimator> animatorBackHomelMap;


    public FallingLayout(final Context context) {
        super(context);
        init();
    }

    public FallingLayout(final Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    private void init() {
        animatorFallMap = new HashMap<>();
        animatorBackHomelMap = new HashMap<>();

        /*Init listeners*/
        clickListener = createClickListener();
        longClickListener = createLongClickListener();
        this.setOnClickListener(clickListener);
        this.setOnLongClickListener(longClickListener);

        /*Init animation*/
        rotateLeft = new RotateAnimation(0, -ROTATE_DEGREE,
                Animation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateLeft.setRepeatCount(ROTATE_REPEAT_COUNT - 1);

        rotateRight = new RotateAnimation(ROTATE_DEGREE, 0,
                Animation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateRight.setRepeatCount(ROTATE_REPEAT_COUNT);

        set = new AnimationSet(true);
        set.setAnimationListener(createAnimationListener());
        set.addAnimation(rotateLeft);
        set.addAnimation(rotateRight);
        set.setDuration(DURATION / ROTATE_REPEAT_COUNT);
        set.setFillAfter(true);
        set.setRepeatMode(Animation.REVERSE);
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
                animateLikePendulum(view);
                animateFall(view);
            }
        }
        animated = true;

    }

    private void animateLikePendulum(View view) {
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
        int viewHashCode = view.hashCode();

        if(!animatorFallMap.containsKey(viewHashCode)) {
            ObjectAnimator animatorFall = ObjectAnimator.ofFloat(view, "translationY", 0,
                    this.getHeight() - view.getBottom());

            animatorFall.setDuration(DURATION);
            animatorFallMap.put(viewHashCode, animatorFall);
        }

        animatorFallMap.get(viewHashCode).start();
    }

    private void animateBackHome(View view) {
        int viewHashCode = view.hashCode();

        if (!animatorBackHomelMap.containsKey(viewHashCode)) {
            ObjectAnimator animatorBackHome = ObjectAnimator.ofFloat(view, "translationY",
                    this.getHeight() - view.getBottom(), 0);
            animatorBackHome.setDuration(RETURN_DURATION);
            animatorBackHomelMap.put(viewHashCode, animatorBackHome);
        }

        animatorBackHomelMap.get(viewHashCode).start();
    }

}
