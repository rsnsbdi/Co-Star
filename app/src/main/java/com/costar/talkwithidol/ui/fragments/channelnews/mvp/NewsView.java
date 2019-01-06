package com.costar.talkwithidol.ui.fragments.channelnews.mvp;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.exploreNews.DatumN;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.costar.talkwithidol.ui.adapters.NewsAdapterN;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class NewsView extends FrameLayout {

    public AppCompatActivity activity;
    RecyclerView recyclerView;
    NewsAdapterN mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    PreferencesManager preferencesManager;
    ShowLoadingDialog showLoadingDialog;
    LinearLayoutManager layoutManager;
    RelativeLayout loading;
    RelativeLayout noContentView;
    TextView noContentMsg;
    private List<DiscoverDTO> discoverList = new ArrayList<>();

    public NewsView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, NewsAdapterN mAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = mAdapter;
        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);
//        showLoading = new ShowLoadingDialog(activity);

        loading = findViewById(R.id.loading_view);
        noContentView = findViewById(R.id.noContentView);
        noContentMsg = findViewById(R.id.msg);

        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("News");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.preferencesManager = preferencesManager;
        toolbar.setVisibility(GONE);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView = findViewById(R.id.recycler_view);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition, 0);
    }


    public void setNewsList(ArrayList<DatumN> exploreNewsResponse) {
        mAdapter.showList(exploreNewsResponse, activity);

    }

    public LinearLayoutManager layoutManager() {
        return layoutManager;
    }


    public void onLikeSucess(DatumN datum) {
        mAdapter.update(datum);
    }

    public void onDisLikeSucess(DatumN datum) {
        mAdapter.updateDislike(datum);
    }


    //like click observable
    public Observable<DatumN> getClickObservable() {
        return mAdapter.getLikeClickedObservable();
    }


    //detail click observable
    public Observable<DatumN> getDetailClickObservable() {
        return mAdapter.getDetailClickedObservable();
    }


    public void StartDetail(String id) {

        Intent intent = new Intent(activity, NewsDtailActivity.class);
        intent.putExtra("VIDEOID", id);
        activity.startActivity(intent);
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

    public void showNoContent(boolean show, String message) {
        if (show) {
            noContentMsg.setText(message);
            noContentView.setVisibility(View.VISIBLE);
        } else {
            noContentView.setVisibility(View.GONE);
        }
    }
}
