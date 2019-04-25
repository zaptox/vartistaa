package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yousha Arif on 4/18/2019.
 */

public class ProviderDocuments {

    public ProviderDocuments(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @SerializedName("title")
    String fileName;
    @SerializedName("url")
    String filePath;
}
