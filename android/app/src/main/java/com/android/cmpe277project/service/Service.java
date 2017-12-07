package com.android.cmpe277project.service;

import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by aaditya on 10/19/17.
 */

public interface Service {

    @POST("users/signup")
    Observable<Response<ResponseBody>> signUp(@Body User user);

    @FormUrlEncoded
    @POST("users/verify")
    Observable<ResponseBody> verify(@Field("code") String code, @Field("email") String email );

    @FormUrlEncoded
    @POST("users/signin")
    Observable<ResponseBody> signIn(@Field("email") String email, @Field("password") String password);

    @POST("librarian/add")
    Observable<Response<ResponseBody>> addBook(@Body Book book );

    @POST("librarian/update")
    Observable<Response<ResponseBody>> updateBook(@Body Book book);

    @FormUrlEncoded
    @POST("librarian/search")
    Observable<Response<List<Book>>> searchBook(@Field("searchstring") String query, @Field("email") String email);

    @FormUrlEncoded
    @POST("librarian/delete")
    Observable<Response<ResponseBody>> deleteBook(@Field("bookId") String query, @Field("email") String email);

}
