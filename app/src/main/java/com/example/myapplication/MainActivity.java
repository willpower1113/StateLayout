package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;

    static String TAG = "StateLayout";

    NonInvadeFragment nonInvadeFragment;

    XMLFragment xmlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.mTabLayout);
        mTabLayout.addOnTabSelectedListener(listener);
        initFragment();
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        nonInvadeFragment =  new NonInvadeFragment();
        transaction.add(R.id.mContainer,nonInvadeFragment);
        xmlFragment = new XMLFragment();
        transaction.add(R.id.mContainer,xmlFragment);
        transaction.commit();
        changeContent(0);
    }

    private void changeContent(int position) {
        Log.d(TAG, "changeContent: " + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == 0) {
            transaction.show(nonInvadeFragment);
            transaction.hide(xmlFragment);
        } else {
            transaction.show(xmlFragment);
            transaction.hide(nonInvadeFragment);
        }
        transaction.commit();
    }

    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            changeContent(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

}
