package com.costar.talkwithidol.app.network;

import com.costar.talkwithidol.app.network.models.ChangeEmail.ChangeEmailParams;
import com.costar.talkwithidol.app.network.models.ChangeMobileResponse.ChangeMobileParams;
import com.costar.talkwithidol.app.network.models.ChangeMobileResponse.ChangeMobileResponse;
import com.costar.talkwithidol.app.network.models.ChangePassword.ChangePasswordParams;
import com.costar.talkwithidol.app.network.models.ChangePassword.ChangePasswordResponse;
import com.costar.talkwithidol.app.network.models.ChildCommentsResponse.ListChildCommentResponse;
import com.costar.talkwithidol.app.network.models.ChildReportParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.NotificationCountResponse.NotificationCountResponse;
import com.costar.talkwithidol.app.network.models.NotificationSeen.NotificationSeenResponse;
import com.costar.talkwithidol.app.network.models.NtificationCountParam;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.PostComment.AddCommentResponse;
import com.costar.talkwithidol.app.network.models.PostComment.PostCommentParams;
import com.costar.talkwithidol.app.network.models.QuestionParams;
import com.costar.talkwithidol.app.network.models.ReportParams;
import com.costar.talkwithidol.app.network.models.ResendEmailVerification.ResendEmailResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.TermsPolicyContact.TermsContactPrivacyResponse;
import com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse.UpdatePreferenceParams;
import com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse.UpdatePreferenceResponse;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ChannelSubscriptionResponse;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.app.network.models.UserPreferences.UserPreferencesResponse;
import com.costar.talkwithidol.app.network.models.VerifyMobileResponse.VerifyMobileResponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.carousel.CarouselResponse;
import com.costar.talkwithidol.app.network.models.channeldetals.ChannelDetailResponse;
import com.costar.talkwithidol.app.network.models.channelhome.ChannelHomeResponse;
import com.costar.talkwithidol.app.network.models.commentsResponse.CommentsResponse;
import com.costar.talkwithidol.app.network.models.communityDetail.CommunityDetailResponse;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.app.network.models.exploreChannel.ExploreChannelResponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.app.network.models.exploreNews.ExploreNewsResponse;
import com.costar.talkwithidol.app.network.models.exploreVideos.ExploreVideosResponse;
import com.costar.talkwithidol.app.network.models.forgotPassword.ForgotPasswordParams;
import com.costar.talkwithidol.app.network.models.forgotPassword.ForgotPasswordResponse;
import com.costar.talkwithidol.app.network.models.login.LoginParams;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.logoutResponse.LogoutResponse;
import com.costar.talkwithidol.app.network.models.newsDetail.NewsDetailResponse;
import com.costar.talkwithidol.app.network.models.notificationlist.NotificationResponse;
import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.app.network.models.profile.UpdateProfileParams;
import com.costar.talkwithidol.app.network.models.promo.PromoResponse;
import com.costar.talkwithidol.app.network.models.questionSubmit.SubmitQuestionResponse;
import com.costar.talkwithidol.app.network.models.register.RegisterParams;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.app.network.models.register.verification.VerificationResponse;
import com.costar.talkwithidol.app.network.models.registerFCM.RegisterFCMIDResponse;
import com.costar.talkwithidol.app.network.models.reportResponse.ReportResponse;
import com.costar.talkwithidol.app.network.models.talkidols.TalkIdolsResponse;
import com.costar.talkwithidol.app.network.models.videoDetail.VideoDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by dell on 8/7/2017.
 */

public interface PadloktNetwork {


