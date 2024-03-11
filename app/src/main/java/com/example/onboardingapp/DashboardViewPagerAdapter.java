package com.example.onboardingapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class DashboardViewPagerAdapter extends FragmentStateAdapter {




    public DashboardViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle){
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new Revenue();
        }
        if (position == 2){
            return new Penalties();
        }

        return new Volume();
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void AddFragment(Fragment fragment){

    }
}
