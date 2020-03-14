package com.jvboot.pp.web.dto;

public class DataDto<T> {
    private final T data;

    public DataDto(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
