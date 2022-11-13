package com.example.petbook.repository

import com.example.petbook.dataapi.PostApi
import com.example.petbook.model.LikeRequest
import com.example.petbook.model.LoginRequest
import com.example.petbook.model.PostRequest
import com.example.petbook.model.PostResponse
import retrofit2.Call
import retrofit2.Response

class PostRepository {
    suspend fun getPosts(): Response<MutableList<PostResponse>>? {
   val postResponse=PostApi.getApi()?.getAll()
       // println(postResponse!!.body().toString())
        return  postResponse
    }
    suspend fun addPost(postRequest : PostRequest): Response<PostResponse>? {
        return PostApi.getApi()?.addPost(postRequest = postRequest)
    }
    suspend fun addLike(likeRequest : LikeRequest): Response<PostResponse>? {
        return PostApi.getApi()?.likePost(likeRequest  = likeRequest )
    }
    suspend fun removeLike(likeRequest : LikeRequest): Response<PostResponse>? {
        return PostApi.getApi()?.unlikePost(likeRequest  = likeRequest )
    }
}