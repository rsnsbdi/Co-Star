package com.costar.talkwithidol.ui.fragments.channelhome.mvp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.channelhome.Datum;
import com.costar.talkwithidol.databinding.CommunityListItemBinding;
import com.costar.talkwithidol.databinding.EventListItemBinding;
import com.costar.talkwithidol.databinding.NewsListItemActivityBinding;
import com.costar.talkwithidol.databinding.PromoItemsBinding;
import com.costar.talkwithidol.databinding.VideosItemBinding;
import com.costar.talkwithidol.ui.viewholder.CommunityViewHolder;
import com.costar.talkwithidol.ui.viewholder.EventViewHolder;
import com.costar.talkwithidol.ui.viewholder.NewsViewHolder;
import com.costar.talkwithidol.ui.viewholder.PromoViewHolder;
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

public class ChannelHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    private PublishSubject<Datum> clickLikeF = PublishSubject.create();
    private PublishSubject<Datum> clickDetailF = PublishSubject.create();
    private PublishSubject<Datum> clickLikeV = PublishSubject.create();
    private PublishSubject<Datum> clickDetailV = PublishSubject.create();
    private PublishSubject<Datum> clickLikeE = PublishSubject.create();
    private PublishSubject<Datum> clickDetailE = PublishSubject.create();
    private PublishSubject<Datum> clickLikeN = PublishSubject.create();
    private PublishSubject<Datum> clickDetailN = PublishSubject.create();
    private List<Datum> arrayList = new ArrayList<>();


    public void showList(AppCompatActivity activity, ArrayList<Datum> channelHomeResponse) {
        this.activity = activity;
        this.arrayList.clear();
        if (channelHomeResponse != null && !channelHomeResponse.isEmpty())
            this.arrayList.addAll(channelHomeResponse);
        notifyDataSetChanged();
    }


    public Observable<Datum> getLikeClickedVObservable() {
        return clickLikeV;
    }

    public Observable<Datum> getDetailClickedVObservable() {
        return clickDetailV;
    }

    public Observable<Datum> getLikeClickedNObservable() {
        return clickLikeN;
    }

    public Observable<Datum> getDetailClickedNObservable() {
        return clickDetailN;
    }

    public Observable<Datum> getLikeClickedEObservable() {
        return clickLikeE;
    }

    public Observable<Datum> getDetailClickedEObservable() {
        return clickDetailE;
    }

    public Observable<Datum> getLikeClickedFObservable() {
        return clickLikeF;
    }

    public Observable<Datum> getDetailClickedFObservable() {
        return clickDetailF;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                EventViewHolder viewHolder = new EventViewHolder(EventListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
                RxView.clicks(viewHolder.binding.ivAddwatchlist)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                        .subscribe(clickLikeE);
                RxView.clicks(viewHolder.binding.rlDetail)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                        .subscribe(clickDetailE);

                return viewHolder;
            case 1:
                return new PromoViewHolder(PromoItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case 2:

                VideoViewHolder videoViewHolder = new VideoViewHolder(VideosItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
                RxView.clicks(videoViewHolder.binding.ivLike)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(videoViewHolder.getAdapterPosition()))
                        .subscribe(clickLikeV);
                RxView.clicks(videoViewHolder.binding.rlDetail)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(videoViewHolder.getAdapterPosition()))
                        .subscribe(clickDetailV);
                return videoViewHolder;

            case 3:
                CommunityViewHolder communityViewHolder = new CommunityViewHolder(CommunityListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
                RxView.clicks(communityViewHolder.binding.likeView)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(communityViewHolder.getAdapterPosition()))
                        .subscribe(clickLikeF);


                RxView.clicks(communityViewHolder.binding.detailRl)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(communityViewHolder.getAdapterPosition()))
                        .subscribe(clickDetailF);
                return communityViewHolder;
            case 4:
                NewsViewHolder newsViewHolder = new NewsViewHolder(NewsListItemActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
                RxView.clicks(newsViewHolder.binding.ivLike)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(newsViewHolder.getAdapterPosition()))
                        .subscribe(clickLikeN);
                RxView.clicks(newsViewHolder.binding.rl1)
                        .takeUntil(RxView.detaches(parent))
                        .map(aVoid -> arrayList.get(newsViewHolder.getAdapterPosition()))
                        .subscribe(clickDetailN);
                return newsViewHolder;
        }


        return null;

    }

    @Override
    public int getItemViewType(int position) {

        switch (arrayList.get(position).getType()) {
            case "event":
                return 0;
            case "promo":
                return 1;
            case "video":
                return 2;
            case "forum":
                return 3;
            case "news":
                return 4;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                EventViewHolder eventViewHolder = (EventViewHolder) holder;
                eventViewHolder.binding.eventTitle.setText(arrayList.get(position).getEventName());
                eventViewHolder.binding.eventDate.setText(String.valueOf(arrayList.get(position).getEventDate().getDay()));
                eventViewHolder.binding.tvEventState.setText(arrayList.get(position).getEventState());
                eventViewHolder.binding.eventMonthYear.setText(arrayList.get(position).getEventDate().getMonth() + " " + arrayList.get(position).getEventDate().getYear());
                eventViewHolder.binding.tvEventTime.setText(String.valueOf(arrayList.get(position).getEventDate().getHour()) + ":" + String.valueOf(arrayList.get(position).getEventDate().getMinute()));
                Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(eventViewHolder.binding.eventImage);

                if (!arrayList.get(position).getEventType().equals("two-way")) {
                    eventViewHolder.binding.ivTwoway.setVisibility(View.GONE);
                }

                if (arrayList.get(position).getUserWatchlist()) {
                    eventViewHolder.binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
                    DrawableCompat.setTint(eventViewHolder.binding.ivAddwatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
                } else {
                    eventViewHolder.binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
                }

                break;

            case 1:
                PromoViewHolder promoViewHolder = (PromoViewHolder) holder;
                promoViewHolder.binding.tvPromteTitle.setText(arrayList.get(position).getPromoName());
                promoViewHolder.binding.tvPromteDesc.setText(Html.fromHtml(arrayList.get(position).getPromoContent()));

                promoViewHolder.binding.promo.setOnClickListener(v -> {

                    if (arrayList.get(position).getPromo_link() != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((arrayList.get(position).getPromo_link())));
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, "Promo Link is null", Toast.LENGTH_SHORT).show();
                    }
                });

                Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(promoViewHolder.binding.ivPromoteImage);

                break;
            case 2:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.binding.tvVideoTitle.setText(arrayList.get(position).getVideoName());
                videoViewHolder.binding.tvTalentName.setText(arrayList.get(position).getTalentName());
                videoViewHolder.binding.tvVideoTime.setText(arrayList.get(position).getVideoDate());
                Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(videoViewHolder.binding.ivVideo);
                if (arrayList.get(position).getShortDesc() == null) {
                    videoViewHolder.binding.tvVideoDesc.setText("No description");
                } else {
                    videoViewHolder.binding.tvVideoDesc.setText(Html.fromHtml(arrayList.get(position).getShortDesc()));
                }
                if (arrayList.get(position).getLikes() != null) {
                    if (arrayList.get(position).getLikes().getUserLike()) {
                        videoViewHolder.binding.ivLike.setImageResource(R.drawable.ic_liked_filled);
                    } else {
                        videoViewHolder.binding.ivLike.setImageResource(R.drawable.ic_like_border);
                    }
                }
                break;

            case 3:
                CommunityViewHolder communityViewHolder = (CommunityViewHolder) holder;
                communityViewHolder.binding.tvAuthername.setText(arrayList.get(position).getAuthorName());
                communityViewHolder.binding.tvDateTime.setText(arrayList.get(position).getForumDate());
                communityViewHolder.binding.tvTalentName.setText(arrayList.get(position).getChannelName());
                communityViewHolder.binding.tvCommunityDescription.setText(Html.fromHtml(arrayList.get(position).getShortDesc()));

                if (arrayList.get(position).getComments().getLastComment() != null) {
                    communityViewHolder.binding.tvCommenterName.setText(arrayList.get(position).getComments().getLastComment().getAuthorName());
                    communityViewHolder.binding.tvComments.setText(Html.fromHtml(arrayList.get(position).getComments().getLastComment().getComment()));
                } else {
                    communityViewHolder.binding.layoutComment.setVisibility(View.GONE);
                }

                if (arrayList.get(position).getLikes().getUserLike()) {
                    communityViewHolder.binding.likeView.setText("Liked");
                    communityViewHolder.binding.likeView.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.ic_liked_filled), null, null, null);

                } else {
                    communityViewHolder.binding.likeView.setText("Like");
                    communityViewHolder.binding.likeView.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.ic_like_border), null, null, null);

                }


                String lastLiker = arrayList.get(position).getLikes().getLastLiker();
                int totalLikes = arrayList.get(position).getLikes().getTotalLikes();
                if (arrayList.get(position).getLikes().getTotalLikes() == 0) {
                    communityViewHolder.binding.tvTotalLike.setText("");
                } else if (arrayList.get(position).getLikes().getTotalLikes() == 1) {
                    communityViewHolder.binding.tvTotalLike.setText(lastLiker);
                } else if (arrayList.get(position).getLikes().getTotalLikes() > 1) {
                    communityViewHolder.binding.tvTotalLike.setText(lastLiker + " " + "and" + " " + (totalLikes - 1) + " " + "others liked this");
                }


                Picasso.with(activity).load(arrayList.get(position).getAuthorPicture()).into(communityViewHolder.binding.ivAutherImage);


                if (arrayList.get(position).getHas_main_image()) {
                    Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(communityViewHolder.binding.ivImage);
                } else {
                    communityViewHolder.binding.ivImage.setVisibility(View.GONE);
                    communityViewHolder.binding.descriptionNoimg.setText(Html.fromHtml(arrayList.get(position).getShortDesc()));
                    communityViewHolder.binding.descriptionNoimg.setVisibility(View.VISIBLE);
                    communityViewHolder.binding.tvCommunityDescription.setVisibility(View.INVISIBLE);
                }
                break;

            case 4:
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                newsViewHolder.binding.tvNewsName.setText(arrayList.get(position).getNewsName());
                newsViewHolder.binding.tvNewsDescription.setText(arrayList.get(position).getShortDesc());
                newsViewHolder.binding.tvDateTime.setText(arrayList.get(position).getNewsDate());
                newsViewHolder.binding.tvTalentName.setText(arrayList.get(position).getChannelName());
                Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(newsViewHolder.binding.newsImage);

                if (arrayList.get(position).getLikes().getUserLike()) {
                    newsViewHolder.binding.ivLike.setImageResource(R.drawable.filled_love_icon);
                } else {
                    newsViewHolder.binding.ivLike.setImageResource(R.drawable.unfilled_love_icon);
                }
                break;
        }


    }


    public void updateE(Datum datum) {
        datum.setUserWatchlist(true);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    //for user like dislike
    public void updateDislikeE(Datum datum) {
        datum.setUserWatchlist(false);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }


    //for user like dislike
    public void update(Datum datum) {
        datum.getLikes().setUserLike(true);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    //for user like dislike
    public void updateDislike(Datum datum) {
        datum.getLikes().setUserLike(false);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }


    //for user like dislike
    public void updateC(Datum datum, LikeEntityReponse likeEntityReponse) {
        datum.getLikes().setUserLike(true);
        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();
        datum.getLikes().setTotalLikes(totalLikes);
        datum.getLikes().setLastLiker(lastLiker);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    //for user like dislike
    public void updateDislikeC(Datum datum, LikeEntityReponse likeEntityReponse) {
        datum.getLikes().setUserLike(false);
        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();
        datum.getLikes().setTotalLikes(totalLikes);
        datum.getLikes().setLastLiker(lastLiker);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




}
