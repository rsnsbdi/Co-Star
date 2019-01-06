package com.costar.talkwithidol.ui.fragments.home.mvp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ModifiedChannelList.ChannelList;
import com.costar.talkwithidol.app.network.models.ModifiedNewsList.NewsList;
import com.costar.talkwithidol.app.network.models.carousel.CarouselResponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.app.network.models.exploreVideos.ExploreVideosResponse;
import com.costar.talkwithidol.app.network.models.promo.PromoResponse;
import com.costar.talkwithidol.app.network.models.talkidols.Datum;
import com.costar.talkwithidol.app.network.models.talkidols.TalkIdolsResponse;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.MyPageIndicator;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static com.costar.talkwithidol.ui.fragments.home.mvp.TalkidolPagerAdapter.check;


@SuppressLint("ViewConstructor")
public class HomeView extends FrameLayout {


    private static final float RATIO_SCALE = 0.18f;
    public  int pos1= 0;
    @BindView(R.id.ultra_viewpager)
    public ViewPager ultraViewPager;
    @BindView(R.id.ultra_viewpager_channel)
    public ViewPager ultraViewPager_channel;
    @BindView(R.id.ultra_viewpager_event)
    public ViewPager ultraViewPager_event;
    @BindView(R.id.ultra_viewpager_news)
    public ViewPager ultraViewPager_news;
    @BindView(R.id.ultra_viewpager_videos)
    public ViewPager ultraViewPager_videos;
    @BindView(R.id.ultra_viewpager_community)
    public ViewPager ultraViewPager_community;
    @BindView(R.id.ultra_viewpager_promo)
    public ViewPager ultra_viewpager_promo;
    public AppCompatActivity activity;
    @BindView(R.id.pagesContainer)
    LinearLayout mLinearLayout;
    @BindView(R.id.indicator_channel)
    LinearLayout indicator_channel;
    @BindView(R.id.indicator_event)
    LinearLayout indicator_event;
    @BindView(R.id.ultra_indicator)
    LinearLayout indicatior_ultra;
    @BindView(R.id.indicator_news)
    LinearLayout indicator_news;
    @BindView(R.id.indicator_videos)
    LinearLayout indicator_videos;
    @BindView(R.id.indicator_community)
    LinearLayout indicator_community;
    @BindView(R.id.loading_view)
    RelativeLayout loading;
    //    @BindView(R.id.indicator_0)
//    CircleIndicator circleIndicator_0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.uv_pager)
    ViewPager uvUltraViewPager;
    //browse all
    @BindView(R.id.rl_channel)
    RelativeLayout rl_channel;
    @BindView(R.id.rl_events)
    RelativeLayout rl_events;
    @BindView(R.id.rl_videos)
    RelativeLayout rl_videos;
    @BindView(R.id.rl_news)
    RelativeLayout rl_news;
    @BindView(R.id.rl_community)
    RelativeLayout rl_community;
    //    ShowLoadingDialog progressDialog;
    NewsPagerAdapter newsPagerAdapter;
    CommunityPagerAdapter communityPagerAdapter;
    EventsPagerAdapter eventsPagerAdapter;
    VideosPagerAdapter videosPagerAdapter;
    ChannelPagerAdapter channelPagerAdapter;
    MyPageIndicator mIndicator;
    private TalkidolPagerAdapter talkidolPagerAdapter;
    private PromoPagerAdapter adapter;
    private CarouselPagerAdapter carouselPagerAdapter;
    private int gravity_hor;
    private int gravity_ver;
    public static int pos=1;
    float scale;
    TalkIdolsResponse talkIdolsResponse;
    int page;




    public HomeView(@NonNull AppCompatActivity activity, CarouselPagerAdapter carouselPagerAdapter, ChannelPagerAdapter channelPagerAdapter, NewsPagerAdapter newsPagerAdapter, VideosPagerAdapter videosPagerAdapter, EventsPagerAdapter eventsPagerAdapter, CommunityPagerAdapter communityPagerAdapter) {
        super(activity);
        this.activity = activity;
        this.newsPagerAdapter = newsPagerAdapter;
        this.videosPagerAdapter = videosPagerAdapter;
        this.eventsPagerAdapter = eventsPagerAdapter;
        this.communityPagerAdapter = communityPagerAdapter;
        this.channelPagerAdapter = channelPagerAdapter;
        this.carouselPagerAdapter = carouselPagerAdapter;
        inflate(activity, R.layout.fragment_home, this);
        ButterKnife.bind(this);


        activity.setSupportActionBar(toolbar);
        AppUtils.transparentStatusBar(activity.getWindow());
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        gravity_hor = Gravity.CENTER_HORIZONTAL;
        gravity_ver = Gravity.BOTTOM;

       // uvUltraViewPager.setAdapter(talkidolPagerAdapter);

        ultraViewPager_community.setAdapter(communityPagerAdapter);
        ultraViewPager_news.setAdapter(newsPagerAdapter);
        ultraViewPager_event.setAdapter(eventsPagerAdapter);
        ultraViewPager_videos.setAdapter(videosPagerAdapter);
        ultraViewPager_channel.setAdapter(channelPagerAdapter);
        ultraViewPager.setAdapter(carouselPagerAdapter);

    }

    public void setChannel(ArrayList<ChannelList> channelList) {
        channelPagerAdapter.showlist(activity, channelList, false);
        ultraViewPager_channel.setOffscreenPageLimit(channelList.size());
        mIndicator = new MyPageIndicator(activity, indicator_channel, ultraViewPager_channel, R.drawable.indicator_circle);
        mIndicator.setPageCount(channelList.size());
        mIndicator.show();

    }

    public void setCommunity(ExploreCommunitylResponse exploreCommunitylResponse) {
        communityPagerAdapter.showlist(activity, exploreCommunitylResponse, false);
        ultraViewPager_community.setOffscreenPageLimit(exploreCommunitylResponse.getData().size());
        mIndicator = new MyPageIndicator(activity, indicator_community, ultraViewPager_community, R.drawable.indicator_circle);
        mIndicator.setPageCount(exploreCommunitylResponse.getData().size());
        mIndicator.show();
    }

    public void setNews(ArrayList<NewsList> newsList) {
        newsPagerAdapter.showList(activity, newsList, false);
        ultraViewPager_news.setOffscreenPageLimit(newsList.size());
        mIndicator = new MyPageIndicator(activity, indicator_news, ultraViewPager_news, R.drawable.indicator_circle);
        mIndicator.setPageCount(newsList.size());
        mIndicator.show();
    }

    public void setEvent(ExploreEventResponse exploreEventResponse) {
        eventsPagerAdapter.showlist(activity, exploreEventResponse, false);
        ultraViewPager_event.setOffscreenPageLimit(exploreEventResponse.getData().size());
        mIndicator = new MyPageIndicator(activity, indicator_event, ultraViewPager_event, R.drawable.indicator_circle);
        mIndicator.setPageCount(exploreEventResponse.getData().size());
        mIndicator.show();
    }

    public void setVideo(ExploreVideosResponse exploreVideosResponse) {
        videosPagerAdapter.showlist(activity, exploreVideosResponse, false);
        ultraViewPager_videos.setOffscreenPageLimit(exploreVideosResponse.getData().size());
        mIndicator = new MyPageIndicator(activity, indicator_videos, ultraViewPager_videos, R.drawable.indicator_circle);
        mIndicator.setPageCount(exploreVideosResponse.getData().size());
        mIndicator.show();
    }

    public void setCarousel(CarouselResponse carouselResponse) {
        carouselPagerAdapter.showList(activity, carouselResponse, false);
        ultraViewPager.setOffscreenPageLimit(carouselResponse.getData().size());
        mIndicator = new MyPageIndicator(activity, mLinearLayout, ultraViewPager, R.drawable.indicator_circle);
        mIndicator.setPageCount(carouselResponse.getData().size());
        mIndicator.show();
    }


  /*  public void setTalkIdol(TalkIdolsResponse talkIdolsResponse) {
        this.talkIdolsResponse = talkIdolsResponse;
        try {
            talkidolPagerAdapter = new TalkidolPagerAdapter(activity.getSupportFragmentManager());
            for (int i = 0; i < talkIdolsResponse.getData().size(); i++) {
                talkidolPagerAdapter.addFragment(TalkWithFragment.newInstance(activity, talkIdolsResponse.getData().get(i).getEventId(), talkIdolsResponse.getData().get(i).getTalentName(), talkIdolsResponse.getData().get(i).getAvatarUrl()));
            }
            uvUltraViewPager.setAdapter(talkidolPagerAdapter);
        } catch (Exception e) {

        }
    }*/

    public void setTalkIdol(List<Datum> talkIdolsResponse) {

        talkidolPagerAdapter = new TalkidolPagerAdapter(activity.getSupportFragmentManager());

        for (int i = 0; i < talkIdolsResponse.size(); i++) {
            talkidolPagerAdapter.addFragment(TalkWithFragment.newInstance(activity,talkIdolsResponse.get(i).getEventId(), talkIdolsResponse.get(i).getTalentName(),talkIdolsResponse.get(i).getAvatarUrl()));
        }
        uvUltraViewPager.setAdapter(talkidolPagerAdapter);
        uvUltraViewPager.setClipToPadding(false);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            uvUltraViewPager.setPadding(260, 30, 260, 150);
        }else{
            uvUltraViewPager.setPadding(160, 30, 160, 150);
        }

        uvUltraViewPager.setOffscreenPageLimit(3);
        uvUltraViewPager.setPageMargin(0);
        // uvUltraViewPager.setCurrentItem(2);
        uvUltraViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("", "onPageScrolled: " + position);

                TalkWithFragment imageFragment = (TalkWithFragment) ((TalkidolPagerAdapter) uvUltraViewPager.getAdapter()).getItem(position);
                scale = 1 - (positionOffset * RATIO_SCALE);
                imageFragment.scaleImage(scale);

