package com.abhi41.mvvmpractice.utils;

import static com.abhi41.mvvmpractice.utils.Status.ERROR;
import static com.abhi41.mvvmpractice.utils.Status.LOADING;
import static com.abhi41.mvvmpractice.utils.Status.SUCCESS;

public class Resource<T> {


    public final Status status;

    public final T data;

    private final String message;


    public Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;

    }



    public static <T> Resource<T> success ( T data)
    {
        return new Resource<>(SUCCESS,data,null);
    }

    public static <T> Resource <T> error (String msg,T data)
    {
        return new Resource<>(ERROR,data,msg);
    }

    public static <T> Resource<T> loading(T data)
    {
        return new Resource<>(LOADING,data,null);
    }

    public String getMessage() {
        return message;
    }
}
