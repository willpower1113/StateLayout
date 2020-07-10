package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.willpower.state.NonInvadeStateHelper;

/**
 * 非侵入式方式接入StateLayout
 */
public class NonInvadeFragment extends Fragment implements View.OnClickListener {

    View rootView;

    NonInvadeStateHelper stateHelper;

    ImageView target;//目标控件，StateLayout 会覆盖目标控件

    Button onNonNetWork, onError, onContent, onEmpty, onLoading;

    CheckBox onLargeText, onRedText, onLargeIcon;

    LinearLayout stateConfig;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_non_invade, container, false);
        initView();
        stateHelper = new NonInvadeStateHelper((ViewGroup) rootView, target);
        stateHelper.withAnimator(true);//开启动画
        return rootView;
    }

    private void initView() {
        target = rootView.findViewById(R.id.target);
        onLargeText = rootView.findViewById(R.id.onLargeText);
        onRedText = rootView.findViewById(R.id.onRedText);
        onLargeIcon = rootView.findViewById(R.id.onLargeIcon);
        onNonNetWork = rootView.findViewById(R.id.onNonNetWork);
        onError = rootView.findViewById(R.id.onError);
        onContent = rootView.findViewById(R.id.onContent);
        onEmpty = rootView.findViewById(R.id.onEmpty);
        onLoading = rootView.findViewById(R.id.onLoading);
        stateConfig = rootView.findViewById(R.id.stateConfig);
        stateConfig.setVisibility(View.GONE);

        onLargeText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    stateHelper.setTextSize(30, Dimension.SP);
                } else {
                    stateHelper.setTextSize(16, Dimension.SP);
                }
            }
        });
        onRedText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    stateHelper.setTextColor(Color.parseColor("#D81B60"));
                } else {
                    stateHelper.setTextColor(Color.parseColor("#AAAAAA"));
                }
            }
        });
        onLargeIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    stateHelper.setIconSize(60, Dimension.DP);
                } else {
                    stateHelper.setIconSize(30, Dimension.DP);
                }
            }
        });
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
                stateConfig.setVisibility(View.VISIBLE);
                stateHelper.netError();
                break;
            case R.id.onError:
                stateConfig.setVisibility(View.VISIBLE);
                stateHelper.error();
                break;
            case R.id.onContent:
                stateConfig.setVisibility(View.GONE);
                stateHelper.content();
                break;
            case R.id.onEmpty:
                stateConfig.setVisibility(View.VISIBLE);
                stateHelper.empty();
                break;
            case R.id.onLoading:
                stateConfig.setVisibility(View.VISIBLE);
                stateHelper.loading();
                break;
        }
    }

}
