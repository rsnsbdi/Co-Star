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
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.carousel.CarouselResponse;
import com.costar.talkwithidol.app.network.models.carousel.Datum;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.EventDetailPexipActivity;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CarouselPagerAdapter extends PagerAdapter {
    public static int LOOPS_COUNT = 100;
    Activity activity;
    private boolean isMultiScr;
    private CarouselResponse carouselResponse;
    private List<Datum> arrayList = new ArrayList<>();


    public void showList(Activity activity, CarouselResponse carouselResponse, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.arrayList = carouselResponse.getData();
        this.activity = activity;
        this.carouselResponse = carouselResponse;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return arrayList.size() * LOOPS_COUNT;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {


        //int position= position1%carouselResponse.getData().size();
        int position = position1 % arrayList.size();

        RelativeLayout linearLayout = null;
        linearLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_carousel, null);
        RelativeLayout rl_Detail = linearLayout.findViewById(R.id.rl_detail);
        TextView textCarousel = linearLayout.findViewById(R.id.tv_carousel_title);
        ImageView imageViewCarousel = linearLayout.findViewById(R.id.iv_cariusel);
        textCarousel.setText(arrayList.get(position).getTitle());
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(imageViewCarousel);
        rl_Detail.setOnClickListener(view -> {
            switch (arrayList.get(position).getLink().getType()) {
                case "event":

                    if (arrayList.get(position).getLink().getMode().equals("upcoming")) {
                        Intent intent = new Intent(activity, EventDtailActivity.class);
                        intent.putExtra("VIDEOID", (arrayList.get(position).getLink().getId()));
                        activity.startActivity(intent);

                    } else if (arrayList.get(position).getLink().getMode().equals("watch_live")) {

                        Intent intent = new Intent(activity, EventDetailLiveActivity.class);
                        intent.putExtra("VIDEOID", (arrayList.get(position).getLink().getId()));
                        activity.startActivity(intent);

                    } else if (arrayList.get(position).getLink().getMode().equals("talent_vmr")) {

//                        Intent intent = new Intent(activity, EventDetailPexipActivity.class);
//                        intent.putExtra("VIDEOID", (arrayList.get(position).getLink().getId()));
//                        activity.startActivity(intent);
                        Intent intent = new Intent(activity, EventPretestActivity.class);
                        intent.putExtra("VIDEOID", (arrayList.get(position).getLink().getId()));
                        activity.startActivity(intent);

                    } else if (arrayList.get(position).getLink().getMode().equals("participent_vmr")) {
//                        Intent intent = new Intent(activity, EventDetailPexipActivity.class);
//                        intent.putExtra("VIDEOID", (arrayList.get(position).getLink().getId()));
//                        activity.startActivity(intent);

                        Intent intent = new Intent(activity, EventPretestActivity.class);
                        intent.putExtra("VIDEOID", (arrayList.get(position).getLink().getId()));
                        activity.startActivity(intent);
                    }

                    break;

                case "forum":
                    Intent i1 = new Intent(activity, CommunityDtailActivity.class);
                    i1.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
                    activity.startActivity(i1);
                    break;
                case "news":
                    Intent i2 = new Intent(activity, NewsDtailActivity.class);
                    i2.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
                    activity.startActivity(i2);
                    break;
                case "channel":
                    Intent i3 = new Intent(activity, ChannelDetailFragment.class);
                    i3.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
                    activity.startActivity(i3);
                    break;
                case "video":
                    Intent i4 = new Intent(activity, VideoDtailActivity.class);
                    i4.putExtra("VIDEOID", arrayList.get(position).getLink().getId());
                    activity.startActivity(i4);
                    break;
                case "empty":
                    break;


            }
        });


        container.addView(linearLayout);

        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }


    @Override
    public Parcelable saveState() {
        return null;
    }
}
