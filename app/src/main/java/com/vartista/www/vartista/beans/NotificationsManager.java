package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsManager {

    @SerializedName("Response")
    @Expose
    String Response;




    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("body")
    @Expose
    String body;

    @SerializedName("user_id")
    @Expose
    String user_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setProvider_id(String provider_id) {
        this.user_id = provider_id;
    }
}
