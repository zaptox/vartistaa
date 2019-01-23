package com.vartista.www.vartista.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class GetServiceProviders implements Serializable,Parcelable{

    @SerializedName("response")
    private String Response;




    public GetServiceProviders(int service_id, int address_id, double latitude, double longitude, int user_id, String service_title, String service_description, double price, int category_id, String sp_name) {
        this.service_id = service_id;
        this.address_id = address_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.price = price;
        this.category_id = category_id;
        this.sp_name=sp_name;

    }


    public GetServiceProviders(int service_id, int address_id, double latitude, double longitude, int user_id, String service_title, String service_description, double price, int category_id, String sp_name,int serv_count,String location) {
        this.service_id = service_id;
        this.address_id = address_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.price = price;
        this.category_id = category_id;
        this.sp_name=sp_name;
        this.serv_count=serv_count;
        this.location=location;
    }

    public GetServiceProviders(int service_id, int address_id, double latitude, double longitude, int user_id, String service_title, String service_description, double price, int category_id, String sp_name, double ratings) {
        this.service_id = service_id;
        this.address_id = address_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.price = price;
        this.category_id = category_id;
        this.sp_name=sp_name;
        this.ratings = ratings;
    }

    public GetServiceProviders(int service_id, int address_id, double latitude, double longitude, int user_id, String service_title, String service_description, double price, int category_id, String sp_name, double ratings, int user_status, String image) {
        this.service_id = service_id;
        this.address_id = address_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.price = price;
        this.category_id = category_id;
        this.sp_name=sp_name;
        this.ratings = ratings;
        this.user_status = user_status;
        this.image = image;
    }
    public GetServiceProviders(int service_id, int address_id, double latitude, double longitude, int user_id, String service_title, String service_description, double price, int category_id, String sp_name, double ratings, int user_status, String image,int busy_status) {
        this.service_id = service_id;
        this.address_id = address_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.service_title = service_title;
        this.service_description = service_description;
        this.price = price;
        this.category_id = category_id;
        this.sp_name=sp_name;
        this.ratings = ratings;
        this.user_status = user_status;
        this.image = image;
        this.busy_status=busy_status;
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
        sp_name=in.readString();
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

    public String getSp_name() {
        return sp_name;
    }

    public void setSp_name(String sp_name) {
        this.sp_name = sp_name;
    }

    public int getServ_count() {
        return serv_count;
    }

    public void setServ_count(int serv_count) {
        this.serv_count = serv_count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getBusy_status() {
        return busy_status;
    }

    public void setBusy_status(int busy_status) {
        this.busy_status = busy_status;
    }

    @SerializedName("name")
    private String category_name;


    @SerializedName("location")
    private String location;

    @SerializedName("busy_status")
    private int busy_status;


    @SerializedName("serv_count")
    private int serv_count;

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

    @SerializedName("sp_name")
    private String sp_name;

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

    @SerializedName("ratings")
    private double ratings;

    @SerializedName("user_status")
    private int user_status;

    @SerializedName("image")
    private String image;

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
        dest.writeString(sp_name);
    }
}
