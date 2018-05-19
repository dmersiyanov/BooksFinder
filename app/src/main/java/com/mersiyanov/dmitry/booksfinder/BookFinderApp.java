package com.mersiyanov.dmitry.booksfinder;

import android.app.Application;

import com.mersiyanov.dmitry.booksfinder.di.AppComponent;
import com.mersiyanov.dmitry.booksfinder.di.DaggerAppComponent;

public class BookFinderApp extends Application {

    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }
}
