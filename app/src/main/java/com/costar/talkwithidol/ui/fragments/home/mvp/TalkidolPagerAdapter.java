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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.talkidols.TalkIdolsResponse;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TalkidolPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;
    int i;
    int count;
    int currentPos;
    private boolean isMultiScr;
    private TalkIdolsResponse talkIdolsResponse;
    public static  View talkidolview;
    static boolean check = true;
    FragmentManager fm;
    Fragment fragment;

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public TalkidolPagerAdapter(FragmentManager manager) {

        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
//       return mFragmentList.get(position);
        return mFragmentList.get(position%mFragmentList.size());

    }

    @Override
    public int getCount() {
      // return mFragmentList.size();
        return 200;
    }

    public void addFragment(Fragment fragment) {

        mFragmentList.add(fragment);
    }


  /*  public TalkidolPagerAdapter(Activity activity, TalkIdolsResponse talkIdolsResponse, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.talkIdolsResponse = talkIdolsResponse;
        count = talkIdolsResponse.getData().size();
        this.activity = activity;
    }


    @Override
    public int getCount() {

        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {
        int position = position1 % talkIdolsResponse.getData().size();


        RelativeLayout linearLayout = null;

        linearLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.ultra_item, null);

        TextView eventtitle = linearLayout.findViewById(R.id.tv_channel_name);
        ImageView eventImage = linearLayout.findViewById(R.id.iv_channel);
        RelativeLayout rl_detail = linearLayout.findViewById(R.id.rl_detail);
         talkidolview = linearLayout.findViewById(R.id.overlay);


        talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient_blue));
//        if(pos==position1){
//            talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));
//        }

//        if(!check) {
//            talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient_blue));
//            if (pos == 0) {
//                talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));
//            }
//            if (pos == 1) {
//                talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));
//            }
//            if (pos == 2) {
//                talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));
//            }
//            if (pos == 3) {
//                talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));
//            }
//            if (pos == 4) {
//                talkidolview.setBackground(activity.getResources().getDrawable(R.drawable.channel_image_gradient));
//            }
//        }



        if (talkIdolsResponse != null) {
            try {
                eventtitle.setText(talkIdolsResponse.getData().get(position).getTalentName());
                Picasso.with(activity).load(talkIdolsResponse.getData().get(position).getAvatarUrl()).into(eventImage);
                rl_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (talkIdolsResponse.getData() != null) {
                            try {
                                Intent i = new Intent(activity, EventDtailActivity.class);
                                i.putExtra("VIDEOID", talkIdolsResponse.getData().get(position).getEventId());
                                activity.startActivity(i);

                            } catch (Exception e) {

                            }

                        }

                    }
                });

            } catch (Exception e) {

            }

        }


        container.addView(linearLayout);
        return linearLayout;
    }

    public void notifyChanged(int pos){


       this.notifyDataSetChanged();


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }*/
}
