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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreCommunity.DatumC;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.databinding.CommunityListItemBinding;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;


public class CommunityPagerAdapter extends PagerAdapter {
    public static int LOOPS_COUNT = 100;
    Activity activity;
    LinearLayout ll_comment;
    int position = 0;
    LikeClickInterface likeClickInterface;
    Boolean liked = null;
    private List<DatumC> arrayList = new ArrayList<>();
    private boolean isMultiScr;
    private PublishSubject<DatumC> clickLike = PublishSubject.create();

    public void showlist(Activity activity, ExploreCommunitylResponse exploreCommunitylResponse, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.arrayList.clear();
        this.arrayList = exploreCommunitylResponse.getData();
        this.activity = activity;
        notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

//    public Observable<DatumC> getLikeClickedObservable() {
//        return clickLike;
//    }

    @Override
    public int getCount() {
        return arrayList.size() * LOOPS_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {
        int position = position1 % arrayList.size();
        CommunityListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.community_list_item, null, false);


        binding.tvTalentName.setText(arrayList.get(position).getChannelName());
        binding.tvAuthername.setText(arrayList.get(position).getAuthorName());
        binding.tvDateTime.setText(arrayList.get(position).getForumDate());
        binding.tvCommunityDescription.setText(Html.fromHtml(arrayList.get(position).getShortDesc()));

        if (arrayList.get(position).getComments().getLastComment() != null) {
            binding.tvCommenterName.setText(arrayList.get(position).getComments().getLastComment().getAuthorName());
            binding.tvComments.setText(arrayList.get(position).getComments().getLastComment().getComment());
        } else {
            binding.layoutComment.setVisibility(View.INVISIBLE);
        }

        if (arrayList.get(position).getLikes().getUserLike()) {
            binding.likeView.setText("Liked");
            liked = true;
            binding.likeView.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.filled_love_icon), null, null, null);

        } else {
            binding.likeView.setText("Like");
            liked = false;
            binding.likeView.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.unfilled_love_icon), null, null, null);

        }

        String lastLiker = arrayList.get(position).getLikes().getLastLiker();
        int totalLikes = arrayList.get(position).getLikes().getTotalLikes();

        if (arrayList.get(position).getLikes().getTotalLikes() == 0) {
            binding.tvTotalLike.setText("");
        } else if (arrayList.get(position).getLikes().getTotalLikes() == 1) {
            binding.tvTotalLike.setText(lastLiker + " liked this");
        } else if (arrayList.get(position).getLikes().getTotalLikes() > 1) {
            binding.tvTotalLike.setText(lastLiker + " " + "and" + " " + (totalLikes-1) + " " + "others liked this");
        }


        Picasso.with(activity).load(arrayList.get(position).getAuthorPicture()).into(binding.ivAutherImage);

        if (arrayList.get(position).getHas_main_image()) {
            Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(binding.ivImage);
        } else {
            binding.ivImage.setVisibility(View.GONE);
            binding.descriptionNoimg.setText(Html.fromHtml(arrayList.get(position).getShortDesc()));
            binding.descriptionNoimg.setVisibility(View.VISIBLE);
            binding.tvCommunityDescription.setVisibility(View.INVISIBLE);
        }

        binding.detailRl.setOnClickListener(view1 -> {
            Intent i = new Intent(activity, CommunityDtailActivity.class);
            i.putExtra("VIDEOID", arrayList.get(position).getForumId());
            activity.startActivity(i);

        });

        binding.likeView.setOnClickListener(v -> {

            if (liked) {
                liked = false;
                binding.likeView.setText("Like");
                binding.likeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.unfilled_love_icon, 0, 0, 0);

            } else {
                liked = true;
                binding.likeView.setText("Liked");
                binding.likeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filled_love_icon, 0, 0, 0);
            }

            likeClickInterface.drawReceived(arrayList.get(position).getForumId(), "community");

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
