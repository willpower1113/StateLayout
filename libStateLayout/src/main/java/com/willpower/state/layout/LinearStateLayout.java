package com.willpower.state.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.willpower.state.model.IModel;
import com.willpower.state.utils.StateChangeHelper;
import com.willpower.state.animator.IProgress;

/**
 * 线性布局
 */
public class LinearStateLayout extends LinearLayout implements IModel {

    public LinearStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public LinearStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        post(new Runnable() {
            @Override
            public void run() {
                helper.empty();
            }
        });
    }

    public void empty(final String msg) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.empty(msg);
            }
        });
    }

    public void empty(final OnClickListener refresh) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.empty(refresh);
            }
        });
    }

    public void empty(final String msg, final OnClickListener refresh) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.empty(msg, refresh);
            }
        });
    }

    /**
     * State -> Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void error() {
        post(new Runnable() {
            @Override
            public void run() {
                helper.error();
            }
        });
    }

    public void error(final String errorMsg) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.error(errorMsg);
            }
        });
    }

    public void error(final OnClickListener refresh) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.error(refresh);
            }
        });
    }

    public void error(final String errorMsg, final OnClickListener refresh) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.error(errorMsg,refresh);
            }
        });
    }

    /**
     * State -> Net_Error
     * 方法重载
     * param => refresh 点击事件回调
     */
    public void netError() {
        post(new Runnable() {
            @Override
            public void run() {
                helper.netError();
            }
        });
    }

    public void netError(final String errorMsg) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.netError(errorMsg);
            }
        });
    }

    public void netError(final OnClickListener refresh) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.netError(refresh);
            }
        });
    }

    public void netError(final String errorMsg, final OnClickListener refresh) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.netError(errorMsg, refresh);
            }
        });
    }

    @Override
    public void loading() {
        post(new Runnable() {
            @Override
            public void run() {
                helper.loading();
            }
        });
    }

    @Override
    public void loading(final String msg) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.loading(msg);
            }
        });
    }

    @Override
    public void loading(final IProgress iProgress) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.loading( iProgress);
            }
        });
    }

    @Override
    public void loading(final String msg, final IProgress iProgress) {
        post(new Runnable() {
            @Override
            public void run() {
                helper.loading(msg, iProgress);
            }
        });
    }

    @Override
    public void hideLoading() {
        post(new Runnable() {
            @Override
            public void run() {
                helper.hideLoading();
            }
        });
    }

    /**
     * State => Content
     * 正常界面显示
     */
    public void content() {
        post(new Runnable() {
            @Override
            public void run() {
                helper.content();
            }
        });
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
