package com.abhi41.mvvmpractice.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.mvvmpractice.DI.DaggerMyComponent;
import com.abhi41.mvvmpractice.NetworkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.response.DataItem;
import com.abhi41.mvvmpractice.response.UsersList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RepoUsers {

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> userError = new MutableLiveData<>();

    public MutableLiveData<List<DataItem>> mutableUserdetail;
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<UsersList> mutableUserData = new MutableLiveData<>();

    @Inject
    public ApiClient apiClient;

    public RepoUsers() {
        DaggerMyComponent.create().inject(this);
    }

    public LiveData<UsersList> fetchUsersList(int currentPage) {
       return apiFetchUsers(currentPage);
    }

    public void fetchUserDetails(String name) {
        apiFetchUserDetails(name);
    }

    public MutableLiveData<Boolean> getLoading()
    {
        return loading;
    }



    public MutableLiveData<List<DataItem>> getMutableUserDetail() {
        if (mutableUserdetail == null) {
            mutableUserdetail = new MutableLiveData<>();
        }
        return mutableUserdetail;
    }


    private LiveData<UsersList> apiFetchUsers(int currentPage) {

        loading.setValue(true);

        disposable.add(apiClient.getUsers(currentPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UsersList>() {

                    @Override
                    public void onSuccess(@NonNull UsersList usersList) {
                        loading.setValue(false);
                        userError.setValue(false);
                        mutableUserData.setValue(usersList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        userError.setValue(true);
                        loading.setValue(false);

                        mutableUserData.setValue(null);

                    }
                }));

        return mutableUserData;

    }

    private void apiFetchUserDetails(String name) {

        loading.setValue(true);

        disposable.add(apiClient.getUserDetails(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UsersList>() {
                    @Override
                    public void onSuccess(@NonNull UsersList usersList) {
                        loading.setValue(false);

                        mutableUserdetail.setValue(usersList.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        loading.setValue(false);
                    }
                }));

    }


}
