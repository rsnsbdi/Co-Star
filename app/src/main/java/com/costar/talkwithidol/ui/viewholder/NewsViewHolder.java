package com.costar.talkwithidol.ui.viewholder;

import android.support.v7.widget.RecyclerView;

import com.costar.talkwithidol.databinding.NewsListItemActivityBinding;

/**
 * Created by shreedhar on 12/28/17.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public final NewsListItemActivityBinding binding;

    public NewsViewHolder(NewsListItemActivityBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}