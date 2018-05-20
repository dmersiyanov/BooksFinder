package com.mersiyanov.dmitry.booksfinder.presentation;

import com.mersiyanov.dmitry.booksfinder.domain.Entity.Item;

import java.util.List;

public interface View {

    void showSearchResults(List<Item> books);
    void showError(String msg);
    void showLoading(boolean flag);

}
