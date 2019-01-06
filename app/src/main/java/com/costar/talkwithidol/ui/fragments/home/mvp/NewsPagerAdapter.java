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
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ModifiedNewsList.NewsList;
import com.costar.talkwithidol.app.network.models.exploreNews.DatumN;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class NewsPagerAdapter extends PagerAdapter {
    public static int LOOPS_COUNT = 50;
    Activity activity;
    LikeClickInterface likeClickInterface;
    Boolean added = null;
    Boolean added1 = null;
    private boolean isMultiScr;
    private List<NewsList> arrayList = new ArrayList<>();
    private PublishSubject<DatumN> clickLike = PublishSubject.create();

    public void showList(Activity activity, ArrayList<NewsList> arrayList, boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
        this.arrayList.clear();
        this.arrayList = arrayList;

        this.activity = activity;
        notifyDataSetChanged();
    }

    public Observable<DatumN> getLikeClickedObservable() {
        return clickLike;
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
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position1) {
        int position = position1 % arrayList.size();

        RelativeLayout view = null;
        view = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.news_list_item, null);

        RelativeLayout rl_detail, rl_detail1;
        TextView tv_news_name, tv_news_desc, tv_talent_name1, tv_talent_name, tv_news_date, tv_news_name1, tv_news_desc1, tv_news_date1;
        ImageView news_image, like_icon, news_image1, like_icon1;
        LinearLayout like1, like;

        tv_news_name = view.findViewById(R.id.tv_news_name);
        news_image = view.findViewById(R.id.news_image);
        tv_news_desc = view.findViewById(R.id.tv_news_description);
        tv_news_date = view.findViewById(R.id.tv_date_time);
        like_icon = view.findViewById(R.id.like_icon);
        rl_detail = view.findViewById(R.id.rl_detail1);
        tv_talent_name = view.findViewById(R.id.tv_talent_name);
        like = view.findViewById(R.id.iv_like);


        tv_news_name1 = view.findViewById(R.id.tv_news_name1);
        news_image1 = view.findViewById(R.id.news_image1);
        tv_news_desc1 = view.findViewById(R.id.tv_news_description1);
        tv_news_date1 = view.findViewById(R.id.tv_date_time1);
        like_icon1 = view.findViewById(R.id.like_icon1);
        rl_detail1 = view.findViewById(R.id.rl_detail2);
        tv_talent_name1 = view.findViewById(R.id.tv_talent_name1);
        like1 = view.findViewById(R.id.iv_like1);


        tv_talent_name.setText(arrayList.get(position).getData().getChannelName());
        tv_news_name.setText(arrayList.get(position).getData().getNewsName());
        tv_news_desc.setText(Html.fromHtml(arrayList.get(position).getData().getShortDesc()));
        tv_news_date.setText(arrayList.get(position).getData().getNewsDate());
        Picasso.with(activity).load(arrayList.get(position).getData().getImageUrl()).into(news_image);
        rl_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, NewsDtailActivity.class);
                i.putExtra("VIDEOID", arrayList.get(position).getData().getNewsId());
                activity.startActivity(i);

            }
        });
        if (arrayList.get(position).getData().getLikes().getUserLike()) {

            like_icon.setImageResource(R.drawable.ic_liked_filled);
            added = true;
        } else {

            like_icon.setImageResource(R.drawable.ic_like_border);
            added = false;
        }


        tv_talent_name1.setText(arrayList.get(position).getData1().getChannelName());
        tv_news_name1.setText(arrayList.get(position).getData1().getNewsName());
        tv_news_desc1.setText(Html.fromHtml(arrayList.get(position).getData1().getShortDesc()));
        tv_news_date1.setText(arrayList.get(position).getData1().getNewsDate());
        Picasso.with(activity).load(arrayList.get(position).getData1().getImageUrl()).into(news_image1);
        rl_detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, NewsDtailActivity.class);
                i.putExtra("VIDEOID", arrayList.get(position).getData1().getNewsId());
                activity.startActivity(i);

            }
        });

        if (arrayList.get(position).getData1().getLikes().getUserLike()) {
            like_icon1.setImageResource(R.drawable.ic_liked_filled);
            added1 = true;

        } else {

            like_icon1.setImageResource(R.drawable.ic_like_border);
            added1 = false;
        }

        like.setOnClickListener(v -> {
            if (added) {
                added = false;
                like_icon.setImageResource(R.drawable.ic_like_border);
            } else {
                added = true;
                like_icon.setImageResource(R.drawable.ic_liked_filled);
            }
            likeClickInterface.drawReceived(arrayList.get(position).getData().getNewsId(), "news");
        });

        like1.setOnClickListener(v -> {
            if (added1) {
                added1 = false;
                like_icon1.setImageResource(R.drawable.ic_like_border);
            } else {
                added1 = true;
                like_icon1.setImageResource(R.drawable.ic_liked_filled);
            }
            likeClickInterface.drawReceived(arrayList.get(position).getData1().getNewsId(), "news");
        });

        container.addView(view);
        return view;
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
