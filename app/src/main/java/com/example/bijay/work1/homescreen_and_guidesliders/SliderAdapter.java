package com.example.bijay.work1.homescreen_and_guidesliders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bijay.work1.R;

/**
 * Created by Bijay on 3/3/2018.
 */

public class SliderAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //arrays

    public int[] slide_images={

            R.drawable.eat_icon,
            R.drawable.sleep_icon,
            R.drawable.code_icon

    };

    public String[] slide_headings={

            "Online Booking System",
            "Online Booking System",
            "Online Booking System"

    };

    public String[] slide_decs={

            "Awesome! As you can see, the TextView we added the font to has the new font. However, in most cases, you might want to use the same font across the app and not add it to every TextView. A simple way to do it is to go to your styles.xml and add this to the theme of your app.",
            "Awesome! As you can see, the TextView we added the font to has the new font. However, in most cases, you might want to use the same font across the app and not add it to every TextView. A simple way to do it is to go to your styles.xml and add this to the theme of your app",
            "Awesome! As you can see, the TextView we added the font to has the new font. However, in most cases, you might want to use the same font across the app and not add it to every TextView. A simple way to do it is to go to your styles.xml and add this to the theme of your app."



    };





    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageview=(ImageView) view.findViewById(R.id.slide_image);
        TextView slideheading=(TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescrption=(TextView) view.findViewById(R.id.slide_dec);

        slideImageview.setImageResource(slide_images[position]);
        slideheading.setText(slide_headings[position]);
        slideDescrption.setText(slide_decs[position]);

        container.addView(view);



        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
