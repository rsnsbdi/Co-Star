package com.costar.talkwithidol.ui;

import com.costar.talkwithidol.app.network.models.NotificationCountResponse.NotificationCountResponse;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;

/**
 * Created by dell on 11/14/2017.
 */

public class RxEvent {



    public static class LoginApiResponse{

        public LoginResponse loginResponse;

        public LoginApiResponse(LoginResponse loginResponse){
            this.loginResponse = loginResponse;
        }

    }


    public static class NoticicatiionApiResponse{

        public NotificationCountResponse notificationCountResponse;

        public NoticicatiionApiResponse(NotificationCountResponse notificationCountResponse){
            this.notificationCountResponse = notificationCountResponse;
        }

    }
}
