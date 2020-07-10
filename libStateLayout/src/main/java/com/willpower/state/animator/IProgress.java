package com.willpower.state.animator;

import android.graphics.Canvas;

/**
 * Loading 状态 显示的进度条基类
 * 用户可以实现此接口创建自己的进度条
 */
public interface IProgress {
    /**
     * 绘制进度条的方法
     *
     * @param canvas      画布
     * @param size        progress 期望尺寸
     * @param centerPoint progress 期望中心点 [x,y]
     */
    void drawProgress(Canvas canvas, int size, int[] centerPoint);

    /**
     * 开启进度条动画
     */
    void showProgress();

    /**
     * 关闭进度条动画
     */
    void hideProgress();

    /**
     * 对外提供进度条实时刷新的回调接口
     *
     * @param listener 实时刷新的回调接口
     */
    void setOnProgressChangeListener(OnProgressChangeListener listener);

    /**
     * 进度条实时刷新的回调接口
     */
    interface OnProgressChangeListener {
        //通知 View 更新绘制的回调方法
        void viewPostInvalidate();
    }
}
