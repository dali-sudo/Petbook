package com.example.petbook.dataapi

import com.example.petbook.model.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.Body

import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {


        @POST("user/signup")
        fun Register(@Body params: RequestBody): Call<User>



            var BASE_URL = "http://192.168.1.23:9090/"


            fun create(): ApiInterface {

                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Dali_BASE_URL)
                    .build()

                return retrofit.create(ApiInterface::class.java)
            }
        }
}
