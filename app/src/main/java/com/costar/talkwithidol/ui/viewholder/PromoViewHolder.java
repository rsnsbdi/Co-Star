package com.costar.talkwithidol.ui.viewholder;

import android.support.v7.widget.RecyclerView;

import com.costar.talkwithidol.databinding.PromoItemsBinding;

/**
 * Created by shreedhar on 12/28/17.
 */

public class PromoViewHolder extends RecyclerView.ViewHolder {

    public final PromoItemsBinding binding;

    public PromoViewHolder(PromoItemsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}