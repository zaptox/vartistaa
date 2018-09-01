package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 2018-08-05.
 */

public class Services {
    @SerializedName("response")
    @Expose
    String response;


    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("updated_at")
    @Expose
    String updated_at;

    @SerializedName("category_id")
    @Expose
    int category_id;

    public Services(int id, String title, String description, int status, String created_at, String updated_at, int category_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getResponse() {
        return response;
    }
}
