package com.costar.talkwithidol.ui.viewholder;

import android.support.v7.widget.RecyclerView;

import com.costar.talkwithidol.databinding.SubscribedListItemBinding;

/**
 * Created by shreedhar on 12/28/17.
 */


public class SubscriptionViewHolder extends RecyclerView.ViewHolder {

    public final SubscribedListItemBinding binding;

    public SubscriptionViewHolder(SubscribedListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
