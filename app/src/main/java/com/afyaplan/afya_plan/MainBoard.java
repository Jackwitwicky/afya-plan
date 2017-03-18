package com.afyaplan.afya_plan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.afyaplan.afya_plan.fragments.FragmentChamaDetails;
import com.afyaplan.afya_plan.fragments.FragmentContribute;
import com.afyaplan.afya_plan.fragments.FragmentHelp;
import com.afyaplan.afya_plan.fragments.FragmentRequest;
import com.afyaplan.afya_plan.fragments.FragmentStatement;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

public class MainBoard extends AppCompatActivity {
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        BottomNavigation bottomNavigation = (BottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setDefaultItem(2);
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int itemId) {
                switch (itemId) {
                    case R.id.tab_home:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentChamaDetails());
                        break;
                    case R.id.tab_images:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentContribute());
                        break;
                    case R.id.tab_camera:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentStatement());
                        break;
                    case R.id.tab_products:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentRequest());
                        break;
                    case R.id.tab_more:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentHelp());
                        break;
                    default:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentChamaDetails());
                }
                transaction.commit();
            }
        });
    }
}
