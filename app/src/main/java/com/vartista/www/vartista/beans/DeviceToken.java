package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceToken {

    @SerializedName("Response")
    @Expose
    String Response;




    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    @SerializedName("token")
    @Expose
    String token;
    @SerializedName("user_id")
    @Expose
    int user_id;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
