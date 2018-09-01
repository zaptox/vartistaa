package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 2018-08-05.
 */

public class Requests {
    @SerializedName("response")
    @Expose
    String response;

    @SerializedName("user_customer_id")
    @Expose

    int user_customer_id;

    @SerializedName("user_service_id")
    @Expose

    int user_service_id;

    @SerializedName("service_id")
    @Expose
    int service_id;

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("time")
    @Expose
    String time;

    public Requests(int user_customer_id, int user_service_id, int service_id, String date, String time) {
        this.user_customer_id = user_customer_id;
        this.user_service_id = user_service_id;
        this.service_id = service_id;
        this.date = date;
        this.time = time;
    }

    public int getUser_customer_id() {
        return user_customer_id;
    }

    public void setUser_customer_id(int user_customer_id) {
        this.user_customer_id = user_customer_id;
    }

    public int getUser_service_id() {
        return user_service_id;
    }

    public void setUser_service_id(int user_service_id) {
        this.user_service_id = user_service_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResponse() {
        return response;
    }
}
