package com.vartista.www.vartista.restcalls;



import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.beans.CreateRequest;
//import com.vartista.www.vartista.beans.DocUpload;
//import com.vartista.www.vartista.beans.DocUploadList;
import com.vartista.www.vartista.beans.Doument_Upload_Nil;
import com.vartista.www.vartista.beans.EarningsBean;
import com.vartista.www.vartista.beans.EarningsDuesBean;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.beans.forgotpassword;
import com.vartista.www.vartista.beans.UserAddressBean;
import com.vartista.www.vartista.modules.user.StartService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Vksoni on 7/11/2018.
 */

public interface ApiInterface {

    @GET("register.php")
 Call<User> performRegistration(@Query("name") String name, @Query("email") String email,

                                @Query("password") String password, @Query("image") String image,

                                @Query("status") String status, @Query("contact") String contact,

                                @Query("created_at") String created_at, @Query("updated_at") String updated_at,

                                @Query("gender") String gender

                                );

    @POST("login_post.php")
    @FormUrlEncoded
    Call<User> performUserLogin(@Field("email") String email, @Field("password") String password);
//    Call<User> performUserLogin();



    @GET("get_user_by_id.php")
    Call<User> getUserById(@Query("id") int id);

    @GET("get_latest_user_id")
    Call<User>  getUserIdByEmail(@Query("email") String email);


    @GET("update_user.php")
    Call<User> updateUser(@Query("name") String name, @Query("email") String email, @Query("password") String password,@Query("id") int id);

    @GET("Update_Settings.php")
    Call<User> updateUserSettings(@Query("id") int id,@Query("name") String name,
                                  @Query("email") String email, @Query("password") String password,
                                  @Query("image") String image,@Query("status") int status,
                                  @Query("contact") String contact,@Query("created_at") int created_at,
                                  @Query("updated_at") int updated_at);

    @GET("create_request.php")
    Call<CreateRequest> createRequest(@Query("user_customer_id") int user_customer_id, @Query("service_provider_id") int service_provider_id,
                                      @Query("service_id") int service_id,
                                      @Query("date") String date, @Query("time") String  time
            , @Query("location") String location,@Query("city") String city, @Query("request_status") int request_status, @Query("service_cat_id") int service_cat_id);

    @GET("request_serviceupdate.php")
       Call<ServiceRequets> updateOnClickRequests(@Query("requestservice_status") int requestservice_status,@Query("requestservice_id") int requestservice_id);

   @GET("Assign_Ratings.php")
   Call<CreateRequest> InsertRatings(@Query("id") int id,@Query("stars") double stars,@Query("user_id") int user_id,@Query("service_p_id") int service_p_id,@Query("service_id") int service_id,

                            @Query("user_remarks") String user_remarks,@Query("date") String date,@Query("time") String time);

    @GET("Assign_Ratings_To_User.php")
    Call<CreateRequest> InsertSpRatings(@Query("id") int id,@Query("stars") double stars,@Query("service_p_id") int service_p_id,@Query("user_id") int user_id,@Query("service_id") int service_id,

                                        @Query("sp_remarks") String user_remarks,@Query("date") String date,@Query("time") String time,@Query("requestservice_id")int requestservice_id);


    @GET("update_ratings.php")
    Call<CreateRequest> updateratings(@Query("id") int id,@Query("stars") double stars,@Query("user_remarks") String user_remarks,@Query("request_id") int request_id);



    @GET("update_sp_ratings.php")
    Call<CreateRequest> updateSpRatings(@Query("id") int id,@Query("stars") double stars,@Query("sp_remarks") String sp_remarks,@Query("request_id") int request_id);

    @GET("insert_user_address.php")
    Call<UserAddressBean> insertUserAddress(@Query("work_address") String work_address, @Query("permanent_address") String permanent_address,
                                            @Query("city") String city,
                                            @Query("province") String province, @Query("zipcode") String  zipcode
            , @Query("country") String country, @Query("user_id") int user_id);
    @GET("update_address.php")
    Call<UserAddressBean> UpdateUserAddress(@Query("work_address") String work_address, @Query("permanent_address") String permanent_address,
                                            @Query("city") String city,
                                            @Query("province") String province, @Query("zipcode") String  zipcode
            , @Query("country") String country, @Query("user_id") int user_id);

    @GET("forget_password.php")
    Call<forgotpassword> User_Verification_Email(@Query("email") String email);

    @GET("forget_password.php")
    Call<forgotpassword> User_Verification_Code(@Query("email") String code);

    @GET("GettinguserId.php")
    Call<Doument_Upload_Nil> document_upload_nil(@Query("name") String name,
                                                 @Query("password") String password,
                                                 @Query("contact") String contacno
                                                 );


    @GET("updateuserstatus.php")
    Call<User> UpdateUserStatus(@Query("user_id") int user_id);


    @GET("update_sp_status.php")
    Call<User> UpdateSpStatus(@Query("user_id") int user_id);

    @GET("updatebusystatus.php")
    Call<User> updatebusystatus(@Query("id") int id);


    @GET("Update_requestStatus.php")
    Call<CreateRequest> updaterequeststatus(@Query("requestservice_id") int request_id);

    //    @GET("fetch_doc_req.php")
//    Call<DocUploadList> getDocUploadListByUserId(@Query("user_id") int user_id);

    @GET("insert_allnotification.php")
    Call<AllNotificationBean> Insert_Notification(@Query("title")String title,@Query("message")String message ,@Query("sender_id") int sender_id,@Query("receiver_id") int receiver_id,@Query("status")int status,@Query("created_at")String created_at);

    @POST("earning_insert_post.php")
    @FormUrlEncoded
    Call<EarningsBean> Insert_Earnings(@Field("sp_id") int sp_id, @Field("user_id")int user_id, @Field("service_id") int service_id, @Field("request_service_id") int request_service_id, @Field("total_amount")double total_amount, @Field("admin_tax")double admin_tax, @Field("discount")double discount, @Field("user_bonus")double user_bonus, @Field("sp_earning")double sp_earning, @Field("admin_earning")double admin_earning, @Field("date")String date);

    @GET("update_dues.php")
    Call<EarningsDuesBean> UpdateDues(@Query("sp_id")int sp_id,@Query("amount_due")double amount_due);

}

