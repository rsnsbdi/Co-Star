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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreVideos.DatumV;
import com.costar.talkwithidol.app.network.models.exploreVideos.ExploreVideosResponse;
import com.costar.talkwithidol.databinding.VideosItemBinding;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class VideosPagerAdapter extends PagerAdapter {
    Activity activity;
    LikeClickInterface likeClickInterface;
    Boolean liked = null;
    private boolean isMultiScr;
    private PublishSubject<DatumV> clickLike = PublishSubject.create();
    private List<DatumV> arrayList = new ArrayList<>();

    public void showlist(Activity activity, ExploreVideosResponse exploreVideosResponse, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.arrayList.clear();
        this.arrayList = exploreVideosResponse.getData();
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
        VideosItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.videos_item, null, false);

        binding.tvVideoTime.setText(arrayList.get(position).getVideoDate());
        binding.tvVideoTitle.setText(arrayList.get(position).getVideoName());

        binding.tvTalentName.setText(arrayList.get(position).getTalentName());
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(binding.ivVideo);
        if (arrayList.get(position).getLikes() != null) {
            if (arrayList.get(position).getLikes().getUserLike()) {
                liked = true;
                binding.ivLike.setImageResource(R.drawable.ic_liked_filled);
            } else {
                liked = false;
                binding.ivLike.setImageResource(R.drawable.ic_like_border);
            }
        }
        binding.rlDetail.setOnClickListener(view -> {
            Intent i = new Intent(activity, VideoDtailActivity.class);
            i.putExtra("VIDEOID", arrayList.get(position).getVideoId());
            activity.startActivity(i);
        });

        binding.ivLike1.setOnClickListener(v -> {
            if (liked) {
                liked = false;
                binding.ivLike.setImageResource(R.drawable.ic_like_border);
            } else {
                liked = true;
                binding.ivLike.setImageResource(R.drawable.ic_liked_filled);
            }
            likeClickInterface.drawReceived(arrayList.get(position).getVideoId(), "video");

        });


        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    public Observable<DatumV> getLikeClickedObservable() {
        return clickLike;
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
