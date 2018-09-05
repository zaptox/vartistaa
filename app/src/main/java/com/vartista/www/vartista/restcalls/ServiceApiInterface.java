package com.vartista.www.vartista.restcalls;

import com.vartista.www.vartista.beans.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApiInterface {

    @GET("CREATE_SERVICES/createservices.php")
    Call<Service> createService(@Query("service_title") String service_title, @Query("userid") int user_id,
                                @Query("service_description") String service_description,
                                @Query("location") String location,
                                @Query("status") int status,
                                @Query("price") double price,
                                @Query("category_id") int category_id,
                                @Query("created_at") String created_at,
                                @Query("updated_at") String updated_at);

    @GET("CREATE_SERVICES/update.services.php")
    Call<Service> updateService(@Query("service_title") String service_title,
                                @Query("service_description") String service_description,
                                @Query("location") String location,
                                @Query("category_id") int category_id,
                                @Query("price") double price,
                                @Query("updated_at") String updated_at,
                                @Query("service_id") int service_id);

    @GET("CREATE_SERVICES/get.service.by.id.php")
    Call<Service> getServiceById(@Query("id") int id);

    @GET("CREATE_SERVICES/delete.service.php")
    Call<Service> deleteService(@Query("service_id") int service_id);
}
