package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequets {

    @SerializedName("Response")
    @Expose
    String Response;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public int getRequests_status() {
        return requests_status;
    }

    public void setRequests_status(int requests_status) {
        this.requests_status = requests_status;
    }

    @SerializedName("reqservice_id")
    @Expose
    int reqservice_id;
    @SerializedName("username")
    @Expose
    String username;
    @SerializedName("requests_status")
    @Expose
    int requests_status;
    @SerializedName("date")    @Expose
    String date;
    @SerializedName("time")
    @Expose
    String time;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("user_customer_id")
    @Expose
    int user_customer_id;
    @SerializedName("service_provider_id")
    @Expose
    int service_provider_id;
    @SerializedName("service_id")
    @Expose
    int service_id;
    @SerializedName("service_cat_id")
    @Expose
    int service_cat_id;
    @SerializedName("service_title")
    @Expose
    String service_title;
    @SerializedName("service_description")
    @Expose
    String service_description;

    @SerializedName("price")
    @Expose
    double price;
    @SerializedName("catgname")
    @Expose
    String catgname;

    @SerializedName("user_image")
    @Expose
    String user_image;

    @SerializedName("reqeustsend_at")
    @Expose
    String reqeustsend_at;



    public ServiceRequets(int reqservice_id, String username, int requests_status, String date, String time, String location, int user_customer_id, int service_provider_id, int service_id, int service_cat_id, String service_title,double price,String service_description, String catgname) {
        this.reqservice_id = reqservice_id;
        this.username = username;
        this.requests_status = requests_status;
        this.date = date;
        this.time = time;
        this.location = location;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.service_id = service_id;
        this.service_cat_id = service_cat_id;
        this.service_title = service_title;
        this.price = price;
        this.service_description= service_description;
        this.catgname = catgname;

    }

    public ServiceRequets(int reqservice_id, String username, int requests_status, String date, String time, String location, int user_customer_id, int service_provider_id, int service_id, int service_cat_id, String service_title,double price,String service_description, String catgname,String user_image) {
        this.reqservice_id = reqservice_id;
        this.username = username;
        this.requests_status = requests_status;
        this.date = date;
        this.time = time;
        this.location = location;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.service_id = service_id;
        this.service_cat_id = service_cat_id;
        this.service_title = service_title;
        this.price = price;
        this.service_description= service_description;
        this.catgname = catgname;
        this.user_image=user_image;
    }

    public ServiceRequets(int reqservice_id, String username, int requests_status, String date, String time, String location, int user_customer_id, int service_provider_id, int service_id, int service_cat_id, String service_title,double price,String service_description, String catgname,String user_image,String reqeustsend_at) {
        this.reqservice_id = reqservice_id;
        this.username = username;
        this.requests_status = requests_status;
        this.date = date;
        this.time = time;
        this.location = location;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.service_id = service_id;
        this.service_cat_id = service_cat_id;
        this.service_title = service_title;
        this.price = price;
        this.service_description= service_description;
        this.catgname = catgname;
        this.user_image=user_image;
        this.reqeustsend_at= reqeustsend_at;
    }

    public String getReqeustsend_at() {
        return reqeustsend_at;
    }

    public void setReqeustsend_at(String reqeustsend_at) {
        this.reqeustsend_at = reqeustsend_at;
    }


    //
//    public ServiceRequets() {
//    }


    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getReqservice_id() {
        return reqservice_id;
    }

    public void setReqservice_id(int reqservice_id) {
        this.reqservice_id = reqservice_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getService_cat_id() {
        return service_cat_id;
    }

    public void setService_cat_id(int service_cat_id) {
        this.service_cat_id = service_cat_id;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public String getCatgname() {
        return catgname;
    }

    public void setCatgname(String catgname) {
        this.catgname = catgname;
    }


}
