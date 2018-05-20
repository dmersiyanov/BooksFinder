package com.mersiyanov.dmitry.booksfinder.domain;


import io.reactivex.Single;

public interface UseCase<P, R> {
    Single<R> execute(P parameter);
}