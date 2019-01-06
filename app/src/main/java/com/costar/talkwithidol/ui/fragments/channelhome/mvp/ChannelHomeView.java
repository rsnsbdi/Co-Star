package com.costar.talkwithidol.ui.fragments.channelhome.mvp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.channelhome.ChannelHomeResponse;
import com.costar.talkwithidol.app.network.models.channelhome.Datum;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class ChannelHomeView extends FrameLayout {

    public static String id;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    ChannelHomeAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    PreferencesManager preferencesManager;
    private List<DiscoverDTO> discoverList = new ArrayList<>();
    private AppCompatActivity activity;
    private PagerAdapter adapter;
    private int gravity_hor;
    private int gravity_ver;
    ShowLoadingDialog showLoadingDialog;
    private UltraViewPager.Orientation gravity_indicator;
    LinearLayoutManager layoutManager;
    RelativeLayout loading;

    RelativeLayout noContentView;
    TextView noContentMsg;
    public ChannelHomeView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, ChannelHomeAdapter mAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = mAdapter;
//        showLoading = new ShowLoadingDialog(activity);
        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);

        this.preferencesManager = preferencesManager;
        recyclerView = findViewById(R.id.recycler_view);
        loading = findViewById(R.id.loading_view);
        noContentView = findViewById(R.id.noContentView);
        noContentMsg = findViewById(R.id.cycle);

        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("News");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.preferencesManager = preferencesManager;
        toolbar.setVisibility(GONE);
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition,0);


    }

    public void StartDetail(ChannelHomeResponse channelHomeResponse1, Datum integer) {

        switch ((integer).getType()) {
            case "forum":
                id = (integer).getForumId();
                Intent intent = new Intent(activity, CommunityDtailActivity.class);
                intent.putExtra("VIDEOID", id);
                activity.startActivity(intent);

                break;
            case "news":
                id = (integer).getNewsId();
                Intent intent1 = new Intent(activity, NewsDtailActivity.class);
                intent1.putExtra("VIDEOID", id);
                activity.startActivity(intent1);
                break;
            case "video":
                id = (integer).getVideoId();
                Intent intent3 = new Intent(activity, VideoDtailActivity.class);
                intent3.putExtra("VIDEOID", id);
                activity.startActivity(intent3);
                break;
            case "promo":
                id = (integer).getPromoId();
//                Intent intent = new Intent(activity, NewsDtailActivity.class);
//                intent.putExtra("VIDEOID", id);
//                activity.startActivity(intent);
                break;
        }


    }


    public void StartDetail(String id, String mode){


        if(mode.equals("upcoming")) {
            Intent intent = new Intent(activity, EventDtailActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);



        }else if(mode.equals("watch_live")){

            Intent intent = new Intent(activity, EventDetailLiveActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        }else if(mode.equals("talent_vmr")){


            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        }else if(mode.equals("participent_vmr")){

            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);
        }
    }


    public void onLikeSucess(Datum datum)
    {
        mAdapter.update(datum);
    }

    public void onDisLikeSucess(Datum datum)
    {
        mAdapter.updateDislike(datum);
    }


    public void onLikeSucessCommunity(Datum datum, LikeEntityReponse likeEntityReponse)
    {
        mAdapter.updateC(datum, likeEntityReponse);
    }

    public void onDisLikeSucessCommunity(Datum datum, LikeEntityReponse likeEntityReponse)
    {
        mAdapter.updateDislikeC(datum, likeEntityReponse);
    }

    //like click observable
    public Observable<Datum> getClickVObservable() {
        return mAdapter.getLikeClickedVObservable();
    }

    //detail click observable
    public Observable<Datum> getDetailClickVkObservable() {
        return mAdapter.getDetailClickedVObservable();
    }

    //like click observable
    public Observable<Datum> getClickFObservable() {
        return mAdapter.getLikeClickedFObservable();
    }

    //detail click observable
    public Observable<Datum> getDetailClickFObservable() {
        return mAdapter.getDetailClickedFObservable();
    }

    //like click observable
    public Observable<Datum> getClickNObservable() {
        return mAdapter.getLikeClickedNObservable();
    }

    //detail click observable
    public Observable<Datum> getDetailClickNObservable() {
        return mAdapter.getDetailClickedNObservable();
    }

    //like click observable
    public Observable<Datum> getClickEObservable() {
        return mAdapter.getLikeClickedEObservable();
    }

    //detail click observable
    public Observable<Datum> getDetailClickEObservable() {
        return mAdapter.getDetailClickedEObservable();
    }


    public LinearLayoutManager layoutManager(){
        return layoutManager;
    }

    public void setChannelHome(ArrayList<Datum> channelHomeResponse) {
        mAdapter.showList(activity, channelHomeResponse);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public void onLikeSucessE(Datum datum)
    {
        mAdapter.updateE(datum);
    }

    public void onDisLikeSucessE(Datum datum)
    {
        mAdapter.updateDislikeE(datum);
    }


    public void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    public void showNoContent(boolean show, String message) {
        if (show) {
            noContentMsg.setText(message);
            noContentView.setVisibility(View.VISIBLE);
        } else {
            noContentView.setVisibility(View.GONE);
        }
    }

}
