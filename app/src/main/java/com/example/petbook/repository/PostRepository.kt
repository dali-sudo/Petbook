package com.example.petbook.repository

import com.example.petbook.dataapi.PostApi
import com.example.petbook.model.*
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
    suspend fun getPostByUser(userpostRequest: UserPostRequest): Response<MutableList<PostResponse>>? {
        return PostApi.getApi()?.getPostByUser(userpostRequest =userpostRequest)

    }
    suspend fun getDiscover(): Response<MutableList<DiscoverResponse>>? {
        return PostApi.getApi()?.getDiscover()
    }
    suspend fun getDiscoverPost(userpostRequest:UserPostRequest): Response<PostResponse>? {
        return PostApi.getApi()?.getDiscoverPost(userpostRequest=userpostRequest)
    }
    suspend fun getPagination(getPaginationRequest: getPaginationRequest): Response<MutableList<PostResponse>>? {
        return PostApi.getApi()?.getPagination(getPaginationRequest =getPaginationRequest)

    }
}