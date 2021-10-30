package com.abhi41.mvvmpractice.di;

import com.abhi41.mvvmpractice.networkApiConfig.ApiClient;
import com.abhi41.mvvmpractice.networkApiConfig.ApiServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class ApiModule {
    private static final String BASE_URL = "https://raw.githubusercontent.com";
    private static final String BASE_URL_User = "https://gorest.co.in/";

    @Named("RetrofitClient")
    @Provides
    public ApiServices getRetrofitCLient()  //let the dagger system knows used to provide something relevent
    {

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit.Builder builder = new Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()) //to get JsonArry response in string
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL);

        return builder.build().create(ApiServices.class);

    }


    @Named("UsersClient")
    @Provides
    ApiServices getRetrofitUsersClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()) //to get JsonArry response in string
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL_User);

        return builder.build().create(ApiServices.class);

    }



    @Provides
    public ApiClient providerApiservice() {
        return ApiClient.getInstance();
    }
}
