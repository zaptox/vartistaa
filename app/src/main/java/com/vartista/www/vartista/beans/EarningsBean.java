package com.vartista.www.vartista.beans;

public class EarningsBean {


    private int sp_id;
    private int user_id;
    private int service_id;
    private int request_service_id;
    private double total_amout;
    private double admin_tax;
    private double discount;
    private double user_bonus;
    private double sp_earning;
    private double admin_earning;
    private String date;
    private String response;


    public EarningsBean(int sp_id, int user_id, int service_id, int request_service_id, double total_amout, double admin_tax, double discount, double user_bonus, double sp_earning, double admin_earning, String date) {
        this.sp_id = sp_id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.request_service_id = request_service_id;
        this.total_amout = total_amout;
        this.admin_tax = admin_tax;
        this.discount = discount;
        this.user_bonus = user_bonus;
        this.sp_earning = sp_earning;
        this.admin_earning = admin_earning;
        this.date = date;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getSp_id() {
        return sp_id;
    }

    public void setSp_id(int sp_id) {
        this.sp_id = sp_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getRequest_service_id() {
        return request_service_id;
    }

    public void setRequest_service_id(int request_service_id) {
        this.request_service_id = request_service_id;
    }

    public double getTotal_amout() {
        return total_amout;
    }

    public void setTotal_amout(double total_amout) {
        this.total_amout = total_amout;
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
