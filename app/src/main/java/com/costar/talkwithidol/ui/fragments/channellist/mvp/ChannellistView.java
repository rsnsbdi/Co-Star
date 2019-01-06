package com.costar.talkwithidol.ui.fragments.channellist.mvp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.costar.talkwithidol.app.network.models.exploreChannel.Datum;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ViewConstructor")
public class ChannellistView extends FrameLayout {

    public AppCompatActivity activity;


    RecyclerView recyclerView;
    ChannellisttAdapter mAdapter;
    private List<DiscoverDTO> discoverList = new ArrayList<>();
    ImageView backButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    GridLayoutManager layoutManager;

    RelativeLayout loading;
    public ChannellistView(@NonNull AppCompatActivity activity, ChannellisttAdapter channellisttAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = channellisttAdapter;
//        progressDialog = new ShowLoadingDialog(activity);

        inflate(activity, R.layout.activity_channellist, this);
        ButterKnife.bind(this);

        textView = toolbar.findViewById(R.id.toolbar_title);
        loading = findViewById(R.id.loading_view);
        textView.setText("Channels");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        layoutManager = new GridLayoutManager(activity, 2);
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


    public  void setChannel(ArrayList<Datum> exploreChannelResponse){
        mAdapter.showList(activity,exploreChannelResponse, position -> {
//            /*startChannelsActivity(activity);*/
//            Intent intent = new Intent(activity, ChannelDetailFragment.class);
//            intent.putExtra("VIDEOID",exploreChannelResponse.get(position).getChannelId());
//            activity.startActivity(intent);
            HomePageActivity.VIDEOID =exploreChannelResponse.get(position).getChannelId();
            loadBus("channelDetail");

        });
//        mAdapter = new ChannellisttAdapter(activity,exploreChannelResponse, new ChannellisttAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                /*startChannelsActivity(activity);*/
//                Intent intent = new Intent(activity, ChannelDetailFragment.class);
//                intent.putExtra("VIDEOID",exploreChannelResponse.get(position).getChannelId());
//                activity.startActivity(intent);
//
//            }
//        });


    }

    private void loadBus(String name){

        RxBus.getInstance().send(name);

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
