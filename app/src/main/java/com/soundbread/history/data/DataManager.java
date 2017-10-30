package com.soundbread.history.data;

/**
 * Created by fruitbites on 2017-09-17.
 */

import android.content.Context;

import com.soundbread.history.data.db.HistoryDatabaseHelper;
import com.soundbread.history.data.db.InformationDatabaseHelper;
import com.soundbread.history.data.db.model.Content;
import com.soundbread.history.data.db.model.Incident;
import com.soundbread.history.data.network.SearchHelper;
import com.soundbread.history.data.network.model.SearchResponse;
import com.soundbread.history.data.pref.PrefsHelper;
import com.soundbread.history.di.scope.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class DataManager {

    private Context mContext;
    private InformationDatabaseHelper iHelper;
    private HistoryDatabaseHelper hHelper;

    private SearchHelper sHelper;
    private PrefsHelper pHelper;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       InformationDatabaseHelper iHelper,
                       HistoryDatabaseHelper hHelper,
                       SearchHelper sHelper,
                       PrefsHelper pHelper) {
        mContext = context;
        this.iHelper = iHelper;
        this.hHelper = hHelper;
        this.sHelper = sHelper;
        this.pHelper = pHelper;
    }

    public Observable<SearchResponse> getSearchResponse(String dic, String query, String format, int count,int start) {
        return sHelper.getSearchResponse(dic,query,format,count,start);
    }

    public List<Incident> getIncidents() {
        try {
            return iHelper.getIncidents();
        }
        catch(Exception ignore) {
            Timber.e(ignore,"getIncidents()");
            return new ArrayList<Incident>();
        }
    }

    public Incident getIncident(String id) {
        try {
            return iHelper.getIncident(id);
        }
        catch(Exception ignore) {
            return new Incident();
        }
    }


    public List<Content> getContents(String id) {
        try {
            return iHelper.getContents(id);
        }
        catch(Exception ignore) {
            Timber.e(ignore,"getContents()");
            return new ArrayList<Content>();
        }
    }

}