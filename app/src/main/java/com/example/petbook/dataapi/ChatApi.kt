package com.example.petbook.dataapi

import com.example.petbook.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("/chat/get")
    suspend fun get(@Body chatRoomRequest: ChatRoomRequest): Response<ChatRoomResponse>

    @POST("/chat/getmy")
    suspend fun getContacts(@Body chatRoomRequest: ChatRoomRequest): Response<MutableList<ChatContactsResponse>>

    @POST("/chat/send")
    suspend fun sendmessage(@Body messageRequest: MessageRequest): Response<ChatRoomResponse>

    companion object {
        fun getApi(): ChatApi? {
            return ApiClient.client?.create(ChatApi::class.java)
        }


    }
}