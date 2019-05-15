package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRequest {


    public CreateRequest() {
    }

    public CreateRequest(int user_customer_id, int requestservice_id, int service_provider_id, int service_id, String date, String time, String location, int request_status, int city, int service_cat_id) {
        this.user_customer_id = user_customer_id;
        this.requestservice_id = requestservice_id;
        this.service_provider_id = service_provider_id;
        this.service_id = service_id;
        this.date = date;
        this.time = time;
        this.location = location;
        this.request_status = request_status;
        this.city = city;
        this.service_cat_id = service_cat_id;
    }


    @SerializedName("user_customer_id")
    int user_customer_id;


    @SerializedName("requestservice_id")
    int requestservice_id;


    @SerializedName("response")
    String response;


    @SerializedName("service_provider_id")
    int service_provider_id;

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


    @SerializedName("city")

    int city;

    @SerializedName("service_cat_id")

    int service_cat_id;


    public String getResponse() {
        return response;
    }
}
