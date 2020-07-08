package com.willpower.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.willpower.state.R;
import com.willpower.state.utils.Utils;

/**
 * ViewGroup
 */
public class StateLayout extends ViewGroup implements IState , GestureDetector.OnGestureListener {
    @DrawableRes
    int resource;
    String message;

    Paint paint;

    GestureDetector detector;

    boolean withAnimator = false;

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
        detector = new GestureDetector(getContext(),this);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(Utils.sp2px(getContext(),getContext().getResources().getInteger(R.integer.text_size)));
        paint.setColor(getContext().getResources().getColor(R.color.text_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!TextUtils.isEmpty(message)) {
            drawMessage(canvas);
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
        Log.d("StateLayout", "绘制文字: ");
        // 文字宽
        float textWidth = paint.measureText(message);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(paint.ascent() + paint.descent()) / 2;
        //居中
        canvas.drawText(message, (Utils.screenWidth(getContext()) - textWidth) / 2,
                baseLineY + Utils.screenHeight(getContext()) / 2, paint);

        if (listener!=null)drawTips(canvas);
    }

    /**
     * 绘制 '点击刷新'
     */
    private void drawTips(Canvas canvas){
        String tips = "点击屏幕刷新";
        // 文字宽
        float textWidth = paint.measureText(tips);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(paint.ascent() + paint.descent()) / 2;
        //居下
        canvas.drawText(tips, (Utils.screenWidth(getContext()) - textWidth) / 2,
                baseLineY + Utils.screenHeight(getContext()) / 2 + paint.getTextSize() * 2, paint);
    }

    /**
     * 绘制图标
     *
     * @param canvas
     */
    private void drawResource(Canvas canvas) {
        Rect rect = new Rect(Utils.screenWidth(getContext()) / 2 - Utils.px2dp(getContext(),128),
                Utils.screenHeight(getContext()) / 3 - Utils.px2dp(getContext(),128),
                Utils.screenWidth(getContext()) / 2 + Utils.px2dp(getContext(),128),
                Utils.screenHeight(getContext()) / 3 + Utils.px2dp(getContext(),128));
        canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), resource), null, rect, paint);
    }

    /**
     * State -> Empty
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void empty() {
        this.message = "没有数据！";
        this.resource = R.drawable.icon_empty;
        postInvalidate();
    }

    public void empty(String msg) {
        this.message = msg;
        this.resource = R.drawable.icon_empty;
        postInvalidate();
    }

    public void empty(OnClickListener refresh) {
        this.listener = refresh;
        empty();
    }

    public void empty(String msg, OnClickListener refresh) {
        this.listener = refresh;
        empty(msg);
    }

    /**
     * State -> Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void error() {
        this.message = "数据加载失败！";
        this.resource = R.drawable.icon_error;
        postInvalidate();
    }

    public void error(String errorMsg) {
        this.message = errorMsg;
        this.resource = R.drawable.icon_error;
        postInvalidate();
    }

    public void error(OnClickListener refresh) {
        this.listener = refresh;
        error();
    }

    public void error(String errorMsg, OnClickListener refresh) {
        this.listener = refresh;
        error(errorMsg);
    }

    /**
     * State -> Net_Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void netError() {
        this.message = "网络异常，请检查网络连接！";
        this.resource = R.drawable.icon_net_error;
        postInvalidate();
    }

    public void netError(String errorMsg) {
        this.message = errorMsg;
        this.resource = R.drawable.icon_net_error;
        postInvalidate();
    }

    public void netError(OnClickListener refresh) {
        this.listener = refresh;
        netError();
    }

    public void netError(String errorMsg, OnClickListener refresh) {
        this.listener = refresh;
        netError(errorMsg);
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

    /**
     * 点击事件处理
     */

    long realTime = 0;

    int rate = 1000;

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if ((System.currentTimeMillis() - rate) >= 1000 && listener!=null){
            realTime = System.currentTimeMillis();
            listener.onClick(this);
        }
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
}
