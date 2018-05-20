package com.mersiyanov.dmitry.booksfinder.di;

import com.mersiyanov.dmitry.booksfinder.data.network.GoogleApi;
import com.mersiyanov.dmitry.booksfinder.data.repositories.BookRepositoryImpl;
import com.mersiyanov.dmitry.booksfinder.domain.BookRepository;
import com.mersiyanov.dmitry.booksfinder.domain.BookSearchUseCase;
import com.mersiyanov.dmitry.booksfinder.presentation.BookSearchPresenter;

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

    @Provides
    @Singleton
    BookRepositoryImpl provideBookRepositoryImpl(GoogleApi api) {
        return new BookRepositoryImpl(api);
    }

    @Provides
    @Singleton
    BookRepository provideBookRepository(BookRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    BookSearchUseCase provideBookSearchUseCase(BookRepository bookRepository) {
        return new BookSearchUseCase(bookRepository);
    }


    @Provides
    @Singleton
    BookSearchPresenter provideBookSearchPresenter(BookSearchUseCase useCase) {
        return new BookSearchPresenter(useCase);
    }

}
