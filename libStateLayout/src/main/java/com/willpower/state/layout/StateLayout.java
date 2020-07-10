package com.willpower.state.layout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.willpower.state.R;
import com.willpower.state.animator.IProgress;
import com.willpower.state.animator.ProgressCircleFlower;
import com.willpower.state.model.IState;
import com.willpower.state.utils.Utils;

/**
 * ViewGroup
 */
public class StateLayout extends ViewGroup implements IState, GestureDetector.OnGestureListener {

    /**
     * 属性
     */
    @DrawableRes
    int resource;
    String message;
    int iconSize;
    int progressSize;

    Paint paint;

    GestureDetector detector;

    boolean withAnimator = false;

    IProgress progress;



    /**
     * 事件
     */
    OnClickListener listener;

    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
    }

    private void init() {
        setWillNotDraw(false);

        detector = new GestureDetector(getContext(), this);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.text_size));
        paint.setColor(getContext().getResources().getColor(R.color.text_color));
        this.progressSize = getResources().getDimensionPixelSize(R.dimen.progress_size);
        this.iconSize =  getResources().getDimensionPixelSize(R.dimen.icon_size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawContent(canvas);
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
        canvas.drawText(message, (getWidth() - textWidth) / 2,
                baseLineY + getHeight() / 2, paint);

        if (listener != null) drawTips(canvas);
    }

    /**
     * 绘制 '点击刷新'
     */
    private void drawTips(Canvas canvas) {
        String tips = "点击刷新";
        // 文字宽
        float textWidth = paint.measureText(tips);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(paint.ascent() + paint.descent()) / 2;
        //居下
        canvas.drawText(tips, (getWidth() - textWidth) / 2,
                baseLineY + getHeight() / 2 + paint.getTextSize() * 2, paint);
    }

    /**
     * 绘制图标
     *
     * @param canvas
     */
    private void drawResource(Canvas canvas) {
        Rect rect = new Rect(getWidth() / 2 - this.iconSize,
                getHeight() / 3 - this.iconSize,
                getWidth() / 2 + this.iconSize,
                getHeight() / 3 + this.iconSize);
        canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), resource), null, rect, paint);
    }

    /**
     * 绘制加载动画
     */
    private void drawLoading(Canvas canvas) {
        progress.drawProgress(canvas, progressSize, new int[]{getWidth() / 2, getHeight() / 3});
        progress.showProgress();
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

    public void empty(OnClickListener refresh) {
        empty("没有数据！", refresh);
    }

    public void empty(String msg, OnClickListener refresh) {
        hideLoading();
        this.message = msg;
        this.resource = R.drawable.icon_empty;
        postInvalidate();
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

    public void error(OnClickListener refresh) {
        error("数据加载失败！", refresh);
    }

    public void error(String errorMsg, OnClickListener refresh) {
        hideLoading();
        this.listener = refresh;
        this.message = errorMsg;
        this.resource = R.drawable.icon_error;
        postInvalidate();
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

    public void netError(OnClickListener refresh) {
        netError("网络异常，请检查网络连接！", refresh);
    }

    public void netError(String errorMsg, OnClickListener refresh) {
        hideLoading();
        this.listener = refresh;
        this.message = errorMsg;
        this.resource = R.drawable.icon_net_error;
        postInvalidate();
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
    public void loading(String msg, IProgress iProgress) {
        this.listener = null;
        this.message = msg;
        this.progress = iProgress;
        postInvalidate();
    }

    @Override
    public void hideLoading() {
        if (this.progress != null)
            this.progress.hideProgress();
        this.progress = null;
        postInvalidate();
    }


    /**
     * State => Content
     * 正常界面显示
     */
    public void content() {
        this.setClickable(false);
        this.setOnClickListener(null);
        resource = 0;
        message = null;
        postInvalidate();
    }

    @Override
    public void withAnimator(boolean with) {
        this.withAnimator = with;
    }

    @Override
    public boolean isWithAnimator() {
        return this.withAnimator;
    }

    @Override
    public void release() {
        hideLoading();
    }

    /**
     * 点击事件处理
     */

    long realTime = 0;

    final int RATE = 1000;

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (listener == null) return false;
        if (isMistake()) return false;
        listener.onClick(this);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


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

    /**
     * 加载条对外提供的更新回调
     */
    IProgress.OnProgressChangeListener changeListener = new IProgress.OnProgressChangeListener() {
        @Override
        public void viewPostInvalidate() {
            postInvalidate();
        }
    };

    public void setTextSize(int textSize) {
        this.paint.setTextSize(textSize);
        postInvalidate();
    }

    public void setTextColor(int textColor) {
        this.paint.setColor(textColor);
        postInvalidate();
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
        postInvalidate();
    }
}
