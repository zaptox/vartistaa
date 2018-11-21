package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khan on 11/8/2018.
 */

public class Doument_Upload_Nil {

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("contactno")
    private String contactno;

    @SerializedName("response")
    private String Response;

    public Doument_Upload_Nil(String name, String password, String contactno) {
        this.name = name;
        this.password = password;
        this.contactno = contactno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
