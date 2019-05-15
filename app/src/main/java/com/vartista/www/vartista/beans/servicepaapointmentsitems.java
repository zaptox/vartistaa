package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by khan on 9/6/2018.
 */

public class servicepaapointmentsitems implements Serializable {

    @SerializedName("requestservice_id")
    private String requestservice_id;

    @SerializedName("user_customer_id")
    private String user_customer_id;

    @SerializedName("service_provider_id")
    private String service_provider_id;

    @SerializedName("username")
    private String username;

    @SerializedName("Spname")
    private String spname;

    @SerializedName("service_description")
    private String service_description;

    @SerializedName("image")
    private String Image;

    @SerializedName("location")
    private String location;

    @SerializedName("request_status")
    private String request_status;

    @SerializedName("date")
    private String date;

    @SerializedName("service_title")
    private String service_title;

    @SerializedName("price")
    private String price;

    @SerializedName("name")
    private String name;

    @SerializedName("time")
    private String time;

    @SerializedName("response")
    private String response;

    @SerializedName("image")
    private String image;

    @SerializedName("contact")
    private String contact;


    @SerializedName("service_id")
    private int service_id;

    @SerializedName("rating_status")
    private String rating_status;


    @SerializedName("rating_id")
    private String rating_id;

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getRating_status() {
        return rating_status;
    }

    public void setRating_status(String rating_status) {
        this.rating_status = rating_status;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }



    public servicepaapointmentsitems(String requestservice_id, String user_customer_id, String service_provider_id, String username, String service_description, String location, String request_status, String date, String service_title, String price, String name, String time, String image) {
        this.requestservice_id = requestservice_id;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.username = username;
        this.service_description = service_description;
        this.location = location;
        this.request_status = request_status;
        this.date = date;
        this.service_title = service_title;
        this.price = price;
        this.name = name;
        this.time = time;
        this.image = image;
    }
    public servicepaapointmentsitems(String requestservice_id, String user_customer_id, String service_provider_id, String username, String service_description, String location, String request_status, String date, String service_title, String price, String name, String time, String image,String contact) {
        this.requestservice_id = requestservice_id;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.username = username;
        this.service_description = service_description;
        this.location = location;
        this.request_status = request_status;
        this.date = date;
        this.service_title = service_title;
        this.price = price;
        this.name = name;
        this.time = time;
        this.image = image;
        this.contact=contact;
    }
    public servicepaapointmentsitems(String requestservice_id, String user_customer_id, String service_provider_id, String username, String service_description, String location, String request_status, String date, String service_title, String price, String name, String time, String image,String contact,int service_id) {
        this.requestservice_id = requestservice_id;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.username = username;
        this.service_description = service_description;
        this.location = location;
        this.request_status = request_status;
        this.date = date;
        this.service_title = service_title;
        this.price = price;
        this.name = name;
        this.time = time;
        this.image = image;
        this.contact=contact;
        this.service_id=service_id;
    }
    public servicepaapointmentsitems(String requestservice_id, String user_customer_id, String service_provider_id, String username, String service_description, String location, String request_status, String date, String service_title, String price, String name, String time, String image,String contact,int service_id,String rating_id) {
        this.requestservice_id = requestservice_id;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.username = username;
        this.service_description = service_description;
        this.location = location;
        this.request_status = request_status;
        this.date = date;
        this.service_title = service_title;
        this.price = price;
        this.name = name;
        this.time = time;
        this.image = image;
        this.contact=contact;
        this.service_id=service_id;
        this.rating_id=rating_id;
    }
    public servicepaapointmentsitems(String requestservice_id, String user_customer_id, String service_provider_id, String username, String service_description, String location, String request_status, String date, String service_title, String price, String name, String time, String image,String contact,String rating_status,String rating_id) {
        this.requestservice_id = requestservice_id;
        this.user_customer_id = user_customer_id;
        this.service_provider_id = service_provider_id;
        this.username = username;
        this.service_description = service_description;
        this.location = location;
        this.request_status = request_status;
        this.date = date;
        this.service_title = service_title;
        this.price = price;
        this.name = name;
        this.time = time;
        this.image = image;
        this.contact=contact;
        this.rating_status=rating_status;
        this.rating_id=rating_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRequestservice_id() {
        return requestservice_id;
    }

    public void setRequestservice_id(String requestservice_id) {
        this.requestservice_id = requestservice_id;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getUser_customer_id() {
        return user_customer_id;
    }

    public void setUser_customer_id(String user_customer_id) {
        this.user_customer_id = user_customer_id;
    }


    public String getService_provider_id() {
        return service_provider_id;
    }

    public void setService_provider_id(String service_provider_id) {
        this.service_provider_id = service_provider_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setResponse(String response) {
        this.response = response;
    }

}
