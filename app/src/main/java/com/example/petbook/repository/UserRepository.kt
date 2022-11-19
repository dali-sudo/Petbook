package com.example.petbook.repository


import com.example.petbook.dataapi.UserApi
import com.example.petbook.model.*
import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
    suspend fun EditUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return UserApi.getApi()?.editUser(loginRequest = loginRequest)
    }
    suspend fun FindUsers(searchRequest: SearchRequest): Response<MutableList<SearchResponse>>? {
        return UserApi.getApi()?.findUser(searchRequest = searchRequest )
    }
}
