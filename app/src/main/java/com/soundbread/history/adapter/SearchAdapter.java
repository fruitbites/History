package com.soundbread.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soundbread.history.R;
import com.soundbread.history.data.network.model.Search;
import com.soundbread.history.util.Highlight;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int AD = 1;
    public static final int CONTENT = 2;

    // 아이템 리스트
    private List<Search> data = new ArrayList<Search>();
    private PublishSubject<Search> mPublishSubject;
    private Context mContext;
    private Pattern qp;
    public SearchAdapter(Context context) {
        this.mContext = context;
        this.mPublishSubject = PublishSubject.create();
    }
    public PublishSubject<Search> getPublishSubject() {
        return mPublishSubject;
    }
    public void setPattern(Pattern qp) {
        this.qp = qp;
    }

    public void addAll(List<Search> searchs) {
        data.addAll(searchs);
    }

    public void add(Search search) {
        data.add(search);
    }

    public void add(int index,Search search) { data.add(index,search);}

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(position);
    }
    public Search get(int position) {
        return data.get(position);
    }





    @Override
    public int getItemViewType(int position) {
        Search search = data.get(position);
        if (false) {
            return AD;
        }
        else {
            return CONTENT;
        }
    }


    /* (non-Javadoc)
     * @see android.support.v7.widget_info.RecyclerView.Adapter#getItemCount()
     */
    @Override
    public int getItemCount()
    {
        return data.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i)
    {

        switch(holder.getItemViewType()) {
            case CONTENT : {
                SearchViewHolder vh = (SearchViewHolder)holder;
                final Search search = data.get(i);
                try {
                    if (qp != null) {

                        vh.name.setText(Html.fromHtml(Highlight.multi(search.getName(), qp, "<b><font color='#C35231'>", "</font></b>")));
                        vh.chinese.setText(Html.fromHtml(Highlight.multi(search.getChinese(), qp, "<b><font color='#C35231'>", "</font></b>")));
                        vh.desc.setText(Html.fromHtml(Highlight.multi(search.getDesc(), qp, "<b><font color='#C35231'>", "</font></b>")));
                        vh.event.setText(Html.fromHtml(Highlight.multi(search.getEvent(), qp, "<b><font color='#C35231'>", "</font></b>")));
                        vh.organization.setText(Html.fromHtml(Highlight.multi(search.getOrganization(), qp, "<b><font color='#C35231'>", "</font></b>")));
                    } else {
                        vh.name.setText(search.getName());
                        vh.chinese.setText(search.getChinese());
                        vh.desc.setText(search.getDesc());
                        vh.event.setText(search.getEvent());
                        vh.organization.setText(search.getOrganization());
                    }
                }
                catch(Exception ignore) { }
                vh.getClickObserver(search).subscribe(mPublishSubject);
                break;
            }
            case AD: {
                break;
            }
        }
    }

    public PublishSubject<Search> getmPublishSubject() {
        return mPublishSubject;
    }

    /* (non-Javadoc)
     * @see android.support.v7.widget_info.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {



        RecyclerView.ViewHolder holder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType) {
            case CONTENT : {
                View itemView = inflater.inflate(R.layout.item_search, parent, false);
                holder = new SearchViewHolder(itemView);

                break;
            }
            case AD : {
                View v = inflater.inflate(R.layout.item_ad, parent, false);
                holder = new AdViewHolder(v);
                break;
            }
        }

        return holder;
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        
        public AdViewHolder(View view) {
            super(view);
        }
    }

    /**
     * The Class CardViewHolder is the View Holder class for Adapter views.
     */
    public class SearchViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.item)
        View view;

        @BindView(R.id.name)   TextView name;
        @BindView(R.id.chinese)  TextView chinese;
        @BindView(R.id.desc)  TextView desc;
        @BindView(R.id.event) TextView event;
        @BindView(R.id.organization)   TextView organization;

        public SearchViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this,v);
        }

        Observable<Search> getClickObserver(Search search)  {

            return Observable.create(e->view.setOnClickListener(view->e.onNext(search)));
        }



    }
}