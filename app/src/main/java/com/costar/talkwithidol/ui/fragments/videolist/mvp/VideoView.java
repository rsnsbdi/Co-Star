package com.costar.talkwithidol.ui.fragments.videolist.mvp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreVideos.DatumV;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
import com.costar.talkwithidol.ui.adapters.VideoAdapter;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class VideoView extends FrameLayout {

    public AppCompatActivity activity;
    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    //    ShowLoadingDialog progressDialog;
    PreferencesManager preferencesManager;
    LinearLayoutManager layoutManager;
    ImageView backButton;
    RelativeLayout loading;
    private List<DiscoverDTO> discoverList = new ArrayList<>();

    public VideoView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, VideoAdapter mAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = mAdapter;
        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);
//        progressDialog = new ShowLoadingDialog(activity);
        loading = findViewById(R.id.loading_view);

        this.preferencesManager = preferencesManager;
        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Videos");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v -> {
            RxBus.getInstance().send(1);
        });

    }

    public LinearLayoutManager layoutManager() {
        return layoutManager;
    }

//    public  void setVideo(ExploreVideosResponse exploreVideosResponse){
//
//        mAdapter = new VideoAdapter(activity, preferencesManager, exploreVideosResponse, (position) -> {
//
//
//            Intent intent = new Intent(activity, VideoDtailActivity.class);
//            intent.putExtra("VIDEOID",exploreVideosResponse.getData().get(position).getVideoId());
//            activity.startActivity(intent);
//
//
//
//        });
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
//
//
//    }


    //    public void setVideo(ExploreVideosResponse exploreVideosResponse) {
//        mAdapter.showList(exploreVideosResponse);
//
//
//    }
    public void setVideo(ArrayList<DatumV> exploreVideosResponse) {
        mAdapter.showList(exploreVideosResponse, activity);
    }


    public void StartDetail(String id) {

        Intent intent = new Intent(activity, VideoDtailActivity.class);
        intent.putExtra("VIDEOID", id);
        activity.startActivity(intent);
    }


    //like click observable
    public Observable<DatumV> getClickObservable() {
        return mAdapter.getLikeClickedObservable();
    }


    //detail click observable
    public Observable<DatumV> getDetailClickObservable() {
        return mAdapter.getDetailClickedObservable();
    }

    /* public Observable<Object> getLikeObservable(){
         return RxView.clicks(view1);
      }
  */
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


    public void onLikeSucess(DatumV datum) {
        mAdapter.update(datum);
    }

    public void onDisLikeSucess(DatumV datum) {
        mAdapter.updateDislike(datum);
    }

    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

}
