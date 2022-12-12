package com.example.petbook.repository

import com.example.petbook.dataapi.PostApi
import com.example.petbook.dataapi.UserApi
import com.example.petbook.dataapi.petApi
import com.example.petbook.model.*

import retrofit2.Response

class PetRepository {


    suspend fun getPets(petRequest: PetRequest): Response<MutableList<PetResponse>>? {
        return petApi.getApi()?.getAll(petRequest = petRequest)
    }



    suspend fun addPet(petRequest : PetRequest): Response<PetResponse>? {
        return petApi.getApi()?.addPet(petRequest = petRequest)
    }

    suspend fun getImages(petRequest : PetRequest): Response<PetResponse>? {
        return petApi.getApi()?.getImgs(petRequest = petRequest)

    }suspend fun getSinglePet(petRequest : PetRequest): Response<PetResponse>? {
        return petApi.getApi()?.getSinglePet(petRequest = petRequest)
    }
}
