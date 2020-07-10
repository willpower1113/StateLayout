package com.willpower.state;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;

import com.willpower.state.animator.IProgress;
import com.willpower.state.animator.TransitionAnimation;
import com.willpower.state.layout.StateLayout;
import com.willpower.state.model.IModel;
import com.willpower.state.utils.Utils;


/**
 * 非侵入式 加载 StateLayout 辅助类
 */
public class NonInvadeStateHelper implements IModel {

    String TAG = "StateLayout";

    ViewGroup root;

    StateLayout stateLayout;

    View filter;

    public NonInvadeStateHelper(ViewGroup root) {
        this(root, 0, null);
    }

    public NonInvadeStateHelper(ViewGroup root, View filter) {
        this(root, 0, filter);
    }

    public NonInvadeStateHelper(ViewGroup root, int backgroundColor) {
        this(root, backgroundColor, null);
    }

    public NonInvadeStateHelper(ViewGroup root, int backgroundColor, View filter) {
        this.root = root;
        this.filter = filter;
        createStateLayout(backgroundColor);
        addStateLayout();
        content();
    }

    public void release() {
        if (this.root != null) removeStateLayout();
        this.root = null;
        this.stateLayout.release();
        this.stateLayout = null;
    }

    public void createStateLayout(int color) {
        stateLayout = new StateLayout(root.getContext());
        if (color != 0)
            stateLayout.setBackgroundColor(color);
    }

    /**
     * 讲 StateLayout 添加到 root
     */
    private void addStateLayout() {
        if (this.filter != null) {
            checkChild(root);
        }
        root.addView(stateLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 查询 StateLayout 要放到哪个布局下边
     *
     * @param viewGroup
     */
    private void checkChild(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        if (viewGroup.getChildCount() == 0) return;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i).getId() == this.filter.getId()) {
                Log.d(TAG, "checkChild: " + viewGroup.getClass());
                this.root = viewGroup;
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                checkChild((ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    /**
     * 讲 StateLayout 从 root 移除
     */
    private void removeStateLayout() {
        root.removeView(stateLayout);
    }


    @Override
    public void empty() {
        hideContent();
        stateLayout.empty();
    }

    @Override
    public void empty(String msg) {
        hideContent();
        stateLayout.empty(msg);
    }

    @Override
    public void empty(View.OnClickListener refresh) {
        hideContent();
        stateLayout.empty(refresh);
    }

    @Override
    public void empty(String msg, View.OnClickListener refresh) {
        hideContent();
        stateLayout.empty(msg, refresh);
    }

    @Override
    public void error() {
        hideContent();
        stateLayout.error();
    }

    @Override
    public void error(String errorMsg) {
        hideContent();
        stateLayout.error(errorMsg);
    }

    @Override
    public void error(View.OnClickListener refresh) {
        hideContent();
        stateLayout.error(refresh);
    }

    @Override
    public void error(String errorMsg, View.OnClickListener refresh) {
        hideContent();
        stateLayout.error(errorMsg, refresh);
    }

    @Override
    public void netError() {
        hideContent();
        stateLayout.netError();
    }

    @Override
    public void netError(String errorMsg) {
        hideContent();
        stateLayout.netError(errorMsg);
    }

    @Override
    public void netError(View.OnClickListener refresh) {
        hideContent();
        stateLayout.netError(refresh);
    }

    @Override
    public void netError(String errorMsg, View.OnClickListener refresh) {
        hideContent();
        stateLayout.netError(errorMsg, refresh);
    }

    @Override
    public void loading() {
        hideContent();
        stateLayout.loading();
    }

    @Override
    public void loading(String msg) {
        hideContent();
        stateLayout.loading(msg);
    }

    @Override
    public void loading(IProgress iProgress) {
        hideContent();
        stateLayout.loading(iProgress);
    }

    @Override
    public void loading(String msg, IProgress iProgress) {
        hideContent();
        stateLayout.loading(msg, iProgress);
    }

    @Override
    public void hideLoading() {
        stateLayout.hideLoading();
    }

    @Override
    public void content() {
        stateLayout.content();
        showContent();
    }

    @Override
    public void withAnimator(boolean with) {
        stateLayout.withAnimator(with);
    }

    @Override
    public boolean isWithAnimator() {
        return stateLayout.isWithAnimator();
    }

    private void hideContent() {
        for (int i = 0; i < this.root.getChildCount(); i++) {
            View view = this.root.getChildAt(i);
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
        if (isWithAnimator()) {
            TransitionAnimation.IN(stateLayout);
        } else {
            stateLayout.setVisibility(View.VISIBLE);
        }
    }

    private void showContent() {
        for (int i = 0; i < this.root.getChildCount(); i++) {
            View view = this.root.getChildAt(i);
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }
        stateLayout.setVisibility(View.GONE);
    }


    /**
     * 对外提供的配置方法
     */

    //设置文字大小
    public void setTextSize(int textSize, @Dimension int unit) {
        switch (unit) {
            case Dimension.SP:
                this.stateLayout.setTextSize(Utils.sp2px(this.root.getContext(), textSize));
                break;
            case Dimension.DP:
                this.stateLayout.setTextSize(Utils.dp2px(this.root.getContext(), textSize));
                break;
            case Dimension.PX:
                this.stateLayout.setTextSize(textSize);
                break;
        }
    }

    //设置文字颜色
    public void setTextColor(@ColorInt int textColor) {
        this.stateLayout.setTextColor(textColor);
    }

    //设置ICON大小
    public void setIconSize(int iconSize, @Dimension int unit) {
        switch (unit) {
            case Dimension.SP:
                this.stateLayout.setIconSize(Utils.sp2px(this.root.getContext(), iconSize));
                break;
            case Dimension.DP:
                this.stateLayout.setIconSize(Utils.dp2px(this.root.getContext(), iconSize));
                break;
            case Dimension.PX:
                this.stateLayout.setIconSize(iconSize);
                break;
        }
    }
}
