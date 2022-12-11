package com.example.petbook.dataapi

import com.example.petbook.model.NotificationRequest
import com.example.petbook.model.NotificationResponse
import com.example.petbook.model.PetRequest
import com.example.petbook.model.PetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationApi {



    @POST("/notification/get")
    suspend fun getAll(@Body notificationRequest: NotificationRequest): Response<MutableList<NotificationResponse>>
    companion object {
        fun getApi(): NotificationApi? {
            return ApiClient.client?.create(NotificationApi::class.java)
        }

    }

}