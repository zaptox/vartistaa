package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samina Arif on 4/18/2019.
 */

public class ProviderPhotos {

    private String title = null;
    private String url = null;
    @SerializedName("cnic_front_title")
    String cnic_front_title;
    @SerializedName("cnic_front_url")
    String cnic_front_url;
    @SerializedName("cnic_back_title")
    String cnic_back_title;
    @SerializedName("cnic_back_url")
    String cnic_back_url;
    @SerializedName("bank_doc_title")
    String bank_doc_title;
    @SerializedName("bank_doc_url")
    String bank_doc_url;
    int DOCTYPE = 0;
    // 0 for cnic front
    // 1 for cnic back
    // 2 for bank detail

    public ProviderPhotos(String cnic_front_title, String cnic_front_url, String cnic_back_title, String cnic_back_url, String bank_doc_title, String bank_doc_url) {
        this.cnic_front_title = cnic_front_title;
        this.cnic_front_url = cnic_front_url;
        this.cnic_back_title = cnic_back_title;
        this.cnic_back_url = cnic_back_url;
        this.bank_doc_title = bank_doc_title;
        this.bank_doc_url = bank_doc_url;
    }

    public int getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(int DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }

    public ProviderPhotos(String title, String url) {
                this.title = title;
                this.url = url;
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

    public String getCnic_front_title() {
        return cnic_front_title;
    }

    public void setCnic_front_title(String cnic_front_title) {
        this.cnic_front_title = cnic_front_title;
    }

    public String getCnic_front_url() {
        return cnic_front_url;
    }

    public void setCnic_front_url(String cnic_front_url) {
        this.cnic_front_url = cnic_front_url;
    }

    public String getCnic_back_title() {
        return cnic_back_title;
    }

    public void setCnic_back_title(String cnic_back_title) {
        this.cnic_back_title = cnic_back_title;
    }

    public String getCnic_back_url() {
        return cnic_back_url;
    }

    public void setCnic_back_url(String cnic_back_url) {
        this.cnic_back_url = cnic_back_url;
    }

    public String getBank_doc_title() {
        return bank_doc_title;
    }

    public void setBank_doc_title(String bank_doc_title) {
        this.bank_doc_title = bank_doc_title;
    }

    public String getBank_doc_url() {
        return bank_doc_url;
    }

    public void setBank_doc_url(String bank_doc_url) {
        this.bank_doc_url = bank_doc_url;
    }
}
