package com.costar.talkwithidol.ui.fragments.channellist.mvp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreChannel.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 8/28/2017.
 */

public class ChannellisttAdapter extends RecyclerView.Adapter<ChannellisttAdapter.MyViewHolder> {

    int i = 0;
    int j = 0;
    Activity activity;
    private List<Datum> arrayList = new ArrayList<>();

    private OnItemClickListener listener;

    public void showList(Activity activity, ArrayList<Datum> exploreChannelResponse, OnItemClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.arrayList.clear();
        if (exploreChannelResponse != null && !exploreChannelResponse.isEmpty())
            this.arrayList.addAll(exploreChannelResponse);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channellist_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (arrayList.get(position).getDiagonalColor().equals("red")){
            holder.channel_view.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));

        }else{
            holder.channel_view.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient_blue));

        }



        holder.bind((position), listener);
        holder.tv_channel_name.setText(arrayList.get(position).getChannelName());
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.iv_channel_image);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_channel_name;
        public ImageView iv_channel_image;
        public View channel_view;

        public MyViewHolder(View view) {
            super(view);
            tv_channel_name = view.findViewById(R.id.tv_channel_name);
            iv_channel_image = view.findViewById(R.id.iv_channel);
            channel_view = view.findViewById(R.id.channel_view);
        }

        public void bind(final int item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}
