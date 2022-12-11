package com.example.petbook.dataapi

import com.example.petbook.model.constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    var mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .retryOnConnectionFailure(true)
        .build()

    var mRetrofit: Retrofit? = null


    val client: Retrofit?
        get() {
            if(mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(constant.Dali_BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build()
            }
            return mRetrofit
        }
}