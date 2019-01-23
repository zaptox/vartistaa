
package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vksoni on 7/11/2018.
 */

public class DocUpload {


    @SerializedName("response")
    private String response;

    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("title")
    private String  title;


    @SerializedName("url")
    private String url;

    @SerializedName("date")
    private String date;


    @SerializedName("response_date")
    private String responseDate;


    @SerializedName("approved")
    private String approved;

    @SerializedName("status")
    private int status;


    public DocUpload() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DocUpload{" +
                "response='" + response + '\'' +
                ", id=" + id +
                ", userId='" + userId + '\'' +
                ", title=" + title +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", responseDate='" + responseDate + '\'' +
                ", approved='" + approved + '\'' +
                ", status=" + status +
                '}';
    }
}
