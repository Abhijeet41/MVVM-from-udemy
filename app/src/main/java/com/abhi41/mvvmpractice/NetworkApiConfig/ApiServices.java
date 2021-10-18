package com.abhi41.mvvmpractice.NetworkApiConfig;

import com.abhi41.mvvmpractice.Model.CountryModel;
import com.abhi41.mvvmpractice.response.UsersList;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServices {

    @GET("DevTides/countries/master/countriesV2.json")
//this is an endpoint
    Single<List<CountryModel>> getCountries();          //single emmits one value and finishes


    @GET("public/v1/users?")
    Single<UsersList> getUsers(@Query("page") int currentPage);

    @GET("public/v1/users?")
    Single<UsersList> getUserDetails(@Query("name") String name);

    //if you don't have endpoints then
//    @GET
//    Single<CountryModel> getObject(@Url String urlString);

}
