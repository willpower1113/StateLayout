package com.willpower.state.animator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

/**
 * 花瓣 - 加载条
 */
public class ProgressCircleFlower implements IProgress {

    private static final int LINE_COUNT = 12;
    private static final int DEGREE_PER_LINE = 360 / LINE_COUNT;

    private Paint mPaint;

    private int mAnimateValue = 0;

    private ValueAnimator mAnimator;

    private OnProgressChangeListener changeListener;//进度变化回调

    public ProgressCircleFlower() {
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(Color.LTGRAY);
    }

    public ProgressCircleFlower(OnProgressChangeListener listener) {
        this.changeListener = listener;
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(Color.DKGRAY);
    }

    @Override
    public void drawProgress(Canvas canvas, int mSize, int[] centerPoint) {
        int width = mSize / 12, height = mSize / 6;
        mPaint.setStrokeWidth(mSize / 12);
        canvas.translate(centerPoint[0] - mSize / 2, centerPoint[1] - mSize);
        canvas.rotate(mAnimateValue * DEGREE_PER_LINE, mSize / 2, mSize / 2);
        canvas.translate(mSize / 2, mSize / 2);
        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.rotate(DEGREE_PER_LINE);
            mPaint.setAlpha((int) (255f * (i + 1) / LINE_COUNT));
            canvas.translate(0, -mSize / 2 + width / 2);
            canvas.drawLine(0, 0, 0, height, mPaint);
            canvas.translate(0, mSize / 2 - width / 2);
        }
    }

    @Override
    public void showProgress() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1);
            mAnimator.addUpdateListener(mUpdateListener);
            mAnimator.setDuration(600);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.start();
        } else if (!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    @Override
    public void hideProgress() {
        if (mAnimator != null) {
            mAnimator.removeUpdateListener(mUpdateListener);
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    @Override
    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.changeListener = listener;
    }

    /**
     * 进度条 动画
     */
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mAnimateValue = (int) animation.getAnimatedValue();
            changeListener.viewPostInvalidate();
        }
    };
}
