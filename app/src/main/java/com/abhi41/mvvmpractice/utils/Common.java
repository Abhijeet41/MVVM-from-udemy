package com.abhi41.mvvmpractice.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.abhi41.mvvmpractice.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import okhttp3.Request;

public class Common {
    public static void loadImage(ImageView view, String url, CircularProgressDrawable progressDrawable)
    {
        RequestOptions options = new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(view);

    }

    public static CircularProgressDrawable getProgressCircularDrawable(Context context)
    {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }
}
