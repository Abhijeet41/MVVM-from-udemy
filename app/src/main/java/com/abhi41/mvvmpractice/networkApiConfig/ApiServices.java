package com.abhi41.mvvmpractice.networkApiConfig;

import com.abhi41.mvvmpractice.data.Model.CountryModel;
import com.abhi41.mvvmpractice.response.UsersList;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
