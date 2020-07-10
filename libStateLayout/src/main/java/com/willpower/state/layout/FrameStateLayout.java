package com.willpower.state.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.willpower.state.model.IModel;
import com.willpower.state.utils.StateChangeHelper;
import com.willpower.state.animator.IProgress;

/**
 * 帧布局
 */
public class FrameStateLayout extends FrameLayout implements IModel {

    public FrameStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public FrameStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    StateChangeHelper helper;

    private void init(AttributeSet attrs) {
        helper = new StateChangeHelper<>(this);
        helper.initAttribute(attrs);
        helper.initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        helper.drawContent(canvas);
    }

    /**
     * State -> Empty
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void empty() {
        helper.empty();
    }

    public void empty(String msg) {
        helper.empty(msg);
    }

    public void empty(OnClickListener refresh) {
        helper.empty(refresh);
    }

    public void empty(String msg, OnClickListener refresh) {
        helper.empty(msg, refresh);
    }

    /**
     * State -> Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void error() {
        helper.error();
    }

    public void error(String errorMsg) {
        helper.error(errorMsg);
    }

    public void error(OnClickListener refresh) {
        helper.error(refresh);
    }

    public void error(String errorMsg, OnClickListener refresh) {
        helper.error(errorMsg,refresh);
    }

    /**
     * State -> Net_Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void netError() {
        helper.netError();
    }

    public void netError(String errorMsg) {
        helper.netError(errorMsg);
    }

    public void netError(OnClickListener refresh) {
        helper.netError(refresh);
    }

    public void netError(String errorMsg, OnClickListener refresh) {
        helper.netError(errorMsg, refresh);
    }

    @Override
    public void loading() {
        helper.loading();
    }

    @Override
    public void loading(String msg) {
        helper.loading(msg);
    }

    @Override
    public void loading(IProgress iProgress) {
        helper.loading( iProgress);
    }

    @Override
    public void loading(String msg,IProgress iProgress) {
        helper.loading(msg, iProgress);
    }

    @Override
    public void hideLoading() {
        helper.hideLoading();
    }

    /**
     * State => Content
     * 正常界面显示
     */
    public void content() {
        helper.content();
    }

    @Override
    public void withAnimator(boolean with) {
        helper.withAnimator(with);
    }

    @Override
    public boolean isWithAnimator() {
        return helper.isWithAnimator();
    }

    @Override
    public void release() {
        helper.release();
    }
}
