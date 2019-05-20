package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yusha Arif on 4/18/2019.
 */

public class ProviderBonusBean {

    @SerializedName("type")
    String bonusType;
    @SerializedName("amount")
    double amount;
    @SerializedName("created_at")
    String createdAt;

    public ProviderBonusBean(String bonusType, double amount, String createdAt) {
        this.bonusType = bonusType;
        this.amount = amount;
        this.createdAt = createdAt;
    }


    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
