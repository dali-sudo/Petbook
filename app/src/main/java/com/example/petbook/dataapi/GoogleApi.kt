package com.example.petbook.dataapi

import com.example.petbook.model.PlacesApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleApi {

    @GET
    suspend fun getNearByPlaces(@Url url: String): Response<PlacesApiResponse>
    companion object {

        var BASE_URL = "https://geocode-api.arcgis.com/"

        fun create(): GoogleApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GoogleApi::class.java)
        }
    }
}