package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.willpower.state.layout.FrameStateLayout;

/**
 * 在 XML 中直接使用 StateLayout
 * library 提供了四种布局可以直接使用：
 * ConstraintStateLayout 继承 ConstraintLayout
 * LinearStateLayout 继承 LinearLayout
 * FrameStateLayout 继承 FrameLayout
 * RelativeStateLayout 继承 RelativeLayout
 * <p>
 * 用户还可以根据需求自定义 StateLayout
 * 1.实现 IModel
 * 2.绑定 StateChangeHelper 【处理状态切换等业务逻辑】
 * <p>
 * 注意：XML 格式接入的StateLayout 不支持动态改变 文字，Icon 属性
 * 可以再布局中直接设置
 */
public class XMLFragment extends Fragment implements View.OnClickListener {

    View rootView;

    FrameStateLayout mStateLayout;

    Button onNonNetWork, onError, onContent, onEmpty, onLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_xml, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        mStateLayout = rootView.findViewById(R.id.mStateLayout);
        mStateLayout.error();
        onNonNetWork = rootView.findViewById(R.id.onNonNetWork);
        onError = rootView.findViewById(R.id.onError);
        onContent = rootView.findViewById(R.id.onContent);
        onEmpty = rootView.findViewById(R.id.onEmpty);
        onLoading = rootView.findViewById(R.id.onLoading);

        onNonNetWork.setOnClickListener(this);
        onError.setOnClickListener(this);
        onContent.setOnClickListener(this);
        onEmpty.setOnClickListener(this);
        onLoading.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onNonNetWork:
                mStateLayout.netError();
                break;
            case R.id.onError:
                mStateLayout.error();
                break;
            case R.id.onContent:
                mStateLayout.content();
                break;
            case R.id.onEmpty:
                mStateLayout.empty();
                break;
            case R.id.onLoading:
                mStateLayout.loading();
                break;
        }
    }
}
