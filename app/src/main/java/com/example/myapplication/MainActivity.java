package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.willpower.state.FrameStateLayout;
import com.willpower.state.StateHelper;

public class MainActivity extends AppCompatActivity {

    StateHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new StateHelper(this,findViewById(R.id.target));
        helper.withAnimator(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onButtonClick(View v){
        helper.netError("网络异常",new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击【StateLayout】！",Toast.LENGTH_SHORT).show();
                helper.content();
            }
        });
    }
}
