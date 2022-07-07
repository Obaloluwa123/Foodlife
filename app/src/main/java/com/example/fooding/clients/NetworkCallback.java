package com.example.fooding.clients;

@SuppressWarnings({"EmptyMethod", "unused"})
public interface NetworkCallback<T> {

    void onSuccess(T data);

    void onFailure(Throwable throwable);
}