    @Headers({"Content-Type: application/json"})
    @POST(Constants.LOGIN)
    Observable<LoginResponse> validateUser(@Body LoginParams loginParams);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.FORGOTPASSWORD)
    Observable<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordParams forgotPasswordParams);

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST(Constants.pushRegister)
    Observable<RegisterFCMIDResponse> registerFCM(@Field("token") String tokenfcm, @Field("type") String type, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.HOMEPAGEEVENT)
    Observable<ExploreEventResponse> getEvents(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.CAROUSEL)
    Observable<CarouselResponse> getCarousel(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.HOMEPAGECHANNLEL)
    Observable<ExploreChannelResponse> getChannel(@Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @GET(Constants.HOMEPAGEVIDEO)
    Observable<ExploreVideosResponse> getVideo(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.HOMEPAGEPROMO)
    Observable<PromoResponse> getPromo(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.NEWS)
    Observable<ExploreNewsResponse> getNews(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.HOMEPAGECOMMUNITY)
    Observable<ExploreCommunitylResponse> getCommunity(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.REGISTER)
    Observable<LoginResponse> registerUser(@Body RegisterParams registerParams);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.USEREULA)
    Observable<EulaResponse> postEula(@Body EulaParams registerParams, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.VERIFICATIONCODE)
    Observable<VerificationResponse> verifyUser(@Body VerificationParams verificationParams);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.TALKWITHIDOL)
    Observable<TalkIdolsResponse> getTalkidol(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<TalkIdolsResponse> getTalkidolPage(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.DETAILVIDEOS)
    Observable<VideoDetailResponse> getVideoDetail(@Path("nid") String nid, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.NEWSDETAIL)
    Observable<NewsDetailResponse> getNewsDetail(@Path("nid") String nid, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.EVENTSDETAIL)
    Observable<EventsDetailResponse> getEventsDetail(@Path("nid") String nid, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.COMMUNITYDETAIL)
    Observable<CommunityDetailResponse> getCommunityDetail(@Path("nid") String nid, @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @GET(Constants.CHANNELDETAIL)
    Observable<ChannelDetailResponse> getChannelDetail(@Path("nid") String nid, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ChannelHomeResponse> getChannelHome(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreChannelResponse> getAllChannel(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreVideosResponse> getAllVideo(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreEventResponse> getAllEvents(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreCommunitylResponse> getAllCommunity(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreNewsResponse> getAllNews(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreEventResponse> getChannelEvent(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreCommunitylResponse> getChannelCommunity(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreNewsResponse> getChannelNews(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreVideosResponse> getChannelVideo(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<NotificationResponse> getNotification(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET
    Observable<NotificationSeenResponse> seenNotification(@Url String url, @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ExploreEventResponse> getWatchlist(@Url String url, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.UPDATEUSER)
    Observable<SettingsProfileResponse> getUserProfile(@Path("uid") String uid, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.FREETRIAL)
    Observable<FreeTrialResponse> freeTrial(@Body FreeTrialParams freeTrialParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.TOGGLELIKE)
    Observable<LikeEntityReponse> likeEntity(@Body LikeEntityParams likeEntityParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.TOGGLEWISHLIST)
    Observable<AddToWatchlistResponse> addTowatchList(@Body AddToWatchlistParams addToWatchlistParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.LOGOUT)
    Observable<LogoutResponse> logout(@Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.TERMSCONDITION)
    Observable<TermsContactPrivacyResponse> getTermsCondition();


    @Headers({"Content-Type: application/json"})
    @GET(Constants.PRIVACYPOLICY)
    Observable<TermsContactPrivacyResponse> getPrivacyPolicy();

    @Headers({"Content-Type: application/json"})
    @GET(Constants.CONTACT)
    Observable<TermsContactPrivacyResponse> getcontact();


    @Headers({"Content-Type: application/json"})
    @GET(Constants.EULA)
    Observable<TermsContactPrivacyResponse> getEula();

    @Headers({"Content-Type: application/json"})
    @POST(Constants.NOTIFICTIONCOUNT)
    Observable<NotificationCountResponse> notificationCount(@Body NtificationCountParam ntificationCountParam, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.PAYDOCKCONFIG)
    Observable<PaydockConfigurationResponse> getPaydockConfig(@Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST
    Observable<PaydockCustomerResponse> getPaydocCustomer(@Url String url, @Body CreditCardParams creditCardParams, @Header("x-user-secret-key") String key);

    @Headers({"Content-Type: application/json"})
    @PUT(Constants.SUBSCRIPTIONSTEP1)
    Observable<SubscriptionStepOneResponse> getSubscriptionOne(@Body ChannelSubsParams channelSubsParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @PUT(Constants.SUBSCRIPTIONSTEP2)
    Observable<SubscriptionStepTwoResponse> getSubscriptionTwo(@Body ChannelSubsParams channelSubsParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.SUBMITQUESTION)
    Observable<SubmitQuestionResponse> submitQestion(@Path("nid") String nid, @Body QuestionParams questionParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.CHECKEVENTSTATE)
    Observable<EventState> checkEventState(@Path("nid") String nid, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @PUT(Constants.UPDATEUSER)
    Observable<ChangePasswordResponse> changePassword(@Body ChangePasswordParams changePasswordParams, @Path("uid") String uid, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @GET(Constants.USERPREFERENCES)
    Observable<UserPreferencesResponse> getUserPreferences(@Path("uid") String uid, @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.USERPREFERENCES)
    Observable<UpdatePreferenceResponse> updatePreferenceResponseObservable(@Body UpdatePreferenceParams updatePreferenceParams, @Path("uid") String uid, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);


    @Headers({"Content-Type: application/json"})
    @GET(Constants.SUBSCRIPTIONS)
    Observable<ChannelSubscriptionResponse> getSubscriptions(@Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.RESENDCONFIRMATIONEMAIL)
    Observable<ResendEmailResponse> resendConfrimationEmail(@Header("Cookie") String cookie, @Path("uid") String uid, @Header("X-CSRF-Token") String token);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.VERIFICATIONCODE)
    Observable<ChangeMobileResponse> requestMobileCode(@Body VerificationParams verificationParams, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.VERIFYCODE)
    Observable<VerifyMobileResponse> verifyMobileCode(@Body ChangeMobileParams changeMobileParams, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);

    @Headers({"Content-Type: application/json"})
    @PUT(Constants.UPDATEUSER)
    Observable<LoginResponse> changeEmail(@Body ChangeEmailParams changeEmailParams, @Path("uid") String uid, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);

    @Headers({"Content-Type: application/json"})
    @PUT(Constants.UPDATEUSER)
    Observable<LoginResponse> updateProfile(@Body UpdateProfileParams updateProfileParams, @Path("uid") String uid, @Header("Cookie") String cookie, @Header("X-CSRF-Token") String token);


    @Headers({"Content-Type: application/json"})
    @GET
    Observable<CommentsResponse> getCommunityComments(@Url String url, @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @GET
    Observable<ListChildCommentResponse> getChildComments(@Url String url, @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.COMMENT)
    Observable<AddCommentResponse> postComment(@Body PostCommentParams postCommentParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.REMOVEFROMWISHLIST)
    Observable<AddToWatchlistResponse> removeFromWatchlist(@Body AddToWatchlistParams addToWatchlistParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.REPORT)
    Observable<ReportResponse> report(@Body ReportParams reportParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.REPORT)
    Observable<ReportResponse> reportChild(@Body ChildReportParams reportParams, @Header("X-CSRF-Token") String token, @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @GET(Constants.USEREULA)
    Observable<EulaResponse> getUserEula(@Header("Cookie") String cookie);

    //
//    @Headers({"Content-Type: application/json"})
//    @PUT(Constants.UPDATEUSER)
//    Observable<ChannelSubscriptionResponse> resendConfrimationEmail(@Path("uid") String uid, @Header("X-CSRF-Token") String token);


}
