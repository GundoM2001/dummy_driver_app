package com.example.onboardingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotLayout;
    Button skipBtn;
    ImageView nextBtn;

    TextView[] dots;
    OnboardingViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextBtn = findViewById(R.id.next_btn);
        skipBtn = findViewById(R.id.skip_btn);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(0) < 2){
                    viewPager.setCurrentItem(getItem(1), true);
                }
                else{
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();

            }
        });

        viewPager = (ViewPager) findViewById(R.id.onboard_view_pager);
        dotLayout = (LinearLayout) findViewById(R.id.dot_layout);

        viewPagerAdapter = new OnboardingViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    //Adds dots to the indicator
    public void setUpIndicator(int position){
        dots = new TextView[3];

        dotLayout.removeAllViews();

        //Loop that creates the dots, by the amount of units and gives them color
        for(int i = 0; i < dots.length; i++ ){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            dotLayout.addView(dots[i]);
        }

        //Sets the current page's dot to blue
        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem(int i){

        return viewPager.getCurrentItem() + i;
    }
}