package com.vartista.www.vartista;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vksoni on 7/11/2018.
 */

public class Service {

    public Service(int service_id, String service_title, String service_description, int status, double price, int category_id, String created_at, String updated_at) {
        this.service_id = service_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.status = status;
        this.price = price;
        this.category_id = category_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Service(int service_id,int user_id, String category_name, String service_title, String service_description, int status, double price, int category_id, String created_at, String updated_at) {
        this.service_id = service_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.status = status;
        this.price = price;
        this.category_id = category_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_name=category_name;
        this.user_id=user_id;
    }

    @SerializedName("response")
    private String Response;


    @SerializedName("name")
    private String category_name;

    @SerializedName("service_id")
    private int service_id;

    @SerializedName("userid")
    private int user_id;

    @SerializedName("service_title")
    private String service_title;

    @SerializedName("service_description")
    private String service_description;

    @SerializedName("status")
    private int status;

    @SerializedName("price")
    private double price;

    @SerializedName("category_id")
    private int category_id;


    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;


    public String getResponse(){

    return Response;

}

    public String getService_title() {
        return service_title;
    }

    public String getService_description() {
        return service_description;
    }

    public int getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
    public int getService_id(){
        return service_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategory_name() {
        return category_name;
    }
}
