package com.vartista.www.vartista.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class GetServiceProviders implements Serializable,Parcelable{

    @SerializedName("response")
    private String Response;




    public GetServiceProviders(int service_id, int address_id, double latitude, double longitude, int user_id, String service_title, String service_description, double price, int category_id) {
        this.service_id = service_id;
        this.address_id = address_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.service_title = service_title;
        this.service_description = service_description;

        this.price = price;
        this.category_id = category_id;
    }

    protected GetServiceProviders(Parcel in) {
        Response = in.readString();
        category_name = in.readString();
        service_id = in.readInt();
        address_id = in.readInt();
        address = in.readString();
        city = in.readString();
        province = in.readString();
        zipcode = in.readString();
        country = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        user_id = in.readInt();
        service_title = in.readString();
        service_description = in.readString();
        status = in.readInt();
        price = in.readDouble();
        category_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<GetServiceProviders> CREATOR = new Creator<GetServiceProviders>() {
        @Override
        public GetServiceProviders createFromParcel(Parcel in) {
            return new GetServiceProviders(in);
        }

        @Override
        public GetServiceProviders[] newArray(int size) {
            return new GetServiceProviders[size];
        }
    };

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @SerializedName("name")
    private String category_name;

    @SerializedName("service_id")
    private int service_id;


    @SerializedName("id")
    private int address_id;


    @SerializedName("address")
    private String address;


    @SerializedName("city")
    private String city;


    @SerializedName("province")
    private String province;



    @SerializedName("zipcode")
    private String zipcode;



    @SerializedName("country")
    private String country;


    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("userid")
    private int user_id;

    @SerializedName("service_title")
    private String service_title;

    @SerializedName("service_description")
    private String service_description;

    @SerializedName("status")
    private int status;

    @SerializedName("price")
    private double price;

    @SerializedName("category_id")
    private int category_id;


    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Response);
        dest.writeString(category_name);
        dest.writeInt(service_id);
        dest.writeInt(address_id);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(zipcode);
        dest.writeString(country);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(user_id);
        dest.writeString(service_title);
        dest.writeString(service_description);
        dest.writeInt(status);
        dest.writeDouble(price);
        dest.writeInt(category_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
