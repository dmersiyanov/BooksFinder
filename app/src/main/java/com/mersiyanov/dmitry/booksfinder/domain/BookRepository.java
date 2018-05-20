package com.mersiyanov.dmitry.booksfinder.domain;

import com.mersiyanov.dmitry.booksfinder.domain.Entity.BooksResponse;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.Item;

import java.util.List;

import io.reactivex.Single;

public interface BookRepository {

    Single<BooksResponse> getSearchResults(String query);
    List<Item> getCache();

}
