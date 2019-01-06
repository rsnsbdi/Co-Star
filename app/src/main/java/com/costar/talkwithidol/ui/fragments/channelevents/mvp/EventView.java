package com.costar.talkwithidol.ui.fragments.channelevents.mvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.costar.talkwithidol.app.network.models.exploreEvent.DatumE;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.costar.talkwithidol.ui.adapters.EventAdapter;
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
    Dialog dialog = null;
    PreferencesManager preferencesManager;
    public AppCompatActivity activity;
    private List<DiscoverDTO> discoverList = new ArrayList<>();
    LinearLayoutManager layoutManager;
//    ShowLoadingDialog showLoading;
    RelativeLayout loading;
    RelativeLayout noContentView;
    TextView noContentMsg;
    public EventView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, EventAdapter eventAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = eventAdapter;
        inflate(activity, R.layout.fragment_notifications, this);
//        showLoading = new ShowLoadingDialog(activity);
        ButterKnife.bind(this);
        loading = findViewById(R.id.loading_view);
        noContentView = findViewById(R.id.noContentView);
        noContentMsg = findViewById(R.id.msg);
        this.preferencesManager = preferencesManager;
        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Events");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setVisibility(GONE);

        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition,0);

    }


    public void onLikeSucess(DatumE datum)
    {
        mAdapter.update(datum);
    }

    public void onDisLikeSucess(DatumE datum)
    {
        mAdapter.updateDislike(datum);
    }

    public LinearLayoutManager layoutManager(){
        return layoutManager;
    }


    public void setEvent(ArrayList<DatumE> exploreEventResponse ) {
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

            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        }else if(mode.equals("participent_vmr")){
            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);
        }
    }


    //like click observable
    public Observable<DatumE> getClickObservable() {
        return mAdapter.getAddtoWatchlistClickedObservable();
    }


    //detail click observable
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

    public void openDiaog(Activity activity) {
        dialog = new Dialog(activity, R.style.DialogStyle);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.event_live);

        dialog.show();
    }

    public void openDiaogNotstarted(Activity activity) {
        dialog = new Dialog(activity, R.style.DialogStyle);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.event_live_not_started);

        dialog.show();
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
            loading.setVisibility(View.GONE);
        } else {
            noContentView.setVisibility(View.GONE);
        }
    }


}
