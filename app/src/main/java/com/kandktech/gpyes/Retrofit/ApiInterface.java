package com.kandktech.gpyes.Retrofit;

import com.kandktech.gpyes.Model.NotificationsModel;
import com.kandktech.gpyes.Model.ResponseModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("x-api-key: w@i76r_7l@h")
    @POST("companyUserLogin")
    Call<ResponseModel> userlogin(@Body JsonObject register);

    @Headers("x-api-key: w@i76r_7l@h")
    @POST("save_gps_user")
    Call<ResponseModel> sendgpsdata(@Body JsonObject sendgpsdata);

    @Headers("x-api-key: w@i76r_7l@h")
    @POST("userAttendance_gps_user")
    Call<ResponseModel> attendanceGps(@Body JsonObject sendgpsdata);

    @Headers("x-api-key: w@i76r_7l@h")
    @GET("getUserNotification")
    Call<NotificationsModel> getnotification(@Query("comp_id") int comp_id);

}
