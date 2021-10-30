package com.abhi41.mvvmpractice.viewmodelTest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.abhi41.mvvmpractice.data.Model.CountryModel;
import com.abhi41.mvvmpractice.networkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.view_model.ListViewModel;

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

public class ListViewmodelTest extends Application {


    //It simplifies that it's a rule that any task executaion will be instant.
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    ApiClient apiClient;

    @InjectMocks
    ListViewModel listViewModel = new ListViewModel(this);

    private Single<List<CountryModel>> testSingle;

    //@Before means it runs before any unit test
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCountriesSuccess(){
        CountryModel countryModel = new CountryModel("country name","capital","flag");
        List<CountryModel> countryModelList = new ArrayList<>();

        countryModelList.add(countryModel);

        testSingle = Single.just(countryModelList);

        Mockito.when(apiClient.getCountries()).thenReturn(testSingle);
        listViewModel.refresh();

        //these are the expected value for three live data which we define in viewmodel class
        Assert.assertEquals(1,listViewModel.countries.getValue().size());
        Assert.assertEquals(false,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());


    }

    @Test
    public void getCountriesfail(){

        testSingle = Single.error(new Throwable());
        Mockito.when(apiClient.getCountries()).thenReturn(testSingle);
        //these are expected value for live data which we define in viewmodel class
        listViewModel.refresh();

        Assert.assertEquals(true,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());

    }

    @Before
    public void setupRxSchedulers(){
        Scheduler immediate =  new Scheduler() {
            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(command -> {
                    command.run();
                },true);
            }
        };

        //.subscribeOn(Schedulers.newThread())
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);

        //.observeOn(AndroidSchedulers.mainThread())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);


        //because of this our unit test is proceed without any interruption
    }


}
