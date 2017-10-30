package com.soundbread.history.di.module;


import com.soundbread.history.data.network.SearchHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fruitbites on 2017-09-04.
 */

@Module
public class NetworkModule {
    String mBaseUrl;

    // Constructor needs one parameter to instantiate.
    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client( httpClient.build()).build();
    }


    @Provides
    @Singleton
    SearchHelper provideApiHelper(Retrofit retrofit) {
        return retrofit.create(SearchHelper.class);
    }





}
