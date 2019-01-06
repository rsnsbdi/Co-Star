package com.costar.talkwithidol.ui.fragments.watchlist.mvp;

import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreEvent.DatumE;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.databinding.EventListItemBinding;
import com.costar.talkwithidol.ui.viewholder.EventViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class WatchListAdapter extends RecyclerView.Adapter<EventViewHolder> {


    ExploreEventResponse exploreEventResponse;
    AppCompatActivity activity;

    private PublishSubject<DatumE> clickAddtoWatchlist = PublishSubject.create();
    private PublishSubject<DatumE> clickDetail = PublishSubject.create();
    private List<DatumE> arrayList = new ArrayList<>();



    public void showList(AppCompatActivity activity, ArrayList<DatumE> exploreEventResponse) {
        this.activity =activity;
        this.arrayList.clear();
        if (exploreEventResponse != null && !exploreEventResponse.isEmpty())
            this.arrayList.addAll(exploreEventResponse);
        notifyDataSetChanged();
    }

    public void update(DatumE datumE)
    {
        datumE.setUserWatchlist(false);
//        notifyItemChanged(this.arrayList.indexOf(datumE), datumE);
        notifyItemRemoved(this.arrayList.indexOf(datumE));
        arrayList.remove(arrayList.indexOf(datumE));
        notifyDataSetChanged();
    }



    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        EventViewHolder viewHolder = new EventViewHolder(EventListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));


        RxView.clicks(viewHolder.binding.ivAddwatchlist)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickAddtoWatchlist);


        RxView.clicks(viewHolder.binding.rlDetail)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickDetail);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {


        holder.binding.eventTitle.setText(arrayList.get(position).getEventName());
        holder.binding.eventDate.setText(String.valueOf(arrayList.get(position).getEventDate().getDay()));
        holder.binding.tvEventState.setText(arrayList.get(position).getEventState());
        holder.binding.eventMonthYear.setText(arrayList.get(position).getEventDate().getMonth() + " " + arrayList.get(position).getEventDate().getYear());
        holder.binding.tvEventTime.setText(String.valueOf(arrayList.get(position).getEventDate().getHour()) + ":" + String.valueOf(arrayList.get(position).getEventDate().getMinute()));
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.binding.eventImage);

        if (!arrayList.get(position).getEventType().equals("two-way")) {
            holder.binding.ivTwoway.setVisibility(View.GONE);
        }

        if (arrayList.get(position).getUserWatchlist()) {
            holder.binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
            DrawableCompat.setTint(holder.binding.ivAddwatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
        } else {
            holder.binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
        }

    }


    public Observable<DatumE> getRemoveFromWatchlistObservable() {
        return clickAddtoWatchlist;
    }

    public Observable<DatumE> getDetailClickedObservable() {
        return clickDetail;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
