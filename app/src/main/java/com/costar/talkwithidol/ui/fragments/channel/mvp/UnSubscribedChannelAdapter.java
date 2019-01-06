package com.costar.talkwithidol.ui.fragments.channel.mvp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ChannelSubscriptionResponse;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.UnsubscribedChannel;
import com.costar.talkwithidol.databinding.SubscribedListItemBinding;
import com.costar.talkwithidol.ui.viewholder.SubscriptionViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class UnSubscribedChannelAdapter extends RecyclerView.Adapter<SubscriptionViewHolder> {

    ChannelSubscriptionResponse channelSubscriptionResponse;
    Activity activity;
    private List<UnsubscribedChannel> arrayList = new ArrayList<>();
    private PublishSubject<UnsubscribedChannel> clickToggle = PublishSubject.create();


    public void showList(ChannelSubscriptionResponse channelSubscriptionResponse, Activity activity) {
        this.activity = activity;
        this.channelSubscriptionResponse = channelSubscriptionResponse;
        this.arrayList = channelSubscriptionResponse.getData().getUnsubscribedChannels();
        notifyDataSetChanged();
    }

    @Override
    public SubscriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SubscriptionViewHolder viewHolder = new SubscriptionViewHolder(SubscribedListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        RxView.clicks(viewHolder.binding.toggleButton)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickToggle);
        return viewHolder;

    }

    public Observable<UnsubscribedChannel> getToggleClickObservable() {
        return clickToggle;
    }

    @Override
    public void onBindViewHolder(SubscriptionViewHolder holder, int position) {
        holder.binding.channelName.setText(arrayList.get(position).getChannelName());
        holder.binding.description.setText(arrayList.get(position).getShortDesc());
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.binding.channelImage);
        holder.binding.toggleButton.setImageDrawable(activity.getDrawable(R.drawable.toggle_off));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
