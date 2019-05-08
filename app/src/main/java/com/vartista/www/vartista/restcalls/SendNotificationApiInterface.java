package com.vartista.www.vartista.restcalls;


import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.beans.NotificationsManager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vksoni on 7/11/2018.
 */

public interface SendNotificationApiInterface {


    @GET("fcm/sendNotification.php")
    Call<NotificationsManager> sendPushNotification(@Query("user_id") int user_id, @Query("body") String body, @Query("title") String title);
//    Call<User> performUserLogin();

}
