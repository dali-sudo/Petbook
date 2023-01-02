package com.example.petbook.dataapi


import com.example.petbook.model.*
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
    @POST("/post/getPostByUser")
    suspend fun getPostByUser(@Body userpostRequest: UserPostRequest): Response<MutableList<PostResponse>>
    @POST("/post/discover")
    suspend fun getDiscover(): Response<MutableList<DiscoverResponse>>
    @POST("/post/discoverPost")
    suspend fun getDiscoverPost(@Body userpostRequest: UserPostRequest): Response<PostResponse>
    @POST("/post/getPagination")
    suspend fun getPagination(@Body getPaginationRequest: getPaginationRequest): Response<MutableList<PostResponse>>
    @POST("/post/deletePost")
    suspend fun deletePost(@Body userpostRequest: UserPostRequest): Response<DeleteResponse>
    companion object {
        fun getApi(): PostApi? {
            return ApiClient.client?.create(PostApi::class.java)
        }


    }



}