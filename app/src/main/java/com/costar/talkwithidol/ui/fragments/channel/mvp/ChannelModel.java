package com.costar.talkwithidol.ui.fragments.channel.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ChannelSubscriptionResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;

import io.reactivex.Observable;


public class ChannelModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    PreferencesManager preferencesManager;

    public ChannelModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }

    public Observable<ChannelSubscriptionResponse> getSubscriptionList(String cookie) {
        return padloktNetwork.getSubscriptions(cookie);
    }


    public Observable<PaydockConfigurationResponse> getPaydockConfigurationObservable() {
        return padloktNetwork.getPaydockConfig(preferencesManager.get(Constants.COOKIE));
    }
    public Observable<PaydockCustomerResponse> PaydockCustomerResponseObservable(CreditCardParams creditCardParams){
        String url =preferencesManager.get(Constants.PAYDOCKENDPOINT) + "/customers";
        return padloktNetwork.getPaydocCustomer(url,creditCardParams,ChannelPresenter.key);
    }


    public Observable<SubscriptionStepOneResponse> subscriptionStepOne(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionOne(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<SubscriptionStepOneResponse> subscriptionStepOneWithID(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionOne(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<SubscriptionStepTwoResponse> subscriptionStepTwo(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionTwo(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }




}


