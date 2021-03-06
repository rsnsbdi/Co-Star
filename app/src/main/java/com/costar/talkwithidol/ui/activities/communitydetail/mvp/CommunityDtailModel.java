package com.costar.talkwithidol.ui.activities.communitydetail.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.PostComment.AddCommentResponse;
import com.costar.talkwithidol.app.network.models.PostComment.PostCommentParams;
import com.costar.talkwithidol.app.network.models.ReportParams;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.commentsResponse.CommentsResponse;
import com.costar.talkwithidol.app.network.models.communityDetail.CommunityDetailResponse;
import com.costar.talkwithidol.app.network.models.reportResponse.ReportResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;

import io.reactivex.Observable;


public class CommunityDtailModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private PreferencesManager preferencesManager;

    public CommunityDtailModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }


    public Observable<LikeEntityReponse> getLikeEntittyObservable(LikeEntityParams likeEntityParams) {
        return padloktNetwork.likeEntity(likeEntityParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<CommunityDetailResponse> getCommunityDetailObservable(String id, String cookie) {
        return padloktNetwork.getCommunityDetail(id, cookie);
    }


    public Observable<ReportResponse> reportObservable(ReportParams reportParams) {
        return padloktNetwork.report(reportParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<CommentsResponse> getCommunityCommentsObservable(int page, String id, String cookie) {
        String url = Constants.COMMUNITY_COMMENTS + "/" + id + "/comments" + "?page=" + page;
        return padloktNetwork.getCommunityComments(url, cookie);
    }


    public Observable<PaydockConfigurationResponse> getPaydockConfigurationObservable() {
        return padloktNetwork.getPaydockConfig(preferencesManager.get(Constants.COOKIE));
    }

    public Observable<PaydockCustomerResponse> PaydockCustomerResponseObservable(CreditCardParams creditCardParams) {
        String url = preferencesManager.get(Constants.PAYDOCKENDPOINT) + "/customers";
        return padloktNetwork.getPaydocCustomer(url, creditCardParams, preferencesManager.get(Constants.PAYDOCK_SECRET_KEY));
    }


    public Observable<SubscriptionStepOneResponse> subscriptionStepOne(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionOne(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<SubscriptionStepTwoResponse> CustomerNewSubscription(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionTwo(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<SubscriptionStepTwoResponse> subscriptionStepTwo(ChannelSubsParams channelSubsParams) {
        return padloktNetwork.getSubscriptionTwo(channelSubsParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<FreeTrialResponse> freeTrialActivationObservable(FreeTrialParams freeTrialParams) {
        return padloktNetwork.freeTrial(freeTrialParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<AddCommentResponse> postCommentObservable(PostCommentParams postCommentParams) {
        return padloktNetwork.postComment(postCommentParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public String getData(String key) {
        return preferencesManager.get(key);
    }

    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }
}


