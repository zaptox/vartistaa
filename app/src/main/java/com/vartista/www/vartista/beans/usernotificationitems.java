package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khan on 9/4/2018.
 */

public class usernotificationitems  {

    @SerializedName("Name")
    private String Name;

    @SerializedName("request_status")
    private String request_status;

    @SerializedName("Time")
    private String Time;

//    @SerializedName("response")
//    private String Response;

    public usernotificationitems(String name, String request_status, String time) {
        Name = name;
        this.request_status = request_status;
        Time = time;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

//    public String getResponse() {
//        return Response;
//    }
//
//    public void setResponse(String response) {
//        Response = response;
//    }


}
