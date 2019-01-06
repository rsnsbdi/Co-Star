package com.costar.talkwithidol.ui.adapters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreNews.DatumN;
import com.costar.talkwithidol.databinding.NewsListItemActivityBinding;
import com.costar.talkwithidol.ui.viewholder.NewsViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class NewsAdapterN extends RecyclerView.Adapter<NewsViewHolder> {


    Activity activity;

    private PublishSubject<DatumN> clickLike = PublishSubject.create();
    private PublishSubject<DatumN> clickDetail = PublishSubject.create();
    private List<DatumN> arrayList = new ArrayList<>();

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsViewHolder viewHolder = new NewsViewHolder(NewsListItemActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));


        RxView.clicks(viewHolder.binding.ivLike)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickLike);


        RxView.clicks(viewHolder.binding.rl1)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickDetail);

        return viewHolder;
    }


    //for user like dislike
    public void update(DatumN datumN) {
        datumN.getLikes().setUserLike(true);
        notifyItemChanged(this.arrayList.indexOf(datumN), datumN);
    }

    //for user like dislike
    public void updateDislike(DatumN datumN) {
        datumN.getLikes().setUserLike(false);
        notifyItemChanged(this.arrayList.indexOf(datumN), datumN);
    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {


        holder.binding.tvNewsName.setText(arrayList.get(position).getNewsName());
        holder.binding.tvNewsDescription.setText(arrayList.get(position).getShortDesc());
        holder.binding.tvDateTime.setText(arrayList.get(position).getNewsDate());
        holder.binding.tvTalentName.setText(arrayList.get(position).getChannelName());

        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.binding.newsImage);

        if (arrayList.get(position).getLikes().getUserLike()) {
            holder.binding.ivLike.setImageResource(R.drawable.ic_liked_filled);
        } else {
            holder.binding.ivLike.setImageResource(R.drawable.ic_like_border);
        }

    }

    public void showList(ArrayList<DatumN> exploreNewsResponse, AppCompatActivity activity) {
        this.arrayList.clear();
        this.activity = activity;
        if (exploreNewsResponse != null && !exploreNewsResponse.isEmpty())
            this.arrayList.addAll(exploreNewsResponse);
        notifyDataSetChanged();
    }

    public Observable<DatumN> getLikeClickedObservable() {
        return clickLike;
    }

    public Observable<DatumN> getDetailClickedObservable() {
        return clickDetail;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
