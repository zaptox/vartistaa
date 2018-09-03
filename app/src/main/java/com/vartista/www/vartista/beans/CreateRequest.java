package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRequest {


    public CreateRequest() {
    }

    public CreateRequest(int user_customer_id, int user_service_id, int service_id, String date, String time, String location, int request_status, int service_cat_id) {
        this.user_customer_id = user_customer_id;
        this.user_service_id = user_service_id;
        this.service_id = service_id;
        this.date = date;
        this.time = time;
        this.location = location;
        this.request_status = request_status;
        this.service_cat_id = service_cat_id;
    }

    @SerializedName("user_customer_id")
    int user_customer_id;


    @SerializedName("response")
    String response;


    @SerializedName("user_service_id")
    int user_service_id;

    @SerializedName("service_id")
    int service_id;

    @SerializedName("date")
    String date ;

    @SerializedName("time")
    String time;

    @SerializedName("location")
    String location;

    @SerializedName("request_status")

    int request_status;

    @SerializedName("service_cat_id")

    int service_cat_id;

    public int getUser_customer_id() {
        return user_customer_id;
    }

    public int getUser_service_id() {
        return user_service_id;
    }

    public int getService_id() {
        return service_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public int getRequest_status() {
        return request_status;
    }

    public int getService_cat_id() {
        return service_cat_id;
    }

    public String getResponse() {
        return response;
    }
}
