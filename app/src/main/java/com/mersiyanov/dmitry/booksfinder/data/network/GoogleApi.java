package com.mersiyanov.dmitry.booksfinder.data.network;

import com.mersiyanov.dmitry.booksfinder.domain.Entity.BooksResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApi {

    @GET("volumes")
    Single<BooksResponse> getBooksInfo(@Query("q") String searsh);


}
