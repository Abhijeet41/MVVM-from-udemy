package com.abhi41.mvvmpractice.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.mvvmpractice.di.DaggerMyComponent;
import com.abhi41.mvvmpractice.networkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.response.DataItem;
import com.abhi41.mvvmpractice.response.UsersList;
import com.abhi41.mvvmpractice.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RepoUsers {

    public static RepoUsers repoUsers;

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> userError = new MutableLiveData<>();

    public MutableLiveData<Resource<List<DataItem>>> mutableUserdetail;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Resource<UsersList>> mUserdata = new MutableLiveData<>();

    @Inject
    public ApiClient apiClient;

    private RepoUsers() {
        DaggerMyComponent.create().inject(this);

    }


    public static RepoUsers getInstance() {
        if (repoUsers == null) {
            repoUsers = new RepoUsers();
        }

        return repoUsers;
    }

    public MutableLiveData<Resource<UsersList>> fetchUsersList(int currentPage) {
        return apiFetchUsers(currentPage);
    }

    public void fetchUserDetails(String name) {
        if (mutableUserdetail == null) {
            mutableUserdetail = new MutableLiveData<>();
        }
        apiFetchUserDetails(name);
    }


    public MutableLiveData<Resource<List<DataItem>>> getMutableUserDetail() {

        return mutableUserdetail;
    }


    public MutableLiveData<Resource<UsersList>> apiFetchUsers(int currentPage) {

        mUserdata.setValue(Resource.loading(null));

        disposable.add(apiClient.getUsers(currentPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UsersList>() {

                    @Override
                    public void onSuccess(@NonNull UsersList usersList) {
                        loading.setValue(false);
                        userError.setValue(false);


                        mUserdata.setValue(Resource.success(usersList));

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        userError.setValue(true);
                        loading.setValue(false);

                        mUserdata.setValue(Resource.error(e.getMessage(), null));

                    }
                }));

        return mUserdata;

    }

    private void apiFetchUserDetails(String name) {

        mutableUserdetail.setValue(Resource.loading(null));

        disposable.add(apiClient.getUserDetails(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UsersList>() {
                    @Override
                    public void onSuccess(@NonNull UsersList usersList) {
                        loading.setValue(false);

                        mutableUserdetail.setValue(Resource.success(usersList.getData()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mutableUserdetail.setValue(Resource.error(e.getMessage(), null));
                        loading.setValue(false);
                    }
                }));

    }


}
