package com.abhi41.mvvmpractice.utils;

import android.app.Application;

import com.abhi41.mvvmpractice.DI.DaggerMyComponent;
import com.abhi41.mvvmpractice.DI.MyComponent;
import com.abhi41.mvvmpractice.DI.ApiModule;


public class MyApplication extends Application {


    private MyComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerMyComponent.builder()
                .apiModule(new ApiModule())
                .build();

    }

    public MyComponent getComponents() {
        return component;
    }
}
