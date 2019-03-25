package com.vartista.www.vartista.firebaseconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.modules.general.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by delaroy on 10/8/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);

        int user_id=ob.getInt("user_id",0);
        if(user_id!=0){
            storeDeviceToken(refreshedToken,user_id);
          try{}catch (Exception e){

          }

            }
  }




    public void storeDeviceToken(String refreshedToken,int user_id){


        Call<DeviceToken> call = HomeActivity.tokenApiInterface.saveDeviceToken
                (user_id,FirebaseInstanceId.getInstance().getToken());
        call.enqueue(new Callback<DeviceToken>() {
            @Override
            public void onResponse(Call<DeviceToken> call, Response<DeviceToken> response) {

                if (response.isSuccessful()) {
                    //for debugging
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "Token stored", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                }
            }

            @Override
            public void onFailure(Call<DeviceToken> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("errorinstoredevicetoken",t.getMessage());
            }

        });



    }
}
