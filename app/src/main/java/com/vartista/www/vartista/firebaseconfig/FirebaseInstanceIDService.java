package com.vartista.www.vartista.firebaseconfig;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/registerToken.php";

    @Override
    public void onTokenRefresh() {

        String token=FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }

    private void registerToken(String token) {

        OkHttpClient client=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("Token",token)
                .build();
        Request request=new Request.Builder()
                .url(UPLOAD_URL)
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
        }catch (IOException i){

        }

    }

}
