package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dell on 2018-08-02.
 */

    public class User implements Serializable{



    @SerializedName("response")
    @Expose
    String response;

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("image")
    @Expose
    String image;

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("contact")
    @Expose
    String contact;


    @SerializedName("created_at")
    @Expose
    String createdAt;

    @SerializedName("updated_at")
    @Expose
    String updatedAt;

    @SerializedName("gender")
    @Expose
    String gender;

    @SerializedName("sp_status")
    @Expose
    String sp_status;



    public User(int id, String name, String email, String password, String image, String status, String contact, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.status = status;
        this.contact = contact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User( int id, String name, String email, String password, String image, String status, String contact, String createdAt, String updatedAt, String gender, String sp_status) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.status = status;
        this.contact = contact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.gender = gender;
        this.sp_status = sp_status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSp_status() {
        return sp_status;
    }

    public void setSp_status(String sp_status) {
        this.sp_status = sp_status;
    }
}
