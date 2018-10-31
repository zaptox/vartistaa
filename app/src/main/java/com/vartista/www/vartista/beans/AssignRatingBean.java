package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khan on 10/30/2018.
 */

public class AssignRatingBean {

    @SerializedName("response")
    private String Response;

    @SerializedName("id")
    private int id;

    @SerializedName("stars")
    private double stars;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("service_p_id")
    private int service_p_id;


    @SerializedName("service_id")
    private int service_id;

    @SerializedName("user_remarks")
    private String user_remarks;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;


    public AssignRatingBean(int id, double stars, int user_id, int service_p_id, int service_id, String user_remarks, String date, String time) {
        this.id = id;
        this.stars = stars;
        this.user_id = user_id;
        this.service_p_id = service_p_id;
        this.service_id = service_id;
        this.user_remarks = user_remarks;
        this.date = date;
        this.time = time;
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

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_p_id() {
        return service_p_id;
    }

    public void setService_p_id(int service_p_id) {
        this.service_p_id = service_p_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getUser_remarks() {
        return user_remarks;
    }

    public void setUser_remarks(String user_remarks) {
        this.user_remarks = user_remarks;
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
}
