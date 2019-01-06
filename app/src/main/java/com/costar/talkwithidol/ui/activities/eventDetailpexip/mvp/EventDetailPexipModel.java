package com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;

import io.reactivex.Observable;


public class EventDetailPexipModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
private PreferencesManager preferencesManager;

    public EventDetailPexipModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager=preferencesManager;

    }


    public Observable<EventsDetailResponse> getEventDetailObservable(String id, String cookie) {
        return padloktNetwork.getEventsDetail(id, cookie);
    }

    public Observable<AddToWatchlistResponse> getAddToWatchList(AddToWatchlistParams addToWatchlistParams) {
        return padloktNetwork.addTowatchList(addToWatchlistParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<PaydockConfigurationResponse> getPaydockConfigurationObservable() {
        return padloktNetwork.getPaydockConfig(preferencesManager.get(Constants.COOKIE));
    }

    public Observable<FreeTrialResponse> freeTrialActivationObservable(FreeTrialParams freeTrialParams) {
        return padloktNetwork.freeTrial(freeTrialParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<PaydockCustomerResponse> PaydockCustomerResponseObservable(CreditCardParams creditCardParams){
        String url =preferencesManager.get(Constants.PAYDOCKENDPOINT) + "/customers";

        return padloktNetwork.getPaydocCustomer(url,creditCardParams, preferencesManager.get(Constants.PAYDOCK_SECRET_KEY));
    }


    public Observable<SubscriptionStepOneResponse> subscriptionStepOne(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionOne(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<SubscriptionStepTwoResponse> subscriptionStepTwo(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionTwo(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }

    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }
}


