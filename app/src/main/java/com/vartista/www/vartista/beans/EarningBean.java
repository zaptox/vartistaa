package com.vartista.www.vartista.beans;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dell on 2019-02-20.
 */

public class EarningBean implements Serializable{

    @SerializedName("response")
    private String Response;

    @SerializedName("id")
    private int id;

    @SerializedName("service_provider")
    private String service_provider;

    @SerializedName("service_availer")
    private String service_availer;

    @SerializedName("service")
    private String service;

    @SerializedName("location")
    private String location;

    @SerializedName("service_time")
    private String service_time;

    @SerializedName("total_amount")
    private double total_amount;

    @SerializedName("admin_tax")
    private double admin_tax;

    @SerializedName("discount")
    private double discount;

    @SerializedName("user_bonus")
    private double user_bonus;

    @SerializedName("sp_earning")
    private double sp_earning;

    @SerializedName("admin_earning")
    private double admin_earning;

    @SerializedName("date")
    private String date;


    public EarningBean(int id, String service_provider, String service_availer, String service, String location, String service_time, double total_amount, double admin_tax, double discount, double user_bonus, double sp_earning, double admin_earning, String date) {
        this.id = id;
        this.service_provider = service_provider;
        this.service_availer = service_availer;
        this.service = service;
        this.location = location;
        this.service_time = service_time;
        this.total_amount = total_amount;
        this.admin_tax = admin_tax;
        this.discount = discount;
        this.user_bonus = user_bonus;
        this.sp_earning = sp_earning;
        this.admin_earning = admin_earning;
        this.date = date;
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

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public String getService_availer() {
        return service_availer;
    }

    public void setService_availer(String service_availer) {
        this.service_availer = service_availer;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getAdmin_tax() {
        return admin_tax;
    }

    public void setAdmin_tax(double admin_tax) {
        this.admin_tax = admin_tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getUser_bonus() {
        return user_bonus;
    }

    public void setUser_bonus(double user_bonus) {
        this.user_bonus = user_bonus;
    }

    public double getSp_earning() {
        return sp_earning;
    }

    public void setSp_earning(double sp_earning) {
        this.sp_earning = sp_earning;
    }

    public double getAdmin_earning() {
        return admin_earning;
    }

    public void setAdmin_earning(double admin_earning) {
        this.admin_earning = admin_earning;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


