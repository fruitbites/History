package com.soundbread.history.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.soundbread.history.di.scope.ApplicationContext;
import com.soundbread.history.di.scope.DatabaseInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fruitbites on 2017-09-04.
 */

@Module
public class ApplicationModule {
    Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }


    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "history.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 1;
    }



    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return mApplication.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }








}
