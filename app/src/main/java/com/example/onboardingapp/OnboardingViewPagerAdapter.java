package com.example.onboardingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class OnboardingViewPagerAdapter extends PagerAdapter {

    Context context;

    int onboardImages[]={
            R.drawable.plane,
            R.drawable.boxes,
            R.drawable.envelope
    };

    int onBoardTitles[]={
           R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three
    };

    int onBoardDesc[]= {
            R.string.desc_one,
            R.string.desc_two,
            R.string.desc_three
    };

    public OnboardingViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return onBoardTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView sliderImage = (ImageView) view.findViewById(R.id.title_image);
        TextView sliderHeading = (TextView) view.findViewById(R.id.slider_title);
        TextView sliderDesc = (TextView) view.findViewById(R.id.slider_desc);

        sliderImage.setImageResource(onboardImages[position]);
        sliderHeading.setText(onBoardTitles[position]);
        sliderDesc.setText(onBoardDesc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
