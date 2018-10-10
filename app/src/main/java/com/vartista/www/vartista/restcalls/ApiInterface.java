package com.vartista.www.vartista.restcalls;



import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.Services;
import com.vartista.www.vartista.beans.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vksoni on 7/11/2018.
 */

public interface ApiInterface {

    @GET("register.php")
 Call<User> performRegistration(@Query("name") String name, @Query("email") String email,

                                @Query("password") String password, @Query("image") String image,

                                @Query("status") String status, @Query("contact") String contact,


                                @Query("created_at") String created_at, @Query("updated_at") String updated_at

                                );

    @GET("login.php")
    Call<User> performUserLogin(@Query("email") String email, @Query("password") String password);
//    Call<User> performUserLogin();



    @GET("get_user_by_id.php")
    Call<User> getUserById(@Query("id") int id);

    @GET("get_latest_user_id")
    Call<User>  getUserIdByEmail(@Query("email") String email);


    @GET("update_user.php")
    Call<User> updateUser(@Query("name") String name, @Query("email") String email, @Query("password") String password,@Query("id") int id);

    @GET("create_request.php")
    Call<CreateRequest> createRequest(@Query("user_customer_id") int user_customer_id, @Query("service_provider_id") int service_provider_id,
                                      @Query("service_id") int service_id,
                                      @Query("date") String date, @Query("time") String  time
            , @Query("location") String location,@Query("city") String city, @Query("request_status") int request_status, @Query("service_cat_id") int service_cat_id);


    @GET("request_serviceupdate.php")
       Call<ServiceRequets> updateOnClickRequests(@Query("requestservice_status") int requestservice_status,@Query("requestservice_id") int requestservice_id);
}
