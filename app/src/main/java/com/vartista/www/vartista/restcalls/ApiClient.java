package com.vartista.www.vartista.restcalls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vksoni on 7/11/2018.
 */

public class ApiClient {
    public static final String BASE_URL="http://vartista.com/vartista_app/";
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient(){

        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;

    }

}
