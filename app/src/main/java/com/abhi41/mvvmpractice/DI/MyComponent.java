package com.abhi41.mvvmpractice.DI;

import com.abhi41.mvvmpractice.NetworkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.Repository.RepoUsers;
import com.abhi41.mvvmpractice.ViewModel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface MyComponent {

    void inject(ApiClient service);

    void inject(ListViewModel listViewModel);

    void inject(RepoUsers repoUsers);
}
