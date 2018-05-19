package com.mersiyanov.dmitry.booksfinder.ui;

import com.mersiyanov.dmitry.booksfinder.network.GoogleApi;
import com.mersiyanov.dmitry.booksfinder.pojo.BooksResponse;
import com.mersiyanov.dmitry.booksfinder.pojo.Item;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

    private GoogleApi api;
    private MainActivity view;
    private List<Item> list;

    public MainPresenter(GoogleApi api) {
        this.api = api;
    }

    public void attachView(MainActivity view) {
        this.view =  view;
    }

    public void detachView() {
        view = null;
    }

    public void makeSearch(String query) {

        api.getBooksInfo(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(new SingleObserver<BooksResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onSuccess(BooksResponse booksResponse) {
                        list = booksResponse.getItems();
                        view.showData(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.toString());
                    }
                });

    }

    public List<Item> getSavedData(){
        return list;
    }
}
