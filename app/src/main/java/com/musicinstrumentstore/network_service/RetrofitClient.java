package com.musicinstrumentstore.network_service;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.musicinstrumentstore.network_service.api_interface.AuthApi;
import com.musicinstrumentstore.network_service.api_interface.CatalogApi;
import com.musicinstrumentstore.network_service.api_interface.OrderApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static final String BASE_URL = "http://192.168.100.212:8080/";
    private Retrofit mRetrofit;
    private TokenManager tokenManager;

    private RetrofitClient(Context context){

        tokenManager = TokenManager.getInstance(context);

        HttpLoggingInterceptor loggInterceptor = new HttpLoggingInterceptor();
        loggInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
               Request originalRequest = chain.request();

               String authToken = tokenManager.getAccessToken();

               if(authToken == null || authToken.isEmpty()){
                return chain.proceed(originalRequest);
               }

                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + authToken)
                        .build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggInterceptor)
                .authenticator(new TokenAuthenticator(tokenManager))
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static RetrofitClient getInstance(Context context){
        if (mInstance == null){
            mInstance = new RetrofitClient(context.getApplicationContext());
        }
        return mInstance;
    }

    public AuthApi getAuthApi(){
        return mRetrofit.create(AuthApi.class);
    }

    public CatalogApi getCatalogApi(){
        return mRetrofit.create(CatalogApi.class);
    }

    public OrderApi getOrderApi(){
        return mRetrofit.create(OrderApi.class);
    }
}
