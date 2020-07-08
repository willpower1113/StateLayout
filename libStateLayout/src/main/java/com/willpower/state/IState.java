package com.willpower.state;

import android.view.View;

/**
 * 包含状态切换功能的接口
 */
public interface IState {
    int CONTENT = 0;//正常
    int ERROR = 1;//异常
    int EMPTY = 2;//没有数据
    int NET_ERROR = 3;//无网络

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
}
