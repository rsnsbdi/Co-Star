package com.costar.talkwithidol.ui.activities.communitydetail.mvp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ChildCommentsResponse.Datum1;
import com.costar.talkwithidol.app.network.models.ChildReportid;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class ListChildCommentAdapter extends RecyclerView.Adapter<ListChildCommentAdapter.MyViewHolder> {

    Activity activity;
    private PublishSubject<Datum1> reply = PublishSubject.create();
    private List<Datum1> arrayList = new ArrayList<>();

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_comment_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_name11.setText(arrayList.get(position).getAuthorName());
        holder.tv_date_time1.setText(Html.fromHtml(arrayList.get(position).getCommentDate()));
        holder.tv_user_comment1.setText(arrayList.get(position).getComment());
        Picasso.with(activity).load(arrayList.get(position).getAuthorPicture()).into(holder.iv_profile11);
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ChildReportid reportId = new ChildReportid();
                reportId.id = Integer.parseInt(arrayList.get(position).getCommentId());
                reportId.userid = Integer.parseInt(arrayList.get(position).getAuthor_id());

                RxBus.getInstance().send(reportId);
                return false;
            }
        });
    }

    public void showList(ArrayList<Datum1> listChildCommentResponse, Activity activity) {
        this.arrayList.clear();
        this.activity = activity;
        if (listChildCommentResponse != null && !listChildCommentResponse.isEmpty())
            this.arrayList.addAll(listChildCommentResponse);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //
        public ImageView iv_profile11;
        public TextView tv_name11;
        public TextView tv_date_time1, tv_user_comment1;
        private RelativeLayout item;


        public MyViewHolder(View view) {
            super(view);
            tv_name11 = view.findViewById(R.id.tv_name11);
            tv_date_time1 = view.findViewById(R.id.tv_date_time1);
            tv_user_comment1 = view.findViewById(R.id.tv_user_comment1);
            iv_profile11 = view.findViewById(R.id.iv_profile11);
            item = view.findViewById(R.id.item);
        }
    }
}
