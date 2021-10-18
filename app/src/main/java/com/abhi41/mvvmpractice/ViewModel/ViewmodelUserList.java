package com.abhi41.mvvmpractice.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.mvvmpractice.Repository.RepoUsers;
import com.abhi41.mvvmpractice.response.DataItem;
import com.abhi41.mvvmpractice.response.UsersList;

import java.util.List;

public class ViewmodelUserList extends AndroidViewModel {

    private RepoUsers repoUsers;

    public ViewmodelUserList(@NonNull Application application) {
        super(application);
        repoUsers = new RepoUsers();
    }

    public LiveData<UsersList> fetchUsersList(int currentPage) {
        return repoUsers.fetchUsersList(currentPage);
    }

    public MutableLiveData<Boolean> getLoading()
    {
        return repoUsers.getLoading();
    }

    public void fetchUserDetail(String name) {
        repoUsers.fetchUserDetails(name);
    }


    public MutableLiveData<List<DataItem>> getUserDetails() {
        return repoUsers.getMutableUserDetail();
    }
}
