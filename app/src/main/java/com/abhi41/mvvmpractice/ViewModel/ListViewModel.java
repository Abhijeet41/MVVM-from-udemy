package com.abhi41.mvvmpractice.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.mvvmpractice.DI.DaggerMyComponent;
import com.abhi41.mvvmpractice.DI.MyComponent;
import com.abhi41.mvvmpractice.Model.CountryModel;
import com.abhi41.mvvmpractice.NetworkApiConfig.ApiClient;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public ApiClient apiClient;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ListViewModel(Application application) {
        super(application);
        DaggerMyComponent.create().inject(this);

    }


    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {
        loading.setValue(true);
        disposable.add(apiClient.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()) //its establish our communication with main thread
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {

                    @Override
                    public void onSuccess(@NonNull List<CountryModel> countryModels) {
                        countries.setValue(countryModels);

                        countryLoadError.setValue(false);
                        loading.setValue(false);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );

    }

    @Override
    protected void onCleared() {  // to prevent  memoryleak
        super.onCleared();
        disposable.clear();
    }
}
