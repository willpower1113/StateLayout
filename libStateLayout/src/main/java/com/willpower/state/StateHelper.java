package com.willpower.state;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.willpower.state.animator.TransitionAnimation;


/**
 * 非侵入式 加载 StateLayout 辅助类
 */
public class StateHelper implements IState {

    ViewGroup root;

    StateLayout stateLayout;

    View filter;

    public StateHelper(Activity activity, View filter) {
        this((ViewGroup) activity.findViewById(android.R.id.content), filter);
    }

    public StateHelper(Activity activity, int backgroundColor, View filter) {
        this((ViewGroup) activity.findViewById(android.R.id.content), backgroundColor, filter);
    }

    public StateHelper(ViewGroup root) {
        this(root, 0, null);
    }

    public StateHelper(ViewGroup root, View filter) {
        this(root, 0, filter);
    }

    public StateHelper(ViewGroup root, int backgroundColor) {
        this(root, backgroundColor, null);
    }

    public StateHelper(ViewGroup root, int backgroundColor, View filter) {
        this.root = root;
        this.filter = filter;
        createStateLayout(backgroundColor);
        addState();
        content();
    }

    public void release() {
        if (this.root != null) removeState();
        this.root = null;
    }

    public void createStateLayout(int color) {
        stateLayout = new StateLayout(root.getContext());
        if (color != 0)
            stateLayout.setBackgroundColor(color);
    }

    private void addState() {
        if (this.filter != null) {
            checkChild(root);
        }
        root.addView(stateLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void checkChild(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        if (viewGroup.getChildCount() == 0) return;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i).getClass().equals(this.filter.getClass())) {
                this.root = viewGroup;
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                checkChild((ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }


    private void removeState() {
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

}
