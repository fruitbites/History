package com.soundbread.history.di.component;

import android.app.Application;
import android.content.Context;

import com.soundbread.history.HistoryApplication;
import com.soundbread.history.MainActivity;
import com.soundbread.history.ViewActivity;
import com.soundbread.history.data.DataManager;
import com.soundbread.history.data.db.HistoryDatabaseHelper;
import com.soundbread.history.data.db.InformationDatabaseHelper;
import com.soundbread.history.data.pref.PrefsHelper;
import com.soundbread.history.di.module.ApplicationModule;
import com.soundbread.history.di.module.NetworkModule;
import com.soundbread.history.di.scope.ApplicationContext;
import com.soundbread.history.fragment.ChronicleFragment;
import com.soundbread.history.fragment.IncidentFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fruitbites on 2017-09-04.
 */


@Singleton
@Component(modules={ApplicationModule.class,NetworkModule.class})
public interface ApplicationComponent {
    void inject(HistoryApplication application);
    void inject(MainActivity mainActivity);
    void inject(ViewActivity viewActivity);
    void inject(ChronicleFragment chronicleFragment);
    void inject(IncidentFragment incidentFragment);


    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();
    PrefsHelper getPrefsHelper();

    InformationDatabaseHelper getInformationDatabaseHelper();
    HistoryDatabaseHelper getHistoryDatabaseHelper();

}
