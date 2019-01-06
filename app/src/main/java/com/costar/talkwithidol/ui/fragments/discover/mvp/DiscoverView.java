package com.costar.talkwithidol.ui.fragments.discover.mvp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.bus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ViewConstructor")
public class DiscoverView extends FrameLayout {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    private AppCompatActivity activity;
    private List<DiscoverDTO> discoverList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DiscoverAdapter mAdapter;

    public DiscoverView(@NonNull AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        inflate(activity, R.layout.fragment_discover, this);
        ButterKnife.bind(this);

        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Discover");

        activity.setSupportActionBar(toolbar);
        AppUtils.transparentStatusBar(activity.getWindow());
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new DiscoverAdapter(discoverList, position -> {

            if (position == 0) {
                loadBus("eventList");


            } else if (position == 1) {

                loadBus("channelList");

            } else if (position == 2) {
                loadBus("videoList");


            } else if (position == 3) {
                loadBus("newsList");


            } else if (position == 4) {
                loadBus("communityList");


            } else if (position == 5) {

                loadBus("watchList");
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }

    private void loadBus(String name) {
        try {
            RxBus.getInstance().send(name);
        } catch (Exception e) {

        }


    }


    public void prepareDiscoverData() {

        DiscoverDTO discoverDTO = new DiscoverDTO("EVENTS", "See a list of upcoming FanCasts, StarCasts and video releases. Register to participate in events or add them to your watch list.", R.drawable.events);
        discoverList.add(discoverDTO);
        discoverDTO = new DiscoverDTO("CHANNELS", "Explore the channels of your favourite celebrities to see their exclusive content all in one place.", R.drawable.channels);
        discoverList.add(discoverDTO);

        discoverDTO = new DiscoverDTO("VIDEOS", "View previous FanCasts, StarCasts and Insider Series Episodes, anywhere, anytime.", R.drawable.videos);
        discoverList.add(discoverDTO);

        discoverDTO = new DiscoverDTO("NEWS", "Keep up to date with the latest news posts from the stars.", R.drawable.news);
        discoverList.add(discoverDTO);

        discoverDTO = new DiscoverDTO("COMMUNITY", "Get involved with fan forums and discuss the big topics with the stars themselves.", R.drawable.community);
        discoverList.add(discoverDTO);

        discoverDTO = new DiscoverDTO("MY WATCHLIST", "See upcoming events that you have added to your watch list.", R.drawable.watchlist);
        discoverList.add(discoverDTO);

        mAdapter.notifyDataSetChanged();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
