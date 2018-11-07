package com.vartista.www.vartista.restcalls;


import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vksoni on 7/11/2018.
 */

public interface TokenApiInterface {


    @GET("fcm/deviceToken.php")
    Call<DeviceToken> saveDeviceToken(@Query("user_id") int user_id, @Query("token") String token);
//    Call<User> performUserLogin();

}
