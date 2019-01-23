package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocUploadList {

    @SerializedName("Urgent")
    private ArrayList<DocUpload> DocUploadList;

    public ArrayList<DocUpload> getDocUploadList() {
        return DocUploadList;
    }

    public void setDocUploadList(ArrayList<DocUpload> DocUploadList) {
        this.DocUploadList = DocUploadList;
    }

    @Override
    public String toString() {
        return "DocUploadList{" +
                "DocUploadList=" + DocUploadList +
                '}';
    }
}
