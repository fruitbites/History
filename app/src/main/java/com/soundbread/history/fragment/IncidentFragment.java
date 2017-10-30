package com.soundbread.history.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundbread.history.HistoryApplication;
import com.soundbread.history.R;
import com.soundbread.history.adapter.IncidentAdapter;
import com.soundbread.history.data.DataManager;
import com.soundbread.history.data.db.model.Content;
import com.soundbread.history.data.db.model.Incident;
import com.soundbread.history.event.IncidentSelectResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class IncidentFragment extends Fragment {

    public static final String INCIDENT_ID = "INCIDENT_ID";
    @Inject  DataManager dm;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private String mId;

    private IncidentAdapter mIncidentAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Unbinder mUnbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HistoryApplication)getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mIncidentAdapter = new IncidentAdapter(getContext());
        mRecyclerView.setAdapter(mIncidentAdapter);
        Disposable d = mIncidentAdapter.getPublishSubject().subscribe(s-> Timber.i(s.getTitle()));
        mCompositeDisposable.add(d);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky=true,threadMode = ThreadMode.MAIN)
    public void onIncidentSelectResult(IncidentSelectResult r) {
        mId = r.getId();

        Disposable d = getObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(item-> {
            mIncidentAdapter.add(item);
            mIncidentAdapter.add(new Content());
            mIncidentAdapter.addAll(item.getContents());
            mIncidentAdapter.add(new Content());
            mIncidentAdapter.notifyDataSetChanged();
        });
        mCompositeDisposable.add(d);
    }

    private Observable<Incident> getObservable() {
        return Observable.just(dm.getIncident(mId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
    }



}
