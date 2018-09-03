package com.vartista.www.vartista.restcalls;



import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.Service;
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

    @GET("get_service_by_id.php")
    Call<Services> getServiceById(@Query("id") int id);

    @GET("update_user.php")
    Call<User> updateUser(@Query("name") String name, @Query("email") String email, @Query("password") String password,@Query("id") int id);


    @GET("createservices.php")
    Call<Service> createService(@Query("service_title") String service_title, @Query("userid") int user_id,
                                @Query("service_description") String service_description,
                                @Query("status") int status, @Query("price") double price
                                , @Query("category_id") int category_id, @Query("created_at")
                                        String created_at, @Query("updated_at") String updated_at);




    @GET("create_request.php")
    Call<CreateRequest> createRequest(@Query("user_customer_id") int user_customer_id, @Query("user_service_id") int user_service_id,
                                      @Query("service_id") int service_id,
                                      @Query("date") String date, @Query("time") String  time
            , @Query("location") String location, @Query("request_status") int request_status, @Query("service_cat_id") int service_cat_id);



}
