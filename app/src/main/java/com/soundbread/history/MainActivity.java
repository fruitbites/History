package com.soundbread.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.soundbread.history.adapter.SearchAdapter;
import com.soundbread.history.data.DataManager;
import com.soundbread.history.data.network.model.Search;
import com.soundbread.history.data.network.model.SearchResponse;
import com.soundbread.history.data.network.model.Value;
import com.soundbread.history.event.IncidentSelectResult;
import com.soundbread.history.util.Highlight;
import com.soundbread.history.util.RxSearch;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject DataManager dm;

    @BindView(R.id.search_recycler_view) RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.search_layout)  ViewGroup mSearchLayout;
    @BindView(R.id.chronicle_layout) ViewGroup mChronicleLayout;
    @BindView(R.id.query_result) TextView mQueryResult;



    private SearchView mSearchView;
    private SearchAdapter mSearchAdapter;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Unbinder mUnbinder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HistoryApplication)getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(llm);
        mSearchAdapter = new SearchAdapter(this);
        mRecyclerView.setAdapter(mSearchAdapter);

        Disposable d = mSearchAdapter.getPublishSubject().subscribe(s-> {

            EventBus.getDefault().postSticky(new IncidentSelectResult(s.getId()));
            Intent i = new Intent(this,ViewActivity.class);
            startActivity(i);
        });
        mCompositeDisposable.add(d);

        setSupportActionBar(mToolbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mi = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView)mi.getActionView();
        mSearchView.setIconified(true);




        RxSearch.fromSearchView(mSearchView)
                .map(item->{
                    if (mSearchLayout.getVisibility() == View.GONE) {
                        mSearchLayout.setVisibility(View.VISIBLE);
                        mChronicleLayout.setVisibility(View.GONE);
                    }
                    return item;})
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    try {
                        Timber.d("query: " + query);
                        if (query != null && query.length() > 0) {
                            Observable<SearchResponse> r = dm.getSearchResponse("history", query, "json", 25, 0);


                            r.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
                                if (item != null && item.getValues() != null) {
                                    Timber.i("item:" + item.getQuery() + "," + item.getTotalcount());
                                    mSearchAdapter.clear();
                                    Pattern qp = Highlight.getQueryPattern(item.getQuery());


                                    mQueryResult.setText(Html.fromHtml(Highlight.multi(item.getQuery() + " " + item.getTotalcount() + "건", qp, "<b><font color='#C35231'>", "</font></b>")));

                                    mSearchAdapter.setPattern(qp);
                                    for (Value v : item.getValues()) {
                                        mSearchAdapter.add(new Search(v.getVal()));

                                    }
                                    mSearchAdapter.notifyDataSetChanged();
                                }
                                else {
                                    mQueryResult.setText("");
                                    mSearchAdapter.clear();
                                    mSearchAdapter.notifyDataSetChanged();
                                }

                            });
                        }
                        else {
                            mQueryResult.setText("");
                            mSearchAdapter.clear();
                            mSearchAdapter.notifyDataSetChanged();

                        }
                    }
                    catch(Exception ignore ) {
                        Timber.e(ignore,"RxSerch...");
                    }
                });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);

    }
    @Override
    public void onStop() {

       // EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public void onBackPressed() {

        Timber.d("onBackPressed");


        if (mSearchLayout.getVisibility() == View.VISIBLE) {
            mSearchLayout.setVisibility(View.GONE);
            mChronicleLayout.setVisibility(View.VISIBLE);
        }
        else {
            if (isTaskRoot()) {
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setContentText("종료 하시겠습니까?")
                        .setTitleText(getResources().getString(R.string.app_name))
                        .setCustomImage(R.mipmap.ic_launcher)
                        .setConfirmText("예")
                        .setCancelText("아니오")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        })
                        .show();

            } else {

                super.onBackPressed();
            }
        }

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


    @OnTouch(R.id.search_recycler_view)
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        return false;
    }

    /*
    @Subscribe
    public void onEvent(IncidentSelectResult r) {
        Timber.i("MainActivity onEvent" + r.getId());

    }
    */



}
