package com.costar.talkwithidol.app.network;

import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.profileUpdateResponse.UpdateProfilePicResponse;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.app.network.models.register.verification.VerificationResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;


public class ApiManager {



    public static void UserProfileImageResponse(Callback<UpdateProfilePicResponse> callback, String token, String cookie, MultipartBody.Part file){
        ApiInterface apiInterface =RestServiceGenerator.createService(ApiInterface.class);
        Call<UpdateProfilePicResponse> call=apiInterface.getuserimageresponse(token,cookie,file);
        call.enqueue(callback);
    }



    public static void getVerificationCode(Callback<VerificationResponse> callback, VerificationParams verificationParams) {
        ApiInterface apiInterface = RestServiceGenerator.createService(ApiInterface.class);
        Call<VerificationResponse> call = apiInterface.verifyUserRetrofit(verificationParams);
        call.enqueue(callback);
    }

    public static void addToWatchlist(Callback<AddToWatchlistResponse> callback, AddToWatchlistParams addToWatchlistParams, String token, String cookies) {
        ApiInterface apiInterface = RestServiceGenerator.createService(ApiInterface.class);
        Call<AddToWatchlistResponse> call = apiInterface.addToWatchlist(addToWatchlistParams, token, cookies);
        call.enqueue(callback);
    }


    public static void removeFromWatchList(Callback<AddToWatchlistResponse> callback, AddToWatchlistParams addToWatchlistParams, String token, String cookies) {
        ApiInterface apiInterface = RestServiceGenerator.createService(ApiInterface.class);
        Call<AddToWatchlistResponse> call = apiInterface.addToWatchlist(addToWatchlistParams, token, cookies);
        call.enqueue(callback);
    }

    public static void likeEntity(Callback<LikeEntityReponse> callback, LikeEntityParams likeEntityParams, String token, String cookies) {
        ApiInterface apiInterface = RestServiceGenerator.createService(ApiInterface.class);
        Call<LikeEntityReponse> call = apiInterface.likeEntity(likeEntityParams, token, cookies);
        call.enqueue(callback);
    }
    public static void sendCrditInfo(Callback<PaydockCustomerResponse> callback, CreditCardParams likeEntityParams, String token) {
        ApiInterface apiInterface = RestServiceGenerator.createService(ApiInterface.class);
        Call<PaydockCustomerResponse> call = apiInterface.getPaydocCustomer(likeEntityParams, token);
        call.enqueue(callback);
    }

}
