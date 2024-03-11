package com.example.onboardingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    ImageView profileBtn;
    ViewPager2 dashboardViewPager;
    TabLayout tabLayout;
    DashboardViewPagerAdapter dashboardViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboardViewPager = findViewById(R.id.dashboard_viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        profileBtn = findViewById(R.id.profile_btn);


        dashboardViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        dashboardViewPager.setAdapter(dashboardViewPagerAdapter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        dashboardViewPagerAdapter = new DashboardViewPagerAdapter(fragmentManager,getLifecycle());

        dashboardViewPager.setAdapter(dashboardViewPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Volume"));
        tabLayout.addTab(tabLayout.newTab().setText("Revenue"));
        tabLayout.addTab(tabLayout.newTab().setText("Penalties"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                dashboardViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        dashboardViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position);
            }
        });



        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(Dashboard.this, Profile.class);
               startActivity(i);
            }
        });


    }
}