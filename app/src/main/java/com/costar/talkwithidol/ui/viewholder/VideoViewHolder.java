package com.costar.talkwithidol.ui.viewholder;

import android.support.v7.widget.RecyclerView;

import com.costar.talkwithidol.databinding.VideosItemBinding;

/**
 * Created by shreedhar on 12/28/17.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public final VideosItemBinding binding;

    public VideoViewHolder(VideosItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}