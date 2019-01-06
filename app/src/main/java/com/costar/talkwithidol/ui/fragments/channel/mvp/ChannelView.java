package com.costar.talkwithidol.ui.fragments.channel.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ChannelSubscriptionResponse;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ExpiringChannel;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.SubscribedChannel;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.UnsubscribedChannel;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.IosSubscriberDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class ChannelView extends FrameLayout {

    public static ArrayList<String> subscribedArrayList = new ArrayList<>();
    public static ArrayList<String> unsubscribedArrayList = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view1)
    RecyclerView recyclerView1;
    @BindView(R.id.recycler_view2)
    RecyclerView recyclerView2;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView billingDescription;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.cycle)
    TextView cycle;
    @BindView(R.id.subsInfo)
    LinearLayout subsInfo;

    @BindView(R.id.billinginfo)
    RelativeLayout billinginfo;

    ProgressDialog progressDialog = new ProgressDialog(getContext());
    ShowLoadingDialog showLoadingDialog;
    SubscribedChannelAdapter subscribedChannelAdapter;
    UnSubscribedChannelAdapter unSubscribedChannelAdapter;
    ExpiringChannelAdapter expiringChannelAdapter;
    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    RelativeLayout loading;
    public AppCompatActivity activity;
    IosSubscriberDialog  iosSubscriberDialog;

    public ChannelView(@NonNull AppCompatActivity activity, DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog,
                       SubscribedChannelAdapter subscribedChannelAdapter, UnSubscribedChannelAdapter unSubscribedChannelAdapter,
                       ExpiringChannelAdapter expiringChannelAdapter, IosSubscriberDialog iosSubscriberDialog) {
        super(activity);
        this.activity = activity;
        this.deniedDialog = deniedDialog;
        this.confirmDialog = confirmDialog;
        this.creditCardDialog = creditCardDialog;
        this.iosSubscriberDialog = iosSubscriberDialog;
        this.subscribedChannelAdapter = subscribedChannelAdapter;
        this.unSubscribedChannelAdapter = unSubscribedChannelAdapter;
        this.expiringChannelAdapter = expiringChannelAdapter;
//        showLoading = new ShowLoadingDialog(activity);
        progressDialog.setMessage("Processing...");
        inflate(activity, R.layout.channel_setting, this);
        ButterKnife.bind(this);
        loading = findViewById(R.id.loading_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subscribedChannelAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(activity);
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(expiringChannelAdapter);
        recyclerView1.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(activity);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(unSubscribedChannelAdapter);
        recyclerView2.setNestedScrollingEnabled(false);


    }

    public void setChannels(ChannelSubscriptionResponse channelSubscriptionResponse) {
        if (channelSubscriptionResponse.getData().getSubscribedChannels().size() != 0) {
            subscribedChannelAdapter.showList(channelSubscriptionResponse, activity);
        }
        if (channelSubscriptionResponse.getData().getUnsubscribedChannels().size() != 0) {
            unSubscribedChannelAdapter.showList(channelSubscriptionResponse, activity);
        }
        if (channelSubscriptionResponse.getData().getExpiringChannels().size() != 0) {
            expiringChannelAdapter.showList(channelSubscriptionResponse, activity);
        }

        if (channelSubscriptionResponse.getData().getSubscriptionInfo() != null) {
            if (channelSubscriptionResponse.getData().getSubscriptionInfo().getSubscribed_gateway().equals("apple")) {
                billinginfo.setVisibility(View.GONE);

            } else {
                title.setText(channelSubscriptionResponse.getData().getSubscriptionInfo().getTitle());
                billingDescription.setText(channelSubscriptionResponse.getData().getSubscriptionInfo().getBillingMessage());
                amount.setText(channelSubscriptionResponse.getData().getSubscriptionInfo().getCurrentBill() + " " + channelSubscriptionResponse.getData().getSubscriptionInfo().getCycle().toUpperCase());
            }

//            cycle.setData(channelSubscriptionResponse.getData().getSubscriptionInfo().getCycle());
        } else {
            subsInfo.setVisibility(View.GONE);
        }

    }

    //detail click observable
    public Observable<Object> confirmButtonObservable() {
        return confirmDialog.confirmButtonObservable();
    }


//    public Observable<Object> updateSubscriptionObservable() {
//        return RxView.clicks(done);
//    }

    public Observable<SubscribedChannel> getSubscribedToggleClickObservable() {
        return subscribedChannelAdapter.getToggleClickObservable();
    }


    public Observable<UnsubscribedChannel> getUnSubscribedToggleClickObservable() {
        return unSubscribedChannelAdapter.getToggleClickObservable();
    }

    public Observable<ExpiringChannel> getExpiringToggleClickObservable() {
        return expiringChannelAdapter.getToggleClickObservable();
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        } else {
            progressDialog.cancel();
        }

    }


    public void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

}
