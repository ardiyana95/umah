 package com.example.umah_app.onboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.umah_app.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {

            R.drawable.on_1,
            R.drawable.on_2

    };

    public String[] slide_title = {
            "Group Decision Support System",
            "Urutkan Kriteria Anda"
    };

    public String[] slide_detail = {
            "Aplikasi Sistem Pendukung Keputusan Kelompok (SPKK) Pencarian rumah ",
            "Urutkan kriteria yang menurut anda paling dibutuhkan"
    };

    @Override
    public int getCount() {
        return  slide_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.on_img);
        TextView slideTitle = (TextView) view.findViewById(R.id.on_title);
        TextView slideDetail = (TextView) view.findViewById(R.id.on_detail);

        slideImageView.setImageResource(slide_images[position]);
        slideTitle.setText(slide_title[position]);
        slideDetail.setText(slide_detail[position]);

        container.addView(view);

        return  view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
