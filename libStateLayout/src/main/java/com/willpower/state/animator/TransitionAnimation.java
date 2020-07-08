package com.willpower.state.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class TransitionAnimation {

    static final long duration = 400;


    public static void IN(final View target) {
        ObjectAnimator x = ObjectAnimator.ofFloat(target, "scaleX", 0, 1);
        ObjectAnimator y = ObjectAnimator.ofFloat(target, "scaleY", 0, 1);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.playTogether(x, y);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.setVisibility(View.VISIBLE);
            }
        });
        set.start();
    }
}
