package com.android.cmpe277project.service;


import com.android.cmpe277project.Config;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aaditya on 10/20/17.
 */

public class ApiModule {

    private static ApiModule apiModule;

    private ApiModule(){
        //Prevent form the reflection api.
        if (apiModule != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static ApiModule getInstance(){
        if (apiModule == null){ //if there is no instance available... create new one
            apiModule = new ApiModule();
        }

        return apiModule;
    }

    public Service getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL_PRODUCTION)
                .client(provideOkHttpClient(provideInterceptors()))
                .addConverterFactory(provideGsonConverterFactory(provideGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(Service.class);
    }


    public Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls()
                .create();
    }

    public OkHttpClient provideOkHttpClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

//        builder.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                HttpUrl url = request.url().newBuilder().addQueryParameter("appid",Config.KEY_).build();
//                request = request.newBuilder().url(url).build();
//                return chain.proceed(request);
//            }
//        });
        return builder.build();
    }

    public List<Interceptor> provideInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        // add header interceptor
        interceptors.add(getHeaderInterceptor());

        // add logging interceptor
        if (Config.DEBUG) {
            interceptors.add(getLoggingInterceptor());
        }

        return interceptors;
    }

    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                Map<String, String> headers = getHeadersAfterAnnotatedSkip(chain.request().headers());
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    builder.addHeader(header.getKey(), header.getValue());
                }

                return chain.proceed(builder.build());
            }
        };
    }

    private Map<String, String> getHeadersAfterAnnotatedSkip(Headers annotatedHeaders) {
        Map<String, String> configHeaders = new HashMap<>(Config.API_HEADERS);

        for (String headerName : annotatedHeaders.names()) {
            configHeaders.remove(headerName);
        }

        return configHeaders;
    }

    private Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

}