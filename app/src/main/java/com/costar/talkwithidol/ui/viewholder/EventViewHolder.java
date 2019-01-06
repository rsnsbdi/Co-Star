package com.costar.talkwithidol.ui.viewholder;

import android.support.v7.widget.RecyclerView;

import com.costar.talkwithidol.databinding.EventListItemBinding;

/**
 * Created by shreedhar on 12/28/17.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {

    public final EventListItemBinding binding;

    public EventViewHolder(EventListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}