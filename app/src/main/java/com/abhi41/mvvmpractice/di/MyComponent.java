package com.abhi41.mvvmpractice.di;

import com.abhi41.mvvmpractice.networkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.data.Repository.RepoUsers;
import com.abhi41.mvvmpractice.view_model.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface MyComponent {

    void inject(ApiClient service);

    void inject(ListViewModel listViewModel);

    void inject(RepoUsers repoUsers);



}
