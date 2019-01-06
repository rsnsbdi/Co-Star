package com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.eventDetail.QuestionList;

import java.util.ArrayList;
import java.util.List;



public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {


    Activity activity;

    private List<QuestionList> arrayList = new ArrayList<>();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_items, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name11.setText(arrayList.get(position).getFirstName()+" "+ arrayList.get(position).getLastName());
       // holder.tv_date_time1.setData(Html.fromHtml(arrayList.get(position).getCommentDate()));
        holder.tv_user_comment1.setText(arrayList.get(position).getQuestion());
        //Picasso.with(activity).load(arrayList.get(position).getAuthorPicture()).into(holder.iv_profile11);

    }

    public void showList(List<QuestionList> childComment) {

        this.arrayList = childComment;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_profile11;
        public TextView tv_name11;
        public TextView tv_date_time1, tv_user_comment1;

        public MyViewHolder(View view) {
            super(view);
            tv_name11 = view.findViewById(R.id.tv_name11);
            tv_date_time1 = view.findViewById(R.id.tv_date_time1);
            tv_user_comment1 = view.findViewById(R.id.tv_user_comment1);
            iv_profile11 = view.findViewById(R.id.iv_profile11);
        }
    }
}
