package com.costar.talkwithidol.ui.fragments.discover.mvp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;

import java.util.List;

/**
 * Created by dell on 8/28/2017.
 */

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int item);
    }


    private List<DiscoverDTO> discoverList;
    private final OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public ImageView img;
        public RelativeLayout rl_discover;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tv_title);
            img = (ImageView) view.findViewById(R.id.image);
            desc = (TextView) view.findViewById(R.id.tv_description);
            rl_discover = view.findViewById(R.id.rl_discover);
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


    public DiscoverAdapter(List<DiscoverDTO> discoverList,OnItemClickListener listener) {
        this.discoverList = discoverList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discovery_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bind((position), listener);

        if(position==0){

            holder.rl_discover.setBackgroundColor(Color.parseColor("#4b3e6f"));
            holder.img.setImageResource(R.drawable.events);
        }else if(position==1){
            holder.rl_discover.setBackgroundColor(Color.parseColor("#6d5385"));
            holder.img.setImageResource(R.drawable.channels);

        }
        else if(position==2){
            holder.rl_discover.setBackgroundColor(Color.parseColor("#805381"));
            holder.img.setImageResource(R.drawable.community);

        }
        else if(position==3){
            holder.rl_discover.setBackgroundColor(Color.parseColor("#864775"));
            holder.img.setImageResource(R.drawable.videos);

        }
        else if(position==4){
            holder.rl_discover.setBackgroundColor(Color.parseColor("#863445"));
            holder.img.setImageResource(R.drawable.news);

        }else if(position==5){
            holder.rl_discover.setBackgroundColor(Color.parseColor("#c02e2D"));
            holder.img.setImageResource(R.drawable.watchlist);

        }
        DiscoverDTO discover = discoverList.get(position);
        holder.title.setText(discover.getTitle());
        holder.desc.setText(discover.getDesc());
       // holder.img.setImageResource(discover.getImage());


    }

    @Override
    public int getItemCount() {
        return discoverList.size();
    }
}
