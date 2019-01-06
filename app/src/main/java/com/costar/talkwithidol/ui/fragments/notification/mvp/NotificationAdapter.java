package com.costar.talkwithidol.ui.fragments.notification.mvp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.notificationlist.Datum;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dell on 8/28/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Activity activity;
    private ArrayList<Datum> arrayList = new ArrayList<>();
    private PublishSubject<Datum> clickDetail = PublishSubject.create();

    public NotificationAdapter(Activity activity) {
        this.activity = activity;
    }

    public void showNotificationlist(ArrayList<Datum> notificationList, AppCompatActivity activity) {

        this.activity = activity;
        this.arrayList.clear();
        if (notificationList != null && !notificationList.isEmpty())
            this.arrayList.addAll(notificationList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);

        RxView.clicks(viewHolder.rl_detail)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> arrayList.get(viewHolder.getAdapterPosition()))
                .subscribe(clickDetail);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.desc.setText(arrayList.get(position).getMessage());
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.iv_author_image);
        if (arrayList.get(position).getState().equals("seen")) {
            holder.line.setVisibility(View.GONE);
            holder.rl_detail.setBackground(activity.getResources().getDrawable(R.drawable.notification_gradient_unseen));
        } else {
            holder.line.setVisibility(View.VISIBLE);
            holder.rl_detail.setBackground(activity.getResources().getDrawable(R.drawable.notification_gradient));

        }
//        holder.rl_detail.setOnClickListener(view -> {
//            RxBus.getInstance().send(arrayList.get(position));
//
//            NotificationSeenModel notificationSeenModel = new NotificationSeenModel();
//            notificationSeenModel.id =Integer.parseInt(arrayList.get(position).getNotifyId());
//            RxBus.getInstance().send(notificationSeenModel);
//            holder.line.setVisibility(View.GONE);
//            holder.rl_detail.setBackground(activity.getResources().getDrawable(R.drawable.notification_gradient_unseen));
//            switch (arrayList.get(position).getLink().getType()) {
//                case "event":
//
//                    if (arrayList.get(position).getLink().getMode().equals("upcoming")) {
//                        Intent intent = new Intent(activity, EventDtailActivity.class);
//                        intent.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                        activity.startActivity(intent);
//
//                    } else if (arrayList.get(position).getLink().getMode().equals("watch_live")) {
//
//                        Intent intent = new Intent(activity, EventDetailLiveActivity.class);
//                        intent.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                        activity.startActivity(intent);
//
//                    } else if (arrayList.get(position).getLink().getMode().equals("talent_vmr")) {
//
//                        Intent intent = new Intent(activity, EventPretestActivity.class);
//                        intent.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                        activity.startActivity(intent);
//
//                    } else if (arrayList.get(position).getLink().getMode().equals("participent_vmr")) {
//                        Intent intent = new Intent(activity, EventPretestActivity.class);
//                        intent.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                        activity.startActivity(intent);
//                    }
//                    NotificationEventId notificationEventId = new NotificationEventId();
//                    notificationEventId.id = Integer.parseInt(arrayList.get(position).getLink().getId());
//                    RxBus.getInstance().send(notificationEventId);
//
//                    break;
//
//                case "forum":
//                    Intent i1 = new Intent(activity, CommunityDtailActivity.class);
//                    i1.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                    activity.startActivity(i1);
//                    break;
//                case "news":
//                    Intent i2 = new Intent(activity, NewsDtailActivity.class);
//                    i2.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                    activity.startActivity(i2);
//                    break;
//                case "channel":
//                    Intent i3 = new Intent(activity, ChannelDetailFragment.class);
//                    i3.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                    activity.startActivity(i3);
//                    break;
//                case "video":
//                    Intent i4 = new Intent(activity, VideoDtailActivity.class);
//                    i4.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
//                    activity.startActivity(i4);
//                    break;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //for user like dislike
    public void update(Datum datum) {
        datum.setState("seen");
        notifyItemChanged(this.arrayList.indexOf(datum), datum);
    }

    public Observable<Datum> getDetailClickedObservable() {
        return clickDetail;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desc;
        public ImageView iv_author_image;
        public View line;
        public RelativeLayout rl_detail;

        public MyViewHolder(View view) {
            super(view);

            iv_author_image = view.findViewById(R.id.iv_auther_image);
            desc = view.findViewById(R.id.tv_description);
            line = view.findViewById(R.id.line_view);
            rl_detail = view.findViewById(R.id.rl_detail);


        }

    }
}
