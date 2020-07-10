package com.willpower.state.model;

import android.graphics.Canvas;
import android.view.View;

import com.willpower.state.animator.IProgress;

public interface IModel {
    /**
     * State -> Empty
     * 方法重载
     * param => refresh 点击事件回调
     */
    void empty();

    void empty(String msg);

    void empty(View.OnClickListener refresh);

    void empty(String msg, View.OnClickListener refresh);

    /**
     * State -> Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    void error();

    void error(String errorMsg);

    void error(View.OnClickListener refresh);

    void error(String errorMsg, View.OnClickListener refresh);

    /**
     * State -> Net_Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    void netError();

    void netError(String errorMsg);

    void netError(View.OnClickListener refresh);

    void netError(String errorMsg, View.OnClickListener refresh);

    /**
     * State -> Loading
     * 方法重载
     * param => refresh 点击事件回调
     */
    void loading();

    void loading(String msg);

    void loading(IProgress iProgress);

    void loading(String msg,IProgress iProgress);

    void hideLoading();

    /**
     * State => Content
     * 正常界面显示
     */
    void content();

    /**
     * 是否开启动画
     * @param with
     */
    void withAnimator(boolean with);

    boolean isWithAnimator();

    void release();
}
