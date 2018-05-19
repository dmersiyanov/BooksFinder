package com.mersiyanov.dmitry.booksfinder.di;

import com.mersiyanov.dmitry.booksfinder.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void injects(MainActivity target);
}