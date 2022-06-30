package com.example.fooding.clients;

public interface NetworkCallback<T> {

    void onSuccess(T data);
    void onFailure(Throwable throwable);
}
