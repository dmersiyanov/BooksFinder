package com.mersiyanov.dmitry.booksfinder.domain;

import com.mersiyanov.dmitry.booksfinder.domain.Entity.BooksResponse;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.Item;

import java.util.List;

import io.reactivex.Single;

public class BookSearchUseCase implements UseCase<String, BooksResponse> {

    private final BookRepository bookRepository;

    public BookSearchUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Single<BooksResponse> execute(final String query) {
        return bookRepository.getSearchResults(query);

    }

    public List<Item> getCache() {
       return bookRepository.getCache();
    }
}
