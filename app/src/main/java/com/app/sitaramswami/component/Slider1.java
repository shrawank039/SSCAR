package com.app.sitaramswami.component;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.sitaramswami.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by kartik on 28-Apr-18.
 */

public class Slider1 extends LinearLayout implements ViewPager.OnPageChangeListener {
    private ViewPager pager;
    private LinearLayout pointer;
    private Intent intent;
    private ArrayList<String> sliderImages;
    private int lastPos=0;
//    private Handler handler=new Handler();
//    private Runnable runnable=new Runnable() {
//        @Override
//        public void run() {
//            if (sliderImages.size() - 1 == pager.getCurrentItem())
//                pager.setCurrentItem(0);
//            else
//                pager.setCurrentItem(pager.getCurrentItem() + 1);
//
//        }
//    };

    public Slider1(Context context) {
        super(context);
    }

    public Slider1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Slider1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.slider_layout, this, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pointer = (LinearLayout) view.findViewById(R.id.slider_balls);
        pager.addOnPageChangeListener(this);
        addView(view);
    }

    public void setSliderImages(final ArrayList<String> sliderImages) {
        if (sliderImages == null&&sliderImages.size()==0)
            return;
        this.sliderImages = sliderImages;
        SliderAdapter adapter = new SliderAdapter();
        pager.setAdapter(adapter);

        pager.setOffscreenPageLimit(sliderImages.size());
        for (int i = 0; i < sliderImages.size(); i++) {
            View pointerView = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(3,3,3,3);
            pointerView.setLayoutParams(params);
            pointerView.setBackgroundResource(R.drawable.pointer);
            pointer.addView(pointerView);
        }
        pointer.getChildAt(0).setBackgroundResource(R.drawable.pointer_selected);
//        handler.postDelayed(runnable,3000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {

        pointer.getChildAt(lastPos).setBackgroundResource(R.drawable.pointer);
        lastPos = position;
        pointer.getChildAt(position).setBackgroundResource(R.drawable.pointer_selected);
//        handler.removeCallbacks(runnable);
//        handler.postDelayed(runnable,3000);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class SliderAdapter extends PagerAdapter {


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public int getCount() {
            return sliderImages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            PhotoViewAttacher pAttacher;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            pAttacher = new PhotoViewAttacher(imageView);
            pAttacher.update();
          Glide.with(getContext().getApplicationContext()).load(sliderImages.get(position)).into(imageView);
//            Picasso.with(getContext().getApplicationContext()).load(sliderImages.get(position)).centerInside()
//                    .placeholder(R.drawable.car)
//                    .error(R.drawable.car)
//                    .into(imageView);
            container.addView(imageView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            return imageView;
        }

    }

}
