package com.abhi41.mvvmpractice.NetworkApiConfig;



import com.abhi41.mvvmpractice.DI.DaggerMyComponent;
import com.abhi41.mvvmpractice.Model.CountryModel;
import com.abhi41.mvvmpractice.response.UsersList;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

public class ApiClient {



    private static ApiClient apiClient;

    @Named("RetrofitClient")
    @Inject
    public ApiServices api;

    @Named("UsersClient")
    @Inject
    public ApiServices apiUsers;


    private ApiClient() {  // hides the constructors
       DaggerMyComponent.create().inject(this);

    }

    public static ApiClient getInstance()
    {
        if (apiClient == null)
        {
            apiClient = new ApiClient();
        }
        return apiClient;
    }


    public Single<List<CountryModel>> getCountries()
    {
        return api.getCountries();
    }

    public Single <UsersList>  getUsers(int currentPage){
        return apiUsers.getUsers(currentPage);
    }

    public Single<UsersList> getUserDetails(String name){
        return apiUsers.getUserDetails(name);
    }

}
