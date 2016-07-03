package com.example.mmihaylov.simplelists;

public interface ResponseListener<T> {

    void onResponse(T responseItems);
}
