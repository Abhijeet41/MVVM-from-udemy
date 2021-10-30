package com.abhi41.mvvmpractice.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.mvvmpractice.data.Repository.RepoUsers;
import com.abhi41.mvvmpractice.response.DataItem;
import com.abhi41.mvvmpractice.response.UsersList;
import com.abhi41.mvvmpractice.utils.Resource;

import java.util.List;

public class ViewmodelUserList extends AndroidViewModel {

    private RepoUsers repoUsers;

    public ViewmodelUserList(@NonNull Application application) {
        super(application);
        repoUsers = RepoUsers.getInstance();

    }

    public MutableLiveData<Resource<UsersList>> fetchUsersList(int currentPage) {
        return repoUsers.fetchUsersList(currentPage);
    }

    public void fetchUserDetail(String name) {
        repoUsers.fetchUserDetails(name);
    }


    public MutableLiveData<Resource<List<DataItem>>> getUserDetails() {
        return repoUsers.getMutableUserDetail();
    }
}
