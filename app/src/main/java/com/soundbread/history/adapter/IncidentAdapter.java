package com.soundbread.history.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soundbread.history.R;
import com.soundbread.history.data.db.model.Content;
import com.soundbread.history.data.db.model.Incident;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static com.soundbread.history.R.id.item;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class IncidentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int HEADER = 0;
    public static final int CONTENT = 1;
    public static final int FOOTER = 2;


    // 아이템 리스트
    private List<Content> data = new ArrayList<Content>();

    private Incident mIncident;


    private PublishSubject<Content> mPublishSubject;

    private Context mContext;


    public IncidentAdapter(Context context) {
        this.mContext = context;
        this.mPublishSubject = PublishSubject.create();
    }

    public void add(Incident incident) {
        this.mIncident = incident;
    }

    public void add(List<Content> contents) {
        data.addAll(contents);
    }

    public void add(Content content) {
        data.add(content);
    }
    public void addAll(List<Content> contents) {
        data.addAll(contents);
    }

    public void add(int index,Content content) { data.add(index,content);}

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(position);
    }
    public Content get(int position) {
        return data.get(position);
    }


    private boolean isPositionHeader (int position) {
        return position == 0;
    }
    private boolean isPositionFooter (int position) {
        return position == data.size () + 1;
    }

    @Override
    public int getItemViewType(int position) {
        Content content = data.get(position);
        if(isPositionHeader (position)) {
            return HEADER;
        } else if(isPositionFooter (position)) {
            return FOOTER;
        }
        else {
            return CONTENT;
        }
    }


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
                ContentViewHolder vh = (ContentViewHolder)holder;
                final Content content = data.get(i);
                vh.title.setText(content.getTitle());
                vh.content.setText(content.getContent());


                vh.getClickObserver(content).subscribe(mPublishSubject);
                break;
            }
            case HEADER: {
                HeaderViewHolder vh = (HeaderViewHolder)holder;
                vh.name.setText(mIncident.getName());
                vh.subname.setText(mIncident.getName());
                vh.desc.setText(mIncident.getDesc());
                vh.birth.setText(mIncident.getBirth());
                vh.period.setText(mIncident.getPeriod());
                vh.alias.setText(mIncident.getAlias());
                try {
                    Glide.with(mContext).load(Uri.parse("file:///android_asset/photos/" + mIncident.getId().toLowerCase()+ ".jpg")).into(vh.photo);
                }
                catch(Exception e) {
                    Timber.i(e,"load");
                }


                break;
            }
            case FOOTER: {
                FooterViewHolder vh = (FooterViewHolder)holder;
                vh.name.setText(mIncident.getName());
                vh.subname.setText(mIncident.getName());
                vh.desc.setText(mIncident.getDesc());
                vh.birth.setText(mIncident.getBirth());
                vh.period.setText(mIncident.getPeriod());
                vh.alias.setText(mIncident.getAlias());

                break;
            }
        }
    }

    public PublishSubject<Content> getPublishSubject() {
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
                View itemView = inflater.inflate(R.layout.item_content, parent, false);
                holder = new ContentViewHolder(itemView);

                break;
            }
            case HEADER : {
                View v = inflater.inflate(R.layout.item_header, parent, false);
                holder = new HeaderViewHolder(v);
                break;
            }
            case FOOTER: {
                View v = inflater.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHolder(v);
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
    public class HeaderViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.header) View view;
        @BindView(R.id.name)TextView name;
        @BindView(R.id.subname) TextView subname;
        @BindView(R.id.desc) TextView desc;
        @BindView(R.id.photo) ImageView photo;
        @BindView(R.id.birth) TextView birth;
        @BindView(R.id.period) TextView period;
        @BindView(R.id.alias) TextView alias;

        public HeaderViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this,v);
        }



    }



    /**
     * The Class CardViewHolder is the View Holder class for Adapter views.
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(item)
        View view;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;


        public ContentViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this,v);
        }

        Observable<Content> getClickObserver(Content content)  {

            return Observable.create(e->view.setOnClickListener(view->e.onNext(content)));
        }

        @OnClick
        public void click() {
            Timber.d("click : " + this.getAdapterPosition());
        }


    }

    /**
     * The Class CardViewHolder is the View Holder class for Adapter views.
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.footer) View view;
        @BindView(R.id.name)TextView name;
        @BindView(R.id.subname) TextView subname;
        @BindView(R.id.desc) TextView desc;
        @BindView(R.id.photo) ImageView photo;
        @BindView(R.id.birth) TextView birth;
        @BindView(R.id.period) TextView period;
        @BindView(R.id.alias) TextView alias;

        public FooterViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this,v);
        }


    }

}