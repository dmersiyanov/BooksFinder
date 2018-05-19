package com.mersiyanov.dmitry.booksfinder.di;

import com.mersiyanov.dmitry.booksfinder.MainPresenter;
import com.mersiyanov.dmitry.booksfinder.network.GoogleApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(GoogleApi api) {
        return new MainPresenter(api);
    }

    @Provides
    @Singleton
    GoogleApi provideApi(){
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(GoogleApi.class);
    }

}
