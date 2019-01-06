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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ModifiedChannelList.ChannelList;
import com.costar.talkwithidol.app.network.models.exploreChannel.ExploreChannelResponse;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChannelPagerAdapter extends PagerAdapter {
    Activity activity;
    ArrayList<ChannelList> arrayList = new ArrayList<>();
    private boolean isMultiScr;
    private ExploreChannelResponse exploreChannelResponse;
    public static int LOOPS_COUNT = 50;
//    public ChannelPagerAdapter(Activity activity, ArrayList<ChannelList> channelLists, boolean isMultiScr) {
//        this.isMultiScr = isMultiScr;
//        this.arrayList = channelLists;
//        this.exploreChannelResponse = exploreChannelResponse;
//        this.activity = activity;
//    }

    public void showlist(Activity activity, ArrayList<ChannelList> channelLists, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.arrayList = channelLists;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size() *LOOPS_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {
//        int position = position1 % exploreChannelResponse.getData().size();
        int position = position1  % arrayList.size();

        RelativeLayout linearLayout = null;
        linearLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.channel_item, null);
        RelativeLayout rl_detail = linearLayout.findViewById(R.id.rl_detail);
        RelativeLayout rl_detail1 = linearLayout.findViewById(R.id.rl_detail1);

        TextView eventtitle = linearLayout.findViewById(R.id.tv_channel_name);
        ImageView eventImage = linearLayout.findViewById(R.id.iv_channel);

        TextView eventtitle1 = linearLayout.findViewById(R.id.tv_channel_name1);
        ImageView eventImage1 = linearLayout.findViewById(R.id.iv_channel1);

        eventtitle.setText(arrayList.get(position).getData().getChannelName());
        Picasso.with(activity).load(arrayList.get(position).getData().getImageUrl()).into(eventImage);
        eventtitle1.setText(arrayList.get(position).getData1().getChannelName());
        Picasso.with(activity).load(arrayList.get(position).getData1().getImageUrl()).into(eventImage1);

        rl_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(activity, ChannelDetailFragment.class);
//                i.putExtra("VIDEOID", arrayList.get(position).getData().getChannelId());
//                activity.startActivity(i);
                HomePageActivity.VIDEOID =arrayList.get(position).getData().getChannelId();
                loadBus("channelDetailH");
            }
        });

        rl_detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(activity, ChannelDetailFragment.class);
//                i.putExtra("VIDEOID", arrayList.get(position).getData1().getChannelId());
//                activity.startActivity(i);
                HomePageActivity.VIDEOID =arrayList.get(position).getData1().getChannelId();
                loadBus("channelDetailH");
            }
        });
        container.addView(linearLayout);
        return linearLayout;
}


    private void loadBus(String name){

                RxBus.getInstance().send(name);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}
