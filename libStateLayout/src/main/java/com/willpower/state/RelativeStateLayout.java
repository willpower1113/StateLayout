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
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.willpower.state.R;
import com.willpower.state.utils.Utils;

/**
 * 相对布局
 */
public class RelativeStateLayout extends RelativeLayout implements IState{
    /**
     * 属性
     */
    int textSize;
    int textColor;
    int state;

    @DrawableRes
    int resource;
    String message;

    Paint paint;

    boolean withAnimator = false;

    /**
     * 事件
     */
    OnClickListener listener;

    int[] ATTRS = new int[]{android.R.attr.textSize, android.R.attr.textColor};

    public RelativeStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public RelativeStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttribute(attrs);
        setWillNotDraw(false);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        this.post(new Runnable() {
            @Override
            public void run() {
                changeState();
            }
        });
    }

    private void initAttribute(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, ATTRS);
        textSize = a.getDimensionPixelSize(0, Utils.sp2px(getContext(), 14));
        textColor = a.getColor(1, Color.GRAY);
        a.recycle();

        a = getContext().obtainStyledAttributes(attrs, R.styleable.StateLayout);
        state = a.getInt(R.styleable.StateLayout_state, CONTENT);
        a.recycle();
    }

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
                baseLineY + Utils.screenHeight(getContext()) / 2 + textSize * 2, paint);
    }

    /**
     * 绘制图标
     *
     * @param canvas
     */
    private void drawResource(Canvas canvas) {
        Rect rect = new Rect(Utils.screenWidth(getContext()) / 2 - Utils.dp2px(getContext(),30),
                Utils.screenHeight(getContext()) / 3 - Utils.dp2px(getContext(),30),
                Utils.screenWidth(getContext()) / 2 + Utils.dp2px(getContext(),30),
                Utils.screenHeight(getContext()) / 3 + Utils.dp2px(getContext(),30));
        canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), resource), null, rect, paint);
    }

    /**
     * State -> Empty
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void empty() {
        hideContent();
        this.message = "没有数据！";
        this.resource = R.drawable.icon_empty;
        postInvalidate();
    }

    public void empty(String msg) {
        hideContent();
        this.message = msg;
        this.resource = R.drawable.icon_empty;
        postInvalidate();
    }

    public void empty(OnClickListener refresh) {
        this.listener = refresh;
        this.setClickable(true);
        this.setOnClickListener(realListener);
        empty();
    }

    public void empty(String msg, OnClickListener refresh) {
        this.listener = refresh;
        this.setClickable(true);
        this.setOnClickListener(realListener);
        empty(msg);
    }

    /**
     * State -> Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void error() {
        hideContent();
        this.message = "数据加载失败！";
        this.resource = R.drawable.icon_error;
        postInvalidate();
    }

    public void error(String errorMsg) {
        hideContent();
        this.message = errorMsg;
        this.resource = R.drawable.icon_error;
        postInvalidate();
    }

    public void error(OnClickListener refresh) {
        this.listener = refresh;
        this.setClickable(true);
        this.setOnClickListener(realListener);
        error();
    }

    public void error(String errorMsg, OnClickListener refresh) {
        this.listener = refresh;
        this.setClickable(true);
        this.setOnClickListener(realListener);
        error(errorMsg);
    }

    /**
     * State -> Net_Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void netError() {
        hideContent();
        this.message = "网络异常，请检查网络连接！";
        this.resource = R.drawable.icon_net_error;
        postInvalidate();
    }

    public void netError(String errorMsg) {
        hideContent();
        this.message = errorMsg;
        this.resource = R.drawable.icon_net_error;
        postInvalidate();
    }

    public void netError(OnClickListener refresh) {
        this.listener = refresh;
        this.setClickable(true);
        this.setOnClickListener(realListener);
        netError();
    }

    public void netError(String errorMsg, OnClickListener refresh) {
        this.listener = refresh;
        this.setClickable(true);
        this.setOnClickListener(realListener);
        netError(errorMsg);
    }

    /**
     * State => Content
     * 正常界面显示
     */
    public void content() {
        showContent();
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

    private void hideContent() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null) {
                view.setVisibility(GONE);
            }
        }
    }

    private void showContent() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null) {
                view.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * 真实‘点击回调接口’,在 ‘listener’外部嵌套
     */

    long realTime = 0;

    int rate = 1000;

    OnClickListener realListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if ((System.currentTimeMillis() - rate) >= 1000 && isClickable()){
                realTime = System.currentTimeMillis();
                listener.onClick(view);
            }
        }
    };
}
