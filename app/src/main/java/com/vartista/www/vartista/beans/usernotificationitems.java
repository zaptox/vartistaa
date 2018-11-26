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

    @SerializedName("sp_status")
    private String sp_status;

    @SerializedName("Time")
    private String Time;

    @SerializedName("service_title")
    private String service_title;

    @SerializedName("price")
    private double price;

    @SerializedName("title")
    private String title;

    @SerializedName("msg")
    private String msg;

    @SerializedName("notificationid")
    private String notificationid;

    @SerializedName("created_at")
    private String created_at;


    @SerializedName("response")
    private String Response;



    public usernotificationitems(String name, String request_status, String time , String service_title ,double price) {
        Name = name;
        this.request_status = request_status;
        Time = time;
        this.service_title = service_title;
        this.price = price;
    }
    public usernotificationitems(String notificationid,String name,String title, String msg, String created_at ,String sp_status) {
        this.notificationid = notificationid;
        Name = name;
        this.title = title;
        this.msg = msg;
        this.created_at = created_at;
        this.sp_status = sp_status;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSp_status() { return sp_status; }

    public void setSp_status(String sp_status) {
        this.sp_status = sp_status;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getservice_title() {
        return service_title;
    }

    public void setservice_title(String servicetitle) {
        service_title = servicetitle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }


}
