package com.soundbread.history.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.soundbread.history.R;
import com.soundbread.history.data.db.model.Incident;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class ChronicleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int AD = 1;
    public static final int CONTENT = 2;

    // 아이템 리스트
    private List<Incident> data = new ArrayList<Incident>();


    private PublishSubject<Incident> mPublishSubject;

    private Context mContext;


    public ChronicleAdapter(Context context) {
        this.mContext = context;
        this.mPublishSubject = PublishSubject.create();
    }

    public void add(List<Incident> incidents) {
        data.addAll(incidents);
    }

    public void add(Incident incident) {
        data.add(incident);
    }

    public void add(int index,Incident incident) { data.add(index,incident);}

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(position);
    }
    public Incident get(int position) {
        return data.get(position);
    }





    @Override
    public int getItemViewType(int position) {
        Incident incident = data.get(position);
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
                ChronicleViewHolder vh = (ChronicleViewHolder)holder;
                final Incident incident = data.get(i);
                String id = incident.getId().toLowerCase();
                try {
                    Glide.with(mContext).load(Uri.parse("file:///android_asset/thumbs/" + id + "_off.png")).into(vh.image);
                }
                catch(Exception e) {
                    Timber.i(e,"load");
                }


                vh.getClickObserver(incident).subscribe(mPublishSubject);
                break;
            }
            case AD: {
                break;
            }
        }
    }

    public PublishSubject<Incident> getPublishSubject() {
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
                View itemView = inflater.inflate(R.layout.item_chronicle, parent, false);
                holder = new ChronicleViewHolder(itemView);

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
    public class ChronicleViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.item)
        View view;

        @BindView(R.id.image)
        ImageView image;


        public ChronicleViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this,v);
        }

        Observable<Incident> getClickObserver(Incident incident)  {

            return Observable.create(e->view.setOnClickListener(view->e.onNext(incident)));
        }



    }
}