package com.vartista.www.vartista.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllNotificationBean implements Serializable {

    @SerializedName("response")
    @Expose
    private String Response;
    @SerializedName("Notification_id")
    @Expose
    private int Notification_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sender_id")
    @Expose
    private int sender_id;
    @SerializedName("receiver_id")
    @Expose
    private int receiver_id;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("created_at")
    @Expose
    private String created_at;

    public AllNotificationBean(int notification_id, String title, String message, int sender_id, int receiver_id, int status, String created_at) {
        Notification_id = notification_id;
        this.title = title;
        this.message = message;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.status = status;
        this.created_at = created_at;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public int getNotification_id() {
        return Notification_id;
    }

    public void setNotification_id(int notification_id) {
        Notification_id = notification_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
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
}
