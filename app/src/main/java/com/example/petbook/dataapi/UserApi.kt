package com.example.petbook.dataapi

import com.example.petbook.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi{

@POST("/user/signin")
suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

@PUT("/user/edit")
suspend fun editUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
@POST("/user/Find")
suspend fun findUser(@Body searchRequest: SearchRequest): Response<MutableList<SearchResponse>>
    @POST("/user/follow")
    suspend fun Follow(@Body followRequest: FollowRequest): Response<FollowResponse>
    @POST("/user/unfollow")
    suspend fun UnFollow(@Body followRequest: FollowRequest): Response<FollowResponse>

    @POST("/user/getUser")
    suspend fun getUser(@Body userPostRequest: UserPostRequest): Response<UserPostResponse>
companion object {
    fun getApi(): UserApi? {
        return ApiClient.client?.create(UserApi::class.java)
    }

}
}