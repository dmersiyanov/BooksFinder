package com.mersiyanov.dmitry.booksfinder.data.repositories;

import com.mersiyanov.dmitry.booksfinder.data.network.GoogleApi;
import com.mersiyanov.dmitry.booksfinder.domain.BookRepository;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.BooksResponse;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.Item;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookRepositoryImpl implements BookRepository {

    private final GoogleApi api;
    private List<Item> cache;

    public BookRepositoryImpl(GoogleApi api) {
        this.api = api;
    }

    @Override
    public Single<BooksResponse> getSearchResults(String query) {
            return api.getBooksInfo(query).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(booksResponse -> cache = booksResponse.getItems());
        }

    @Override
    public List<Item> getCache() {
        return cache;
    }
}
