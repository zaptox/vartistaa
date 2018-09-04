package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vksoni on 7/11/2018.
 */

public class RequestService {

    public RequestService() {
    }

    public RequestService(int request_service, int user_customer_id, int service_provider_id, int service_id, String date, String time, String location, String city, int service_cat_id, String request_status) {
        this.request_service = request_service;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.service_id = service_id;
        this.date = date;
        this.time = time;
        this.location = location;
        this.city = city;
        this.service_cat_id = service_cat_id;
        this.request_status = request_status;
    }

    @SerializedName("response")
    private String Response;


    @SerializedName("request_service")
    private int request_service;

    @SerializedName("user_customer_id")
    private int user_customer_id;

    @SerializedName("service_provider_id")
    private int service_provider_id;

    @SerializedName("service_id")
    private int service_id;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("location")
    private String location;

    @SerializedName("city")
    private String city;

    @SerializedName("service_cat_id")
    private int service_cat_id;


    @SerializedName("request_status")
    private String request_status;


    public String getResponse(){

    return Response;

}

    public void setResponse(String response) {
        Response = response;
    }

    public int getRequest_service() {
        return request_service;
    }

    public void setRequest_service(int request_service) {
        this.request_service = request_service;
    }

    public int getUser_customer_id() {
        return user_customer_id;
    }

    public void setUser_customer_id(int user_customer_id) {
        this.user_customer_id = user_customer_id;
    }

    public int getService_provider_id() {
        return service_provider_id;
    }

    public void setService_provider_id(int service_provider_id) {
        this.service_provider_id = service_provider_id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getService_cat_id() {
        return service_cat_id;
    }

    public void setService_cat_id(int service_cat_id) {
        this.service_cat_id = service_cat_id;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}
