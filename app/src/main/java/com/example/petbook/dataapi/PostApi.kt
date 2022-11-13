package com.example.petbook.dataapi


import com.example.petbook.model.LikeRequest
import com.example.petbook.model.LoginRequest
import com.example.petbook.model.PostRequest
import com.example.petbook.model.PostResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostApi {


    @POST("/post/getAll")
    suspend fun getAll(): Response<MutableList<PostResponse>>
    @POST("/post/addPost")
    suspend fun addPost(@Body postRequest: PostRequest): Response<PostResponse>
    @POST("/post/like")
    suspend fun likePost(@Body likeRequest: LikeRequest): Response<PostResponse>
    @POST("/post/unlike")
    suspend fun unlikePost(@Body likeRequest: LikeRequest): Response<PostResponse>
    companion object {
        fun getApi(): PostApi? {
            return ApiClient.client?.create(PostApi::class.java)
        }

    }



}