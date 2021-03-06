package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khan on 10/16/2018.
 */

public class RatingsReviewDetailBean {

    @SerializedName("response")
    private String Response;


    @SerializedName("id")
    private int id;

    @SerializedName("Stars")
    private Float Stars;

    @SerializedName("UserName")
    private String UserName;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("SpName")
    private String SpName;

    @SerializedName("service_p_id")
    private int service_p_id;

    @SerializedName("service_id")
    private String service_id;

    @SerializedName("service_tittle")
    private String service_tittle;

    @SerializedName("user_remarks")
    private String user_remarks;

    @SerializedName("Date")
    private String Date;

    @SerializedName("Time")
    private String Time;

    public RatingsReviewDetailBean(int id, Float stars, String userName, String user_id, String spName, int service_p_id, String service_id, String service_tittle, String user_remarks, String date, String time) {
        this.id = id;
        Stars = stars;
        UserName = userName;
        this.user_id = user_id;
        SpName = spName;
        this.service_p_id = service_p_id;
        this.service_id = service_id;
        this.service_tittle = service_tittle;
        this.user_remarks = user_remarks;
        Date = date;
        Time = time;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getStars() {
        return Stars;
    }

    public void setStars(Float stars) {
        Stars = stars;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSpName() {
        return SpName;
    }

    public void setSpName(String spName) {
        SpName = spName;
    }

    public int getService_p_id() {
        return service_p_id;
    }

    public void setService_p_id(int service_p_id) {
        this.service_p_id = service_p_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_tittle() {
        return service_tittle;
    }

    public void setService_tittle(String service_tittle) {
        this.service_tittle = service_tittle;
    }

    public String getUser_remarks() {
        return user_remarks;
    }

    public void setUser_remarks(String user_remarks) {
        this.user_remarks = user_remarks;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
