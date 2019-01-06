package com.costar.talkwithidol.ui.fragments.eventlist.mvp;

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

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreEvent.DatumE;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.costar.talkwithidol.ui.adapters.EventAdapter;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class EventView extends FrameLayout {

    RecyclerView recyclerView;
    EventAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    ShowLoadingDialog progressDialog;
    PreferencesManager preferencesManager;
    public AppCompatActivity activity;
    private List<DiscoverDTO> discoverList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ImageView backButton;
    RelativeLayout loading;

    public EventView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, EventAdapter eventAdapter) {
        super(activity);
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        this.mAdapter = eventAdapter;
//        progressDialog = new ShowLoadingDialog(activity);
        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);
        loading = findViewById(R.id.loading_view);

        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Events");
        activity.setSupportActionBar(toolbar);
        AppUtils.transparentStatusBar(activity.getWindow());
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition,0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v->{
            RxBus.getInstance().send(1);
        });


    }
    public LinearLayoutManager layoutManager(){
        return layoutManager;
    }


    public void setEvent(ArrayList<DatumE> exploreEventResponse){

        mAdapter.showList(activity, exploreEventResponse);
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

//            Intent intent = new Intent(activity, EventDetailPexipActivity.class);
//            intent.putExtra("VIDEOID", id);
//            activity.startActivity(intent);

            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        }else if(mode.equals("participent_vmr")){
//            Intent intent = new Intent(activity, EventDetailPexipActivity.class);
//            intent.putExtra("VIDEOID", id);
//            activity.startActivity(intent);

            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);
        }
    }



    public void onLikeSucess(DatumE datumE)
    {
        mAdapter.update(datumE);
    }

    public void onDisLikeSucess(DatumE datumE)
    {
        mAdapter.updateDislike(datumE);
    }

    public Observable<DatumE> getClickObservable() {
        return mAdapter.getAddtoWatchlistClickedObservable();
    }
    public Observable<DatumE> getDetailClickObservable() {
        return mAdapter.getDetailClickedObservable();
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


    public void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

}
