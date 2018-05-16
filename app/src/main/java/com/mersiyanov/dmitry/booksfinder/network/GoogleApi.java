package com.mersiyanov.dmitry.booksfinder.network;

import com.mersiyanov.dmitry.booksfinder.pojo.BooksResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApi {

    @GET("volumes")
    Single<BooksResponse> getBooksInfo(@Query("q") String searsh);


}
