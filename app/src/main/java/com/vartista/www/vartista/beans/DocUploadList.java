package com.wasche.www.wasche.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UrgentCostList {

    @SerializedName("Urgent")
    private ArrayList<UrgentBean> urgentBeanList;

    public ArrayList<UrgentBean> getUrgentBeanList() {
        return urgentBeanList;
    }

    public void setUrgentBeanList(ArrayList<UrgentBean> urgentBeanList) {
        this.urgentBeanList = urgentBeanList;
    }

    @Override
    public String toString() {
        return "UrgentCostList{" +
                "urgentBeanList=" + urgentBeanList +
                '}';
    }
}
