package com.android.cmpe277project.service;

import com.android.cmpe277project.model.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by aaditya on 10/19/17.
 */

public interface Service {

    @POST("users/signup")
    Observable<Response> signUp(@Body User user);

    @POST("users/verify")
    Observable<Response<User>> verify(@Field("code") String code, @Field("email") String email );

    @POST("users/signin")
    Observable<ResponseBody> signIn(@Field("email") String email, @Field("password") String password);

}
