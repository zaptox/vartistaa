package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dell on 2018-11-01.
 */

public class UserAddressBean implements Serializable{


    @SerializedName("response")
    @Expose
    String response;


    @SerializedName("id")
    @Expose
    int  id;

    @SerializedName("work_address")
    @Expose
    String work_address;

    @SerializedName("permanent_address")
    @Expose
    String permanent_address;

    @SerializedName("city")
    @Expose
    String city;


    @SerializedName("province")
    @Expose
    String province;

    @SerializedName("zipcode")
    @Expose
    String zipcode;


    @SerializedName("country")
    @Expose
    String country;

    @SerializedName("user_id")
    @Expose
    int user_id;

    public UserAddressBean(int id, String work_address, String permanent_address, String city, String province, String zipcode, String country, int user_id) {
        this.id = id;
        this.work_address = work_address;
        this.permanent_address = permanent_address;
        this.city = city;
        this.province = province;
        this.zipcode = zipcode;
        this.country = country;
        this.user_id = user_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
