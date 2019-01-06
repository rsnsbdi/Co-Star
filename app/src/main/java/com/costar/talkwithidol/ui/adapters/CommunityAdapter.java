package com.costar.talkwithidol.ui.adapters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.DatumC;
import com.costar.talkwithidol.databinding.CommunityListItemBinding;
import com.costar.talkwithidol.ui.viewholder.CommunityViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class CommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder> {


    Activity activity;

    private PublishSubject<DatumC> clickLike = PublishSubject.create();
    private PublishSubject<DatumC> clickDetail = PublishSubject.create();
    private List<DatumC> arrayList = new ArrayList<>();

    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityViewHolder communityViewHolder = new CommunityViewHolder(CommunityListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        RxView.clicks(communityViewHolder.binding.likeView)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(communityViewHolder.getAdapterPosition()))
                .subscribe(clickLike);


        RxView.clicks(communityViewHolder.binding.detailRl)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(communityViewHolder.getAdapterPosition()))
                .subscribe(clickDetail);
        return communityViewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder communityViewHolder, int position) {

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
            communityViewHolder.binding.likeView.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.filled_love_icon), null, null, null);

        } else {
            communityViewHolder.binding.likeView.setText("Like");
            communityViewHolder.binding.likeView.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.unfilled_love_icon), null, null, null);

        }


        String lastLiker = arrayList.get(position).getLikes().getLastLiker();
        int totalLikes = arrayList.get(position).getLikes().getTotalLikes();
        if (arrayList.get(position).getLikes().getTotalLikes() == 0) {
            communityViewHolder.binding.tvTotalLike.setText("");
        } else if (arrayList.get(position).getLikes().getTotalLikes() == 1) {
            if (lastLiker.length() > 0) {
                communityViewHolder.binding.tvTotalLike.setText(lastLiker + " liked this");
            } else {
                communityViewHolder.binding.tvTotalLike.setText("");
            }
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


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public Observable<DatumC> getLikeClickedObservable() {
        return clickLike;
    }

    public Observable<DatumC> getDetailClickedObservable() {
        return clickDetail;
    }

    //for user like dislike
    public void update(DatumC datum) {
        datum.getLikes().setUserLike(true);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    //for user like dislike
    public void updateDislike(DatumC datum) {
        datum.getLikes().setUserLike(false);
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    //for user like dislike
    public void update(DatumC datumC, LikeEntityReponse likeEntityReponse) {
        datumC.getLikes().setUserLike(true);
        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();
        datumC.getLikes().setTotalLikes(totalLikes);
        datumC.getLikes().setLastLiker(lastLiker);
        notifyItemChanged(this.arrayList.indexOf(datumC), datumC);
    }

    //for user like dislike
    public void updateDislike(DatumC datumC, LikeEntityReponse likeEntityReponse) {
        datumC.getLikes().setUserLike(false);
        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();
        datumC.getLikes().setTotalLikes(totalLikes);
        datumC.getLikes().setLastLiker(lastLiker);
        notifyItemChanged(this.arrayList.indexOf(datumC), datumC);
    }

    public void showList(ArrayList<DatumC> exploreCommunityResponse, AppCompatActivity activity) {
        this.activity = activity;
        this.arrayList.clear();
        if (exploreCommunityResponse != null && !exploreCommunityResponse.isEmpty())
            this.arrayList.addAll(exploreCommunityResponse);
        notifyDataSetChanged();
    }


}
