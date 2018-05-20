package com.mersiyanov.dmitry.booksfinder.presentation;

import com.mersiyanov.dmitry.booksfinder.domain.BookSearchUseCase;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.BooksResponse;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.Item;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookSearchPresenter {
    private View view;
    private final BookSearchUseCase bookSearchUseCase;

    @Inject
    public BookSearchPresenter(BookSearchUseCase bookSearchUseCase) {
        this.bookSearchUseCase = bookSearchUseCase;
    }

    public void makeSearch(String query) {
        bookSearchUseCase.execute(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

    }

    public List<Item> getCache() {
        return bookSearchUseCase.getCache();
    }

    private void onError(Throwable throwable) {
        view.showError(throwable.getMessage());
    }

    private void onSuccess(BooksResponse booksResponse) {
        view.showSearchResults(booksResponse.getItems());
    }

    public void attachView(MainActivity view) {
        this.view =  view;
    }

    public void detachView() {
        view = null;
    }



}
