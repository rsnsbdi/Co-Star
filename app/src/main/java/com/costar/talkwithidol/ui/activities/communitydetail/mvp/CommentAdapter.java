package com.costar.talkwithidol.ui.activities.communitydetail.mvp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.ReportId;
import com.costar.talkwithidol.app.network.models.commentsResponse.CommentsResponse;
import com.costar.talkwithidol.app.network.models.commentsResponse.Datum;
import com.costar.talkwithidol.app.network.models.communityDetail.CommunityDetailResponse;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    CommentsResponse commentsResponse;
    Activity activity;
    CommunityDetailResponse communityDetailResponse = new CommunityDetailResponse();
    TextView likeText, tv_total_love;
    ImageView likeicon;

    private PublishSubject<String> reply = PublishSubject.create();
    private PublishSubject<String> viewall = PublishSubject.create();
    private List<Datum> arrayList = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public int getType() {
        return TYPE_HEADER;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.communitydetailheder, parent, false);
            CommunityHeaderViewHolder communityHeaderViewHolder = new CommunityHeaderViewHolder(v);
            communityHeaderViewHolder.setIsRecyclable(false);
            return communityHeaderViewHolder;

        } else if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_comment_items, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(v);
            viewHolder.setIsRecyclable(false);

            RxView.clicks(viewHolder.btn_viewall)
                    .takeUntil(RxView.detaches(parent))
                    .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition() - 1).getCommentId())
                    .subscribe(viewall);


            RxView.clicks(viewHolder.btn_reply)
                    .takeUntil(RxView.detaches(parent))
                    .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition() - 1).getCommentId())
                    .subscribe(reply);

            return viewHolder;
        }
        return null;
    }


    public void update(LikeEntityReponse likeEntityReponse) {

        if (likeEntityReponse.getData().getStatus().equals("liked")) {
            communityDetailResponse.getData().getLikes().setUserLike(true);
//            likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_liked_filled));
//            likeText.setText("Liked");
        } else {
            communityDetailResponse.getData().getLikes().setUserLike(false);
//            likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_like_border));
//            likeText.setText("Like");
        }
        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();

        communityDetailResponse.getData().getLikes().setTotalLikes(totalLikes);
        communityDetailResponse.getData().getLikes().setLastLiker(lastLiker);
        notifyItemChanged(0);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommunityHeaderViewHolder) {
            CommunityHeaderViewHolder myViewHolder = (CommunityHeaderViewHolder) holder;
            myViewHolder.tv_community_Description.setText(Html.fromHtml(communityDetailResponse.getData().getDescription()));
            myViewHolder.likeview.setOnClickListener(v -> {
                RxBus.getInstance().send("likeclicked");
            });
            if (communityDetailResponse.getData().getLikes().getUserLike()) {
                likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.filled_love_icon));
                likeText.setText("Liked");
            } else {
                likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.unfilled_love_icon));
                likeText.setText("Like");
            }

            String lastLiker = communityDetailResponse.getData().getLikes().getLastLiker();
            int totalLikes = communityDetailResponse.getData().getLikes().getTotalLikes();
            if (communityDetailResponse.getData().getLikes().getTotalLikes() == 0) {
                tv_total_love.setText("");
            } else if (communityDetailResponse.getData().getLikes().getTotalLikes() == 1) {
                tv_total_love.setText(lastLiker + " liked this");
            } else if (communityDetailResponse.getData().getLikes().getTotalLikes() > 1) {
                tv_total_love.setText(lastLiker + " " + "and" + " " + totalLikes + " " + "others liked this");
            }

            if (communityDetailResponse.getData().getHas_main_image()) {
                Picasso.with(activity).load(communityDetailResponse.getData().getImageUrl()).into(myViewHolder.communityImage);
            } else {
                myViewHolder.communityImage.setVisibility(View.GONE);
                myViewHolder.desc_noimg.setText(Html.fromHtml(communityDetailResponse.getData().getDescription()));
                myViewHolder.desc_noimg.setVisibility(View.VISIBLE);
                myViewHolder.tv_community_Description.setVisibility(View.GONE);
            }


        } else if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tv_name1.setText(arrayList.get(position - 1).getAuthorName());
            myViewHolder.tv_date_time.setText(Html.fromHtml(arrayList.get(position - 1).getCommentDate()));
            myViewHolder.tv_user_comment.setText(arrayList.get(position - 1).getComment());
            Picasso.with(activity).load(arrayList.get(position - 1).getAuthorPicture()).into(myViewHolder.parent_profile);

            if (arrayList.get(position - 1).getChildCommentsAvailable()) {
                myViewHolder.btn_viewall.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.btn_viewall.setVisibility(View.GONE);
            }

            if (arrayList.get(position - 1).getChildCommentsAvailable()) {
                if (arrayList.get(position - 1).getChildComments().size() == 1) {
                    myViewHolder.child1.setVisibility(View.VISIBLE);
                    myViewHolder.child2.setVisibility(View.GONE);
                    myViewHolder.childCommenterName1.setText(arrayList.get(position - 1).getChildComments().get(0).getAuthorName());
                    myViewHolder.childCommentDate1.setText(Html.fromHtml(arrayList.get(position - 1).getChildComments().get(0).getCommentDate()));
                    myViewHolder.childCommentDescription1.setText(arrayList.get(position - 1).getChildComments().get(0).getComment());
                    Picasso.with(activity).load(arrayList.get(position - 1).getChildComments().get(0).getAuthorPicture()).into(myViewHolder.childCommenterImage1);
                    myViewHolder.child1.setOnLongClickListener(v -> {
                        ReportId reportId = new ReportId();
                        reportId.id = Integer.parseInt(arrayList.get(position - 1).getChildComments().get(0).getCommentId());
                        reportId.userid = Integer.parseInt(arrayList.get(position - 1).getChildComments().get(0).getAuthor_id());
                        RxBus.getInstance().send(reportId);
                        return false;
                    });

                } else if (arrayList.get(position - 1).getChildComments().size() == 2) {
                    myViewHolder.child1.setVisibility(View.VISIBLE);
                    myViewHolder.childCommenterName1.setText(arrayList.get(position - 1).getChildComments().get(0).getAuthorName());
                    myViewHolder.childCommentDate1.setText(Html.fromHtml(arrayList.get(position - 1).getChildComments().get(0).getCommentDate()));
                    myViewHolder.childCommentDescription1.setText(arrayList.get(position - 1).getChildComments().get(0).getComment());
                    Picasso.with(activity).load(arrayList.get(position - 1).getChildComments().get(0).getAuthorPicture()).into(myViewHolder.childCommenterImage1);
                    myViewHolder.child1.setOnLongClickListener(v -> {
                        ReportId reportId = new ReportId();
                        reportId.id = Integer.parseInt(arrayList.get(position - 1).getChildComments().get(0).getCommentId());
                        reportId.userid = Integer.parseInt(arrayList.get(position - 1).getChildComments().get(0).getAuthor_id());
                        RxBus.getInstance().send(reportId);
                        return false;
                    });


                    myViewHolder.child2.setVisibility(View.VISIBLE);
                    myViewHolder.childCommenterName2.setText(arrayList.get(position - 1).getChildComments().get(1).getAuthorName());
                    myViewHolder.childCommentDate2.setText(Html.fromHtml(arrayList.get(position - 1).getChildComments().get(1).getCommentDate()));
                    myViewHolder.childCommentDescription2.setText(arrayList.get(position - 1).getChildComments().get(1).getComment());
                    Picasso.with(activity).load(arrayList.get(position - 1).getChildComments().get(1).getAuthorPicture()).into(myViewHolder.childCommenterImage2);
                    myViewHolder.child2.setOnLongClickListener(v -> {
                        ReportId reportId = new ReportId();
                        reportId.id = Integer.parseInt(arrayList.get(position - 1).getChildComments().get(1).getCommentId());
                        reportId.userid = Integer.parseInt(arrayList.get(position - 1).getChildComments().get(1).getAuthor_id());
                        RxBus.getInstance().send(reportId);
                        return false;
                    });
                }
            } else {
                myViewHolder.child1.setVisibility(View.GONE);
                myViewHolder.child2.setVisibility(View.GONE);
//        }
            }

            myViewHolder.parent.setOnLongClickListener(v -> {
                ReportId reportId = new ReportId();
                reportId.id = Integer.parseInt(arrayList.get(position - 1).getCommentId());
                reportId.userid = Integer.parseInt(arrayList.get(position - 1).getAuthor_id());
                RxBus.getInstance().send(reportId);
                return false;
            });


        }
    }


    public void showList(AppCompatActivity activity, ArrayList<Datum> commentsResponse, CommunityDetailResponse communityDetailResponse) {
        this.activity = activity;
        this.communityDetailResponse = communityDetailResponse;
        this.arrayList.clear();
        if (commentsResponse != null && !commentsResponse.isEmpty())
            this.arrayList.addAll(commentsResponse);
        notifyDataSetChanged();
    }


    public Observable<String> getReplyObservable() {
        return reply;
    }

    public Observable<String> getViewAllObservable() {
        return viewall;
    }

    @Override
    public int getItemCount() {
        if (arrayList.size()>0){
            return arrayList.size()+1;
        }else{
            return arrayList.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView parent_profile, childCommenterImage1, childCommenterImage2, childreplyicon1, childreplyicon2;
        TextView tv_name1, childCommenterName1, childCommenterName2, childCommentDate1, childCommentDate2;
        TextView tv_date_time, tv_user_comment, childCommentDescription1, childCommentDescription2;
        Button btn_reply, btn_viewall;
        RelativeLayout child1, child2, parent;


        public MyViewHolder(View view) {
            super(view);
            tv_name1 = view.findViewById(R.id.tv_name1);
            tv_date_time = view.findViewById(R.id.tv_date_time);
            tv_user_comment = view.findViewById(R.id.tv_user_comment);
            btn_viewall = view.findViewById(R.id.btn_viewall);
            btn_reply = view.findViewById(R.id.btn_reply);
            parent_profile = view.findViewById(R.id.parent_profile);

            childCommenterImage1 = view.findViewById(R.id.iv_profile11);
            childCommenterImage2 = view.findViewById(R.id.iv_profile1);

            childreplyicon1 = view.findViewById(R.id.iv_rep);
            childreplyicon2 = view.findViewById(R.id.iv_rep2);

            childCommenterName1 = view.findViewById(R.id.tv_name11);
            childCommenterName2 = view.findViewById(R.id.tv_name2);

            childCommentDate1 = view.findViewById(R.id.tv_date_time1);
            childCommentDate2 = view.findViewById(R.id.tv_date_time2);

            childCommentDescription1 = view.findViewById(R.id.tv_user_comment1);
            childCommentDescription2 = view.findViewById(R.id.tv_user_comment2);

            child1 = view.findViewById(R.id.child1);
            child2 = view.findViewById(R.id.child2);


            parent = view.findViewById(R.id.parent);


        }
    }


    class CommunityHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView communityImage;

        TextView tv_community_Description, desc_noimg;
        RelativeLayout likeview;

        public CommunityHeaderViewHolder(View v) {
            super(v);

            communityImage = v.findViewById(R.id.iv_image);
            likeicon = v.findViewById(R.id.iv_like);
            likeview = v.findViewById(R.id.like_view);
            likeText = v.findViewById(R.id.tv_like);
            tv_total_love = v.findViewById(R.id.tv_total_love);
            desc_noimg = v.findViewById(R.id.description_noimg);
            tv_community_Description = v.findViewById(R.id.tv_community_Description);


        }
    }
}
