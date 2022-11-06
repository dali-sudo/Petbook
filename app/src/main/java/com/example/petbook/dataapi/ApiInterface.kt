package com.example.petbook.dataapi

import com.example.petbook.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {


        @POST("signup")
        fun Register(@Query("name") name: String,@Query("email") email: String, @Query("password") password: String): Call<User>


        companion object {
            var Dali_BASE_URL = "http://192.168.56.1:27017/"
            var BASE_URL = "http://192.168.1.22:5000/"

            fun create(): ApiInterface {

                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Dali_BASE_URL)
                    .build()

                return retrofit.create(ApiInterface::class.java)
            }
        }
}