                if (position + 1 < uvUltraViewPager.getAdapter().getCount()) {
                    imageFragment = (TalkWithFragment) ((TalkidolPagerAdapter) uvUltraViewPager.getAdapter()).getItem(position + 1);
                    scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
                    imageFragment.scaleImage(scale);
                }

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("", "onPageSelected: " + position);
                try {
                    ((TalkWithFragment) talkidolPagerAdapter.getItem(uvUltraViewPager.getCurrentItem())).viewChanged.setForeground(getResources().getDrawable(R.drawable.channel_image_gradient));
                } catch (Exception e) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                //((TalkWithFragment) talkidolPagerAdapter.getItem(uvUltraViewPager.getCurrentItem())).viewChanged.setForeground(getResources().getDrawable(R.drawable.channel_image_gradient));

                Log.i("", "onPageScrollStateChanged: " + state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    TalkWithFragment fragment = (TalkWithFragment) ((TalkidolPagerAdapter) uvUltraViewPager.getAdapter()).getItem(uvUltraViewPager.getCurrentItem());
                    fragment.scaleImage(1);
                    fragment.setSelected(true);
                    if (uvUltraViewPager.getCurrentItem() > 0) {
                        fragment = (TalkWithFragment) ((TalkidolPagerAdapter) uvUltraViewPager.getAdapter()).getItem(uvUltraViewPager.getCurrentItem() - 1);
                        fragment.scaleImage(1 - RATIO_SCALE);
                    }

                    if (uvUltraViewPager.getCurrentItem() + 1 < uvUltraViewPager.getAdapter().getCount()) {
                        fragment = (TalkWithFragment) ((TalkidolPagerAdapter) uvUltraViewPager.getAdapter()).getItem(uvUltraViewPager.getCurrentItem() + 1);
                        fragment.scaleImage(1 - RATIO_SCALE);
                    }
                }else{
                    TalkWithFragment fragment = (TalkWithFragment) ((TalkidolPagerAdapter) uvUltraViewPager.getAdapter()).getItem(uvUltraViewPager.getCurrentItem());
                    fragment.setSelected(false);
                }

            }
        });

        uvUltraViewPager.setCurrentItem(1);

       /* uvUltraViewPager.setCurrentItem(pos);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                talkidolPagerAdapter. check = false;
            }
        }, 10000);
*/

//        mIndicator = new MyPageIndicator(activity, indicatior_ultra, uvUltraViewPager, R.drawable.indicator_circle);
//        mIndicator.setPageCount(talkIdolsResponse.getData().size());
//        mIndicator.show();





//        uvUltraViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
////                pos = uvUltraViewPager.getRealItem();
////                System.out.println("Position=="+uvUltraViewPager.getRealItem());
////                talkidolPagerAdapter.notifyChanged(pos);
////                System.out.println("Position1=="+uvUltraViewPager.getRealItem());
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
////                System.out.println("Position2=="+uvUltraViewPager.getRealItem());
//                pos = uvUltraViewPager.getRealItem();
//
//                System.out.println("Position=="+uvUltraViewPager.getRealItem());
//                talkidolPagerAdapter.notifyChanged(pos);
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//                pos = uvUltraViewPager.getRealItem();
//
//               // talkidolPagerAdapter.notifyChanged(pos);
//
//            }
//        });



    }


    public void setPromo(PromoResponse promoResponse) {

        adapter = new PromoPagerAdapter(activity, promoResponse, false);
        ultra_viewpager_promo.setAdapter(adapter);
        mIndicator = new MyPageIndicator(activity, mLinearLayout, ultra_viewpager_promo, R.drawable.indicator_circle);
        mIndicator.setPageCount(promoResponse.getData().size());
        mIndicator.show();

    }

    //click observers
    public Observable<Object> channelObservable() {
        return RxView.clicks(rl_channel);
    }

    public Observable<Object> eventObservable() {
        return RxView.clicks(rl_events);
    }

    public Observable<Object> videosObservable() {
        return RxView.clicks(rl_videos);
    }

    public Observable<Object> newsObservable() {
        return RxView.clicks(rl_news);
    }

    public Observable<Object> communityObservable() {
        return RxView.clicks(rl_community);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }


    public void StartDetail(String id, String mode) {

        if (mode.equals("upcoming")) {
            Intent intent = new Intent(activity, EventDtailActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        } else if (mode.equals("watch_live")) {

            Intent intent = new Intent(activity, EventDetailLiveActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        } else if (mode.equals("talent_vmr")) {

//            Intent intent = new Intent(activity, EventDetailPexipActivity.class);
//            intent.putExtra("VIDEOID", id);
//            activity.startActivity(intent);
            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        } else if (mode.equals("participent_vmr")) {
//            Intent intent = new Intent(activity, EventDetailPexipActivity.class);
//            intent.putExtra("VIDEOID", id);
//            activity.startActivity(intent);
            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);
        }
    }


}
