package com.example.petbook.repository

import com.example.petbook.dataapi.ChatApi
import com.example.petbook.dataapi.petApi
import com.example.petbook.model.*
import retrofit2.Response

class ChatRepository {
    suspend fun get(chatRoomRequest: ChatRoomRequest): Response<ChatRoomResponse>? {
        return ChatApi.getApi()?.get(chatRoomRequest =chatRoomRequest)
    }
    suspend fun getContacts(chatRoomRequest: ChatRoomRequest): Response<MutableList<ChatContactsResponse>>? {
        return ChatApi.getApi()?.getContacts(chatRoomRequest =chatRoomRequest)
    }
    suspend fun sendMessage(messageRequest: MessageRequest): Response<ChatRoomResponse>? {
        return ChatApi.getApi()?.sendmessage(messageRequest=messageRequest)
    }
    suspend fun findorcreate(getChatRequest: GetChatRequest): Response<GetChatResponse>? {
        return ChatApi.getApi()?.findorcreate(getChatRequest =getChatRequest)
    }
}
