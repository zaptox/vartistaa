package com.vartista.www.vartista.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vksoni on 7/11/2018.
 */

public class Category {


    @SerializedName("response")
    private String Response;


    @SerializedName("name")
    private String category_name;

    @SerializedName("id")
    private int cat_id;


    @SerializedName("image")
    private String image;

    public Category(String category_name, int cat_id) {
        this.category_name = category_name;
        this.cat_id = cat_id;
    }

    public Category(String category_name, int cat_id, String image) {
        this.category_name = category_name;
        this.cat_id = cat_id;
        this.image = image;
    }

    public Category() {
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getResponse(){

    return Response;

}

    public int getCat_id() {
        return cat_id;
    }

    public String getCategory_name() {
        return category_name;
    }
}
