package com.example.petbook.dataapi

import com.example.petbook.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface petApi {

    @POST("/pet/getAll")
    suspend fun getAll(@Body petRequest: PetRequest): Response<MutableList<PetResponse>>
    @POST("/pet/addPet")
    suspend fun addPet(@Body petRequest: PetRequest): Response<PetResponse>
    @POST("/pet/getPetImages")
    suspend fun getImgs(@Body petRequest: PetRequest): Response<PetResponse>
    @POST("/pet/getSinglePet")
    suspend fun getSinglePet(@Body petRequest: PetRequest): Response<PetResponse>



    companion object {
        fun getApi(): petApi? {
            return ApiClient.client?.create(petApi::class.java)
        }

    }


}