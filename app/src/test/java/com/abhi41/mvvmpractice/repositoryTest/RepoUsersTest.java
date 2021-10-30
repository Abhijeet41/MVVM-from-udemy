package com.abhi41.mvvmpractice.repositoryTest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.abhi41.mvvmpractice.networkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.data.Repository.RepoUsers;
import com.abhi41.mvvmpractice.response.DataItem;
import com.abhi41.mvvmpractice.response.UsersList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class RepoUsersTest extends Application {

    //It simplifies that it's a rule that any task executaion will be instant.
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    ApiClient apiClient;

    @InjectMocks
    RepoUsers repoUsers = RepoUsers.getInstance();

    private Single<UsersList> usersListSingle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers() {

        List<DataItem> dataItemList = new ArrayList<>();
        DataItem userData = new DataItem("male", "Abhijeet", 1, "abc@gmail.com", "active");
        dataItemList.add(userData);

        UsersList usersList = new UsersList(dataItemList);

        usersListSingle = Single.just(usersList);

        Mockito.when(apiClient.getUsers(1)).thenReturn(usersListSingle);
        repoUsers.fetchUsersList(1);

        Assert.assertEquals(1, repoUsers.mUserdata.getValue().data.getData().size());
        Assert.assertEquals(false, repoUsers.userError.getValue());
        Assert.assertEquals(false, repoUsers.loading.getValue());
    }

    @Before
    public void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(command -> {
                    command.run();
                }, true);
            }
        };

        //.subscribeOn(Schedulers.newThread())
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);

        //.observeOn(AndroidSchedulers.mainThread())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);


        //because of this our unit test is proceed without any interruption
    }
}
