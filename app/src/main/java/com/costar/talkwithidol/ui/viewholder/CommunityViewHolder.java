package com.costar.talkwithidol.ui.viewholder;

import android.support.v7.widget.RecyclerView;

import com.costar.talkwithidol.databinding.CommunityListItemBinding;

/**
 * Created by shreedhar on 12/28/17.
 */

public class CommunityViewHolder extends RecyclerView.ViewHolder {

    public final CommunityListItemBinding binding;

    public CommunityViewHolder(CommunityListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}