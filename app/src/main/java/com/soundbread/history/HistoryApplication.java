package com.soundbread.history;

import android.app.Application;
import android.content.Context;

import com.google.firebase.crash.FirebaseCrash;
import com.soundbread.history.di.component.ApplicationComponent;
import com.soundbread.history.di.component.DaggerApplicationComponent;
import com.soundbread.history.di.module.ApplicationModule;
import com.soundbread.history.di.module.NetworkModule;

import timber.log.Timber;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class HistoryApplication extends Application {
    protected ApplicationComponent applicationComponent;

    /*
    @Inject
    DataManager dataManager;
    */

    public static HistoryApplication get(Context context) {
        return (HistoryApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("http://www.soundbread.com:16912/"))
                .build();
        applicationComponent.inject(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        }
        else {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    FirebaseCrash.report(ex);
                }
            });
        }
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
