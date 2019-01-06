package com.costar.talkwithidol.ext;


public final class Constants {

    public static final String PREFERENCE_NAME = "padlokt_shared_prefs";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String TOKENS = "token";
    public static final String USERID = "userid";

    public static final String FIRSTNAME = "firstname";
    public static final String USERIMAGE = "userimage";

    public static final String LASTNAME = "lastname";

    public static final String EMAIL = "email";

    public static final String MOBILE = "mobile";

    public static final String REFERENCE = "reference";

    public static final String COUNTRY ="country";

    public static final String AVATERIMAGE ="avatarimage";
    public static final String PROFILEIMAGESetting ="profileimage";

    public static String PROFILEIMAGE = "profile_image";
    public static final String ISFIRSTLAUNCH = "firstlaunch";
    public static final String SWITCH1 = "switch1";

    public static final String SWITCH2 = "switch2";
    public static final String SWITCH3 = "switch3";
    public static final String SWITCH4 = "switch4";


    public static  String TEMPMOBILE = "mobile";
    public static  String COUNTRYCODE = "countrycode";

    public static String AccessDenied = "Access denied for user anonymous";



    public static final String CUSTOMER_ID = "id";
    public static final String VerificationTOKENS = "verificationtoken";

    public static final String COOKIE = "cookie";

    public static final String PAYDOCK_PUBLIC_KEY = "public key";
    public static final String PAYDOCK_SECRET_KEY = "secretkey";
    public static final String GATEWAYID = "gateway";
    public static final String PAYDOCKENDPOINT = "endpoint";

    public static final String PAYDOCK_AUTHORISATION = "OAuth oauth_consumer_key=\"Cwzzk4mTDbMK4mBTM8dxqdFaMggKZAF5\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1505719460\",oauth_nonce=\"0PU2xZ22Cc8\",oauth_version=\"1.0\",oauth_signature=\"Fdzlhr%2FV3aVM4hzTZMgKi1pYTcE%3D\"";
    // Network Cache
    public static final String HTTP_DIR_CACHE = "padlokt";
    public static final int CACHE_SIZE = 10 * 1024 * 1024;
    //Debug Tag
    public static final String LOG_TAG = "PADLOKT_DEBUG";

    public static  final String CurrentFragment="CurrentFragment";
    //Api Calls
   // public static final String BASE_URL = "https://demo.mypadlokt.com/api-v1/";
   public static final String BASE_URL = "https://costarexperience.com/api-v1/";

    public static final String PAYDOCK_URL = "https://api-sandbox.paydock.com/v1/";
    public static final String REGISTER = BASE_URL + "accounts/register";
    public static final String LOGIN = BASE_URL + "accounts/login";
    public static final String pushRegister = BASE_URL + "push";
    public static final String LOGOUT = BASE_URL + "accounts/logout";
    public static final String TOKEN = BASE_URL + "accounts/token";
    public static final String USERINFO = BASE_URL + "variables/connect";
    public static final String VERIFICATIONCODE = BASE_URL + "mobile/request_verification_code";
    public static final String VERIFYCODE = BASE_URL + "mobile/verify_by_code";
    public static final String UPDATEUSER = BASE_URL + "accounts/{uid}";
    public static final String ProfilePic = BASE_URL + "accounts/";
    public static final String USERPREFERENCES = BASE_URL + "accounts/{uid}/preferences";
    public static final String CAROUSEL = BASE_URL + "feeds/carousel";

    public static final String TALKWITHIDOL = BASE_URL + "feeds/talk-idols";
    public static final String HOMEPAGECHANNLEL = BASE_URL + "feeds/feature-channels";
    public static final String HOMEPAGEEVENT = BASE_URL + "feeds/feature-events";
    public static final String HOMEPAGEVIDEO = BASE_URL + "feeds/latest-videos";
    public static final String HOMEPAGEPROMO = BASE_URL + "feeds/feature-promos";
    public static final String HOMEPAGECOMMUNITY = BASE_URL + "feeds/latest-community";
    public static final String NEWS = BASE_URL + "feeds/latest-news";
    //for browse all
    public static final String ALLCHANNELS = BASE_URL + "channels";
    public static final String ALLEVENTS = BASE_URL + "events";
    public static final String ALLVIDEOS = BASE_URL + "videos";
    public static final String ALLNEWS = BASE_URL + "news";
    public static final String ALLCOMMUNITY = BASE_URL + "community";
    public static final String DETAILVIDEOS = BASE_URL + "videos/{nid}";
    public static final String NEWSDETAIL = BASE_URL + "news/{nid}";
    public static final String EVENTSDETAIL = BASE_URL + "events/{nid}";
    public static final String COMMUNITYDETAIL = BASE_URL + "community/{nid}";
    public static final String COMMUNITYCOMMUNITY = BASE_URL + "community/{nid}/comments";
    public static final String CHANNELDETAIL = BASE_URL + "channels/{nid}";
    public static final String CHANNELHOME = BASE_URL + "channels/";
    public static final String CHANNELEVENT = BASE_URL + "channels/";
    public static final String CHANNELNEWS = BASE_URL + "channels/";
    public static final String CHANNELVIDEOS = BASE_URL + "channels/";
    public static final String CHANNELCOMMUNITY = BASE_URL + "channels/";
    public static final String ADDTOUSERWISHLIST = BASE_URL + "wishlist/addToWishlist";
    public static final String TOGGLEWISHLIST = BASE_URL + "wishlist/userWishlistToggle";

    public static final String WATCHLIST = BASE_URL + "wishlist";
    public static final String TOGGLELIKE = BASE_URL + "likes/userLikeToggle";
    public static final String FREETRIAL = BASE_URL + "channels/activate_freetrial";
    public static final String SUBSCRIBE_NOW = BASE_URL + "activate_freetrial";
    public static final String NOTIFICATION = BASE_URL + "notifications/";
    public static final String PAYDOCKCONFIG = BASE_URL + "configs/paydock";
    public static final String PAYDOCK_CUSTOMER = PAYDOCK_URL + "customers";

    public static final String SUBSCRIPTIONSTEP1 = BASE_URL + "subscriptions/verify";
    public static final String SUBSCRIPTIONSTEP2 = BASE_URL + "subscriptions/confirmed";

    public static final String SUBSCRIPTIONS = BASE_URL + "subscriptions";
    public static final String REPORT = BASE_URL + "eula/report";



    public static final String FORGOTPASSWORD = BASE_URL + "accounts/request_new_password";

    public static final String RESENDCONFIRMATIONEMAIL = BASE_URL + "accounts/{uid}/resend_email_verification";

    public static final String COMMUNITY_COMMENTS = BASE_URL + "community";


    public static final String CHILD_COMMENTS = BASE_URL + "comments";


    public static final String COMMENT = BASE_URL + "comments";
    public static final String REMOVEFROMWISHLIST = BASE_URL + "wishlist/removeFromWishlist";

    public static final String UploadPic = BASE_URL + "accounts/update_profile_picture";

    public static final String NOTIFICTIONCOUNT = BASE_URL + "notifications/countAll";


    public static final String TERMSCONDITION = BASE_URL + "pages/terms-conditions";
    public static final String PRIVACYPOLICY = BASE_URL + "pages/privacy-policy";
    public static final String CONTACT = BASE_URL + "pages/contact-details";
    public static final String EULA = BASE_URL + "pages/license-agreement";
    public static final String USEREULA = BASE_URL + "eula";

    private Constants() {

    }

    public static final String SUBMITQUESTION = BASE_URL + "events/{nid}/register_question";
    public static final String CHECKEVENTSTATE = BASE_URL + "events/{nid}/state_mode";

}
