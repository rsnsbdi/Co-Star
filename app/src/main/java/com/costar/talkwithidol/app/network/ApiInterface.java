package com.costar.talkwithidol.app.network;

import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.profileUpdateResponse.UpdateProfilePicResponse;
import com.costar.talkwithidol.app.network.models.register.RegisterParams;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.app.network.models.register.verification.VerificationResponse;
import com.costar.talkwithidol.ext.Constants;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by kishor on 02/04/17.
 */

public interface ApiInterface {



    @Headers({"Content-Type: application/json"})
    @POST(Constants.REGISTER)
    Call<LoginResponse> registerUserRetrofit(@Body RegisterParams registerParams);



    @Headers({"Content-Type: application/json"})
    @POST(Constants.VERIFICATIONCODE)
    Call<VerificationResponse> verifyUserRetrofit(@Body VerificationParams verificationParams);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.ADDTOUSERWISHLIST)
    Call<AddToWatchlistResponse> addToWatchlist(@Body AddToWatchlistParams addToWatchlistParams, @Header("X-CSRF-Token") String token , @Header("Cookie") String cookie);

    @Headers({"Content-Type: application/json"})
    @POST(Constants.TOGGLELIKE)
    Call<LikeEntityReponse> likeEntity(@Body LikeEntityParams likeEntityParams, @Header("X-CSRF-Token") String token , @Header("Cookie") String cookie);


    @Headers({"Content-Type: application/json"})
    @POST(Constants.PAYDOCK_CUSTOMER)
    Call<PaydockCustomerResponse> getPaydocCustomer(@Body CreditCardParams creditCardParams, @Header("x-user-secret-key") String key);


    @Multipart
    @POST(Constants.UploadPic)
    Call<UpdateProfilePicResponse> getuserimageresponse(@Header("X-CSRF-Token") String token , @Header("Cookie") String cookie, @Part MultipartBody.Part file);
}
