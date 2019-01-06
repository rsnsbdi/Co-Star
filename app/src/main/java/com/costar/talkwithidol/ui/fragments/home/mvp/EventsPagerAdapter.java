/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.costar.talkwithidol.ui.fragments.home.mvp;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreEvent.DatumE;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.databinding.EventListItemBinding;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class EventsPagerAdapter extends PagerAdapter {
    Activity activity;
    LikeClickInterface likeClickInterface;
    Boolean added = null;
    private boolean isMultiScr;
    private PublishSubject<String> clickLike = PublishSubject.create();
    private List<DatumE> arrayList = new ArrayList<>();

    public Observable<String> getLikeClickedObservable() {
        return clickLike;
    }

    public void showlist(Activity activity, ExploreEventResponse exploreEventResponse, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.arrayList = exploreEventResponse.getData();
        this.activity = activity;
        notifyDataSetChanged();
    }


    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return arrayList.size() * 100;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {
        int position = position1 % arrayList.size();

        EventListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.event_list_item, null, false);


        if (arrayList.get(position).getUserWatchlist()) {
            added = true;
            binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
            DrawableCompat.setTint(binding.ivAddwatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
        } else {
            added = false;
            binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
            DrawableCompat.setTint(binding.ivAddwatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.white));
        }

        if (!arrayList.get(position).getEventType().equals("two-way")) {
            binding.ivTwoway.setVisibility(View.GONE);
        }

        if (arrayList.get(position).getEventType().equals("tba")) {
            binding.eventDate.setText("DATE");
            binding.eventMonthYear.setText("COMING");
            binding.tvEventTime.setText("SOON");
        } else {
            binding.eventDate.setText(String.valueOf(arrayList.get(position).getEventDate().getDay()));
            binding.eventMonthYear.setText(arrayList.get(position).getEventDate().getMonth() + " " + arrayList.get(position).getEventDate().getYear());
            binding.tvEventTime.setText(String.valueOf(arrayList.get(position).getEventDate().getHour()) + ":" + String.valueOf(arrayList.get(position).getEventDate().getMinute()));
        }
        binding.tvEventState.setText(arrayList.get(position).getEventState());
        if (arrayList.get(position).getEventName() != null) {
            binding.eventTitle.setText(arrayList.get(position).getEventName());
        } else {
            binding.eventTitle.setText("");
        }
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(binding.eventImage);
        binding.rlDetail.setOnClickListener(view -> {
            if (arrayList.get(position).getMode().equals("upcoming")) {
                Intent intent = new Intent(activity, EventDtailActivity.class);
                intent.putExtra("VIDEOID", arrayList.get(position).getEventId());
                activity.startActivity(intent);

            } else if (arrayList.get(position).getMode().equals("watch_live")) {

                Intent intent = new Intent(activity, EventDetailLiveActivity.class);
                intent.putExtra("VIDEOID", arrayList.get(position).getEventId());
                activity.startActivity(intent);

            } else if (arrayList.get(position).getMode().equals("talent_vmr")) {

                Intent intent = new Intent(activity, EventPretestActivity.class);
                intent.putExtra("VIDEOID", arrayList.get(position).getEventId());
                activity.startActivity(intent);

            } else if (arrayList.get(position).getMode().equals("participent_vmr")) {
                Intent intent = new Intent(activity, EventPretestActivity.class);
                intent.putExtra("VIDEOID", arrayList.get(position).getEventId());
                activity.startActivity(intent);
            }

        });

        binding.ivAddwatchlist.setOnClickListener(v -> {

            if (added) {
                added = false;
                binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
                DrawableCompat.setTint(binding.ivAddwatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.white));
            } else {
                added = true;
                binding.ivAddwatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
                DrawableCompat.setTint(binding.ivAddwatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
            }

            likeClickInterface.drawReceived(arrayList.get(position).getEventId(), "event");

        });


        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    public void getReceivedDrawData(LikeClickInterface likeClickInterface) {
        this.likeClickInterface = likeClickInterface;
    }

    public interface LikeClickInterface {
        void drawReceived(String id, String type);
    }
}
