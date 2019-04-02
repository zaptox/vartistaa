package com.vartista.www.vartista.beans;

/**
 * Created by Dell on 2019-04-01.
 */

public class EarningsDuesBean  {

    int id;
    int sp_id;
    double amount_due;
    String created_date;
    String updated_date;
    int status;
    String response;

    public EarningsDuesBean(int id, int sp_id, double amount_due, String created_date, String updated_date, int status) {
        this.id = id;
        this.sp_id = sp_id;
        this.amount_due = amount_due;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSp_id() {
        return sp_id;
    }

    public void setSp_id(int sp_id) {
        this.sp_id = sp_id;
    }

    public double getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(double amount_due) {
        this.amount_due = amount_due;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
