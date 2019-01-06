package com.costar.talkwithidol.ui.adapters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreVideos.DatumV;
import com.costar.talkwithidol.databinding.VideosItemBinding;
import com.costar.talkwithidol.ui.viewholder.VideoViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private List<DatumV> arrayList = new ArrayList<>();

    private PublishSubject<DatumV> clickLike = PublishSubject.create();
    private PublishSubject<DatumV> clickDetail = PublishSubject.create();
    Activity activity;


    public Observable<DatumV> getLikeClickedObservable() {
        return clickLike;
    }

    public Observable<DatumV> getDetailClickedObservable() {
        return clickDetail;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoViewHolder viewHolder = new VideoViewHolder(VideosItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        RxView.clicks(viewHolder.binding.ivLike)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickLike);


        RxView.clicks(viewHolder.binding.rlDetail)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickDetail);


        return viewHolder;
    }

    //for user like dislike
    public void update(DatumV datum)
    {
        datum.getLikes().setUserLike(true);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    //for user like dislike
    public void updateDislike(DatumV datum)
    {
        datum.getLikes().setUserLike(false);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }



    public void showList(ArrayList<DatumV> exploreVideosResponse, AppCompatActivity activity) {
        this.activity = activity;
        this.arrayList.clear();
        if (exploreVideosResponse != null && !exploreVideosResponse.isEmpty())
            this.arrayList.addAll(exploreVideosResponse);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.binding.tvVideoTitle.setText(arrayList.get(position).getVideoName());
        holder.binding.tvTalentName.setText(arrayList.get(position).getTalentName());
        holder.binding.tvVideoTime.setText(arrayList.get(position).getVideoDate());
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.binding.ivVideo);
        if(arrayList.get(position).getShortDesc()==null){
            holder.binding.tvVideoDesc.setText("No description");
        }else{
            holder.binding.tvVideoDesc.setText(Html.fromHtml(arrayList.get(position).getShortDesc()));
        }
        if(arrayList.get(position).getLikes()!=null) {
            if (arrayList.get(position).getLikes().getUserLike()) {
                holder.binding.ivLike.setImageResource(R.drawable.ic_liked_filled);
            } else {
                holder.binding.ivLike.setImageResource(R.drawable.ic_like_border);
            }
        }

    }


    @Override
    public int getItemCount() {
        return  arrayList.size();
    }
}
