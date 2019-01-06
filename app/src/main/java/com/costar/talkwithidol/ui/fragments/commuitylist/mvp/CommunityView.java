package com.costar.talkwithidol.ui.fragments.commuitylist.mvp;

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
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.DatumC;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.adapters.CommunityAdapter;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class CommunityView extends FrameLayout {

    public AppCompatActivity activity;


    RecyclerView recyclerView;
    CommunityAdapter mAdapter;
    private List<DiscoverDTO> discoverList = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    ShowLoadingDialog progressDialog;
    LinearLayoutManager layoutManager;
    ImageView backButton;
    RelativeLayout loading;

    public CommunityView(@NonNull AppCompatActivity activity, CommunityAdapter communityAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = communityAdapter;
//        progressDialog = new ShowLoadingDialog(activity);

        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);
        loading = findViewById(R.id.loading_view);

        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Community");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v->{
            RxBus.getInstance().send(1);
        });
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition,0);

    }
    public LinearLayoutManager layoutManager(){
        return layoutManager;
    }

    public void setCommunity(ArrayList<DatumC> exploreCommunityResponse){
        mAdapter.showList( exploreCommunityResponse, activity);
    }


    public void StartDetail(String id){

        Intent intent = new Intent(activity, CommunityDtailActivity.class);
        intent.putExtra("VIDEOID",id);
        activity.startActivity(intent);
    }


    //like click observable
    public Observable<DatumC> getClickObservable() {
        return mAdapter.getLikeClickedObservable();
    }


    //detail click observable
    public Observable<DatumC> getDetailClickObservable() {
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

    public void onLikeSucess(DatumC datumC, LikeEntityReponse likeEntityReponse)
    {
        mAdapter.update(datumC, likeEntityReponse);
    }

    public void onDisLikeSucess(DatumC datumC, LikeEntityReponse likeEntityReponse)
    {
        mAdapter.updateDislike(datumC, likeEntityReponse);
    }

    public void onLikeSucess(DatumC datumC)
    {
        mAdapter.update(datumC);
    }

    public void onDisLikeSucess(DatumC datumC)
    {
        mAdapter.updateDislike(datumC);
    }


    public void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

}
