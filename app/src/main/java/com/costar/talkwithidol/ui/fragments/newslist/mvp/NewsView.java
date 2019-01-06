package com.costar.talkwithidol.ui.fragments.newslist.mvp;

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
import com.costar.talkwithidol.app.network.models.exploreNews.DatumN;
import com.costar.talkwithidol.ext.bus.RxBus;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    ShowLoadingDialog progressDialog;
    PreferencesManager preferencesManager;
    NewsAdapterN newsAdapterN;
    LinearLayoutManager layoutManager;
    private List<DiscoverDTO> discoverList = new ArrayList<>();
    ImageView backButton;
    RelativeLayout loading;

    public NewsView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, NewsAdapterN newsAdapterN) {
        super(activity);
        this.activity = activity;
        this.newsAdapterN = newsAdapterN;
        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);
//        progressDialog = new ShowLoadingDialog(activity);
        this.preferencesManager = preferencesManager;
        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("News");
        loading = findViewById(R.id.loading_view);

        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapterN);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v->{
            RxBus.getInstance().send(1);
        });

    }


    public void setNewsList(ArrayList<DatumN> exploreNewsResponse) {
        newsAdapterN.showList(exploreNewsResponse, activity);

    }

    //like click observable
    public Observable<DatumN> getClickObservable() {
        return newsAdapterN.getLikeClickedObservable();
    }

    public void onLikeSucess(DatumN datumN) {
        newsAdapterN.update(datumN);
    }

    public void onDisLikeSucess(DatumN datumN) {
        newsAdapterN.updateDislike(datumN);
    }


    //detail click observable
    public Observable<DatumN> getDetailClickObservable() {
        return newsAdapterN.getDetailClickedObservable();
    }


    public void StartDetail(String id) {

        Intent intent = new Intent(activity, NewsDtailActivity.class);
        intent.putExtra("VIDEOID", id);
        activity.startActivity(intent);
    }

  /* public void setNews(ExploreNewsResponse exploreNewsResponse){

       mAdapter = new NewsAdapter(activity, preferencesManager,exploreNewsResponse, new NewsAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {

               //startNewsDetailActivity(activity);
               Intent intent = new Intent(activity, NewsDtailActivity.class);
               intent.putExtra("VIDEOID",exploreNewsResponse.getData().get(position).getNewsId());
               activity.startActivity(intent);

           }
       });
       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
       recyclerView.setLayoutManager(mLayoutManager);
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.setAdapter(mAdapter);
   }*/

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

    public LinearLayoutManager layoutManager() {
        return layoutManager;
    }

}
