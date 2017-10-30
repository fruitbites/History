package com.soundbread.history.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundbread.history.HistoryApplication;
import com.soundbread.history.R;
import com.soundbread.history.ViewActivity;
import com.soundbread.history.adapter.ChronicleAdapter;
import com.soundbread.history.data.DataManager;
import com.soundbread.history.data.db.model.Incident;
import com.soundbread.history.event.IncidentSelectResult;

import org.greenrobot.eventbus.EventBus;

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

/**
 * Created by fruitbites on 2017-09-17.
 */

public class ChronicleFragment extends Fragment {

    @Inject DataManager dm;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private ChronicleAdapter mChronicleAdapter;
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
        View view = inflater.inflate(R.layout.fragment_chronicle, container, false);
        ButterKnife.bind(this, view);
        Disposable d = getObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(item-> {
            mChronicleAdapter.add(item);
            mChronicleAdapter.notifyDataSetChanged();
        });
        mCompositeDisposable.add(d);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        GridLayoutManager llm = new GridLayoutManager(getContext(),3);

        mRecyclerView.setLayoutManager(llm);
        mChronicleAdapter = new ChronicleAdapter(getContext());
        mRecyclerView.setAdapter(mChronicleAdapter);

        Disposable d = mChronicleAdapter.getPublishSubject().subscribe(s-> {

            EventBus.getDefault().postSticky(new IncidentSelectResult(s.getId()));
            Intent i = new Intent(getActivity(),ViewActivity.class);
            getActivity().startActivity(i);
        });
        mCompositeDisposable.add(d);
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    private Observable<Incident> getObservable() {
        return Observable.fromIterable(dm.getIncidents()).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
    }



}
