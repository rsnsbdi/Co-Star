package com.costar.talkwithidol.ui.fragments.home.mvp;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.promo.PromoResponse;
import com.costar.talkwithidol.databinding.PromoItemsBinding;
import com.squareup.picasso.Picasso;


public class PromoPagerAdapter extends PagerAdapter {
    Activity activity;
    private boolean isMultiScr;
    private PromoResponse promoResponse;

    public PromoPagerAdapter(Activity activity, PromoResponse promoResponse, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.promoResponse = promoResponse;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return promoResponse.getData().size() * 100;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {
        int position = position1 % promoResponse.getData().size();
        PromoItemsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.promo_items, null, false);

        binding.tvPromteTitle.setText(promoResponse.getData().get(position).getPromoName());
        binding.tvPromteDesc.setText(Html.fromHtml(promoResponse.getData().get(position).getPromoContent()));
        binding.promo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(promoResponse.getData().get(position).getpromo_link()));
            activity.startActivity(intent);
        });

        Picasso.with(activity).load(promoResponse.getData().get(position).getImageUrl()).into(binding.ivPromoteImage);


        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}
