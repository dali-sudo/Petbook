package com.example.petbook.repository

import com.example.petbook.dataapi.NotificationApi
import com.example.petbook.dataapi.petApi
import com.example.petbook.model.NotificationRequest
import com.example.petbook.model.NotificationResponse
import com.example.petbook.model.PetRequest
import com.example.petbook.model.PetResponse
import retrofit2.Response

class NotificationRepository {

    suspend fun getAll(notificationRequest: NotificationRequest): Response<MutableList<NotificationResponse>>? {
        return NotificationApi.getApi()?.getAll(notificationRequest = notificationRequest)
    }


}