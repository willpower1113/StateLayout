package com.willpower.state.utils;

import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.willpower.state.R;
import com.willpower.state.animator.IProgress;
import com.willpower.state.animator.ProgressCircleFlower;
import com.willpower.state.animator.TransitionAnimation;
import com.willpower.state.model.IState;

/**
 * 处理状态变化 的辅助类
 *
 * @param <T>
 */
public class StateChangeHelper<T extends ViewGroup> implements IState {
    /**
     * 属性
     */
    int textSize;
    int textColor;
    int state;

    @DrawableRes
    int resource;
    String message;
    int iconSize;
    int progressSize;

    Paint paint;

    IProgress progress;

    boolean withAnimator;

    T target;

    public StateChangeHelper(@NonNull T target) {
        this.target = target;
        this.target.setWillNotDraw(false);
    }

    public void release() {
        hideLoading();
        this.target = null;
    }

    /**
     * 事件
     */
    View.OnClickListener listener;

    int[] ATTRS = new int[]{android.R.attr.textSize, android.R.attr.textColor};

    /**
     * 初始化配置
     *
     * @param attrs
     */
    public void initAttribute(@Nullable AttributeSet attrs) {
        TypedArray a = this.target.getContext().obtainStyledAttributes(attrs, ATTRS);
        textSize = a.getDimensionPixelSize(0, this.target.getContext().getResources().getDimensionPixelSize(R.dimen.text_size));
        textColor = a.getColor(1, this.target.getContext().getResources().getColor(R.color.text_color));
        a.recycle();

        a = this.target.getContext().obtainStyledAttributes(attrs, R.styleable.StateLayout);
        state = a.getInt(R.styleable.StateLayout_state, CONTENT);
        iconSize = a.getDimensionPixelSize(R.styleable.StateLayout_iconSize, this.target.getContext().getResources().getDimensionPixelSize(R.dimen.icon_size));
        progressSize = a.getDimensionPixelSize(R.styleable.StateLayout_progressSize, this.target.getContext().getResources().getDimensionPixelSize(R.dimen.progress_size));
        withAnimator = a.getBoolean(R.styleable.StateLayout_withAnimator, this.target.getContext().getResources().getBoolean(R.bool.animator));

        a.recycle();
        this.target.post(new Runnable() {
            @Override
            public void run() {
                changeState();
            }
        });
    }

    /**
     * 初始化 Paint
     */
    public void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
    }

    /**
     * 根据需求 改变初始状态
     */
    private void changeState() {
        switch (state) {
            case CONTENT:
                content();
                break;
            case ERROR:
                error();
                break;
            case EMPTY:
                empty();
                break;
            case NET_ERROR:
                netError();
                break;
        }
    }

    @Override
    public void drawContent(Canvas canvas) {
        if (!TextUtils.isEmpty(message)) {
            drawMessage(canvas);
        }
        if (progress != null) {
            drawLoading(canvas);
            return;
        }
        if (resource != 0) {
            drawResource(canvas);
        }
    }

    /**
     * 绘制提示语
     *
     * @param canvas
     */
    private void drawMessage(Canvas canvas) {
        // 文字宽
        float textWidth = paint.measureText(message);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(paint.ascent() + paint.descent()) / 2;
        //居中
        canvas.drawText(message, (this.target.getWidth() - textWidth) / 2,
                baseLineY + this.target.getHeight() / 2, paint);

        if (listener != null) drawTips(canvas);
    }

    /**
     * 绘制 '点击刷新'
     */
    private void drawTips(Canvas canvas) {
        String tips = "点击屏幕刷新";
        // 文字宽
        float textWidth = paint.measureText(tips);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(paint.ascent() + paint.descent()) / 2;
        //居下
        canvas.drawText(tips, (this.target.getWidth() - textWidth) / 2,
                baseLineY + this.target.getHeight() / 2 + textSize * 2, paint);
    }

    /**
     * 绘制图标
     *
     * @param canvas
     */
    private void drawResource(Canvas canvas) {
        Rect rect = new Rect(this.target.getWidth() / 2 - iconSize,
                this.target.getHeight() / 3 - iconSize,
                this.target.getWidth() / 2 + iconSize,
                this.target.getHeight() / 3 + iconSize);
        canvas.drawBitmap(BitmapFactory.decodeResource(this.target.getContext().getResources(), resource), null, rect, paint);
    }

    /**
     * 绘制加载动画
     */
    private void drawLoading(Canvas canvas) {
        progress.drawProgress(canvas, progressSize, new int[]{this.target.getWidth() / 2, this.target.getHeight() / 3});
        progress.showProgress();
    }

    /**
     * State -> Empty
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void empty() {
        empty("没有数据！", null);
    }

    public void empty(String msg) {
        empty(msg, null);
    }

    public void empty(View.OnClickListener refresh) {
        empty("没有数据！", refresh);
    }

    public void empty(final String msg, final View.OnClickListener refresh) {
        this.target.post(new Runnable() {
            @Override
            public void run() {
                hideContent();
                hideLoading();
                StateChangeHelper.this.listener = refresh;
                StateChangeHelper.this.message = msg;
                StateChangeHelper.this.resource = R.drawable.icon_empty;
                if (StateChangeHelper.this.listener != null)
                    StateChangeHelper.this.target.setOnClickListener(realListener);
                StateChangeHelper.this.target.postInvalidate();
            }
        });
    }

    /**
     * State -> Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void error() {
        error("数据加载失败！", null);
    }

    public void error(String errorMsg) {
        error(errorMsg, null);
    }

    public void error(View.OnClickListener refresh) {
        error("数据加载失败！", refresh);
    }

    public void error(final String errorMsg, final View.OnClickListener refresh) {
                hideContent();
                hideLoading();
                StateChangeHelper.this.listener = refresh;
                StateChangeHelper.this.message = errorMsg;
                StateChangeHelper. this.resource = R.drawable.icon_error;
                if (StateChangeHelper.this.listener != null)
                    StateChangeHelper.this.target.setOnClickListener(realListener);
                StateChangeHelper.this.target.postInvalidate();
    }

    /**
     * State -> Net_Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void netError() {
        netError("网络异常，请检查网络连接！", null);
    }

    public void netError(String errorMsg) {
        netError(errorMsg, null);
    }

    public void netError(View.OnClickListener refresh) {
        netError("网络异常，请检查网络连接！", refresh);
    }

    public void netError(final String errorMsg, final View.OnClickListener refresh) {
                hideContent();
                hideLoading();
                StateChangeHelper.this.listener = refresh;
                StateChangeHelper.this.message = errorMsg;
                StateChangeHelper.this.resource = R.drawable.icon_net_error;
                if (StateChangeHelper.this.listener != null)
                    StateChangeHelper.this.target.setOnClickListener(realListener);
                StateChangeHelper.this.target.postInvalidate();
    }

    @Override
    public void loading() {
        loading("努力加载中...");
    }

    @Override
    public void loading(String msg) {
        loading(msg, new ProgressCircleFlower(changeListener));
    }

    @Override
    public void loading(IProgress iProgress) {
        loading("努力加载中...", iProgress);
    }

    @Override
    public void loading(final String msg, final IProgress iProgress) {
                hideContent();
                StateChangeHelper.this.listener = null;
                StateChangeHelper.this.message = msg;
                StateChangeHelper.this.progress = iProgress;
                StateChangeHelper.this.target.postInvalidate();
    }

    @Override
    public void hideLoading() {
        if (this.progress != null)
            this.progress.hideProgress();
        this.progress = null;
    }

    /**
     * State => Content
     * 正常界面显示
     */
    public void content() {
                hideLoading();
                StateChangeHelper.this.listener = null;
                StateChangeHelper.this.resource = 0;
                StateChangeHelper.this.message = null;
                StateChangeHelper.this.target.postInvalidate();
                showContent();
    }

    @Override
    public void withAnimator(boolean with) {
        this.withAnimator = with;
    }

    @Override
    public boolean isWithAnimator() {
        return this.withAnimator;
    }

    private void hideContent() {
        for (int i = 0; i < this.target.getChildCount(); i++) {
            View view = this.target.getChildAt(i);
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
        if (isWithAnimator()) {
            TransitionAnimation.IN(this.target);
        } else {
            this.target.setVisibility(View.VISIBLE);
        }
    }

    private void showContent() {
        for (int i = 0; i < this.target.getChildCount(); i++) {
            View view = this.target.getChildAt(i);
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 加载条对外提供的更新回调
     */
    IProgress.OnProgressChangeListener changeListener = new IProgress.OnProgressChangeListener() {
        @Override
        public void viewPostInvalidate() {
            StateChangeHelper.this.target.postInvalidate();
        }
    };

    /**
     * 真实‘点击回调接口’,在 ‘listener’外部嵌套
     */

    long realTime = 0;

    final int RATE = 1000;

    View.OnClickListener realListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isMistake()) return;
            realTime = System.currentTimeMillis();
            if (listener != null)
                listener.onClick(view);
        }
    };

    /**
     * 检查误触
     *
     * @return
     */
    boolean isMistake() {
        if ((System.currentTimeMillis() - realTime) <= RATE) {
            return true;
        }
        realTime = System.currentTimeMillis();
        return false;
    }
}
