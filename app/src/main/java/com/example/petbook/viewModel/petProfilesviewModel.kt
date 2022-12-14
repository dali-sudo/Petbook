package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*

import com.example.petbook.repository.PetRepository
import com.google.gson.annotations.SerializedName

import kotlinx.coroutines.launch

class petProfilesviewModel(application: Application) : AndroidViewModel(application) {


    val petRepo = PetRepository()
    val getPetsResult: MutableLiveData<BaseResponse<PetResponse>> = MutableLiveData()
    val addpetResult: MutableLiveData<BaseResponse<PetResponse>> = MutableLiveData()
    val getImageResult: MutableLiveData<BaseResponse<PetResponse>> = MutableLiveData()
    val getSinglePetResult: MutableLiveData<BaseResponse<PetResponse>> = MutableLiveData()
    val Petslist:MutableLiveData<MutableList<PetResponse>> = MutableLiveData()
    val SinglePet : MutableLiveData<PetResponse> = MutableLiveData()
    val singlePetImages : MutableLiveData<PetResponse> = MutableLiveData()
    val Deleted : MutableLiveData<BaseResponse<DeleteResponse>> = MutableLiveData()

    // manage loading by setting the loginresult.value each case and sending it via livedata, when req/res set to loading, when code good get body, when not get error msg


    fun AddPet(name:String, type:String, gender:String, race:String, age:String, avatar:String ,own:String) {

        addpetResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val petRequest = PetRequest(


                petName=name,
                petType=type,
                race=race,
                petOwner=own,
                petPic=avatar,
                sexe= gender,
                petAge = age

                )
                val response = petRepo.addPet(petRequest = petRequest)
                if (response?.code() == 200) {
                    addpetResult.value = BaseResponse.Success(response.body())
                } else {
                    addpetResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                addpetResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getPetImages(id: String) {

        viewModelScope.launch {
            try {

                val petRequest = PetRequest(
                    petId=id
                )
                val response = petRepo.getImages(petRequest = petRequest)
                if (response?.code() == 200) {

                    singlePetImages.value = response.body()
                    getImageResult.value = BaseResponse.Success(response.body())
                } else {
                    getImageResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                getImageResult.value = BaseResponse.Error(ex.message)
            }
        }

    }



    fun getPets( owner: String) {

        getPetsResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val petRequest = PetRequest(

                    petOwner = owner

                )
                val response = petRepo.getPets(petRequest = petRequest)
                if (response?.code() == 200) {

                    Petslist.value = response.body()
                    getPetsResult.value = BaseResponse.Success()
                    println("thats what we got in our list from reponse ******************************")
                    println(Petslist)


                } else {
                    getPetsResult.value = BaseResponse.Error(response?.message())
                    println("Erroooorr")
                    println(BaseResponse.Error(response?.message()))
                }

            } catch (ex: Exception) {
                getPetsResult.value = BaseResponse.Error(ex.message )
                println("Exceptionn")
                println( BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }


    fun getSinglePet(id: String) {
        getSinglePetResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val petRequest = PetRequest(

                    petId=id

                )
                val response = petRepo.getSinglePet(petRequest = petRequest)
                if (response?.code() == 200) {
                    SinglePet.value= response.body()
                    getSinglePetResult.value = BaseResponse.Success(response.body())
                } else {
                    getSinglePetResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                getSinglePetResult.value = BaseResponse.Error(ex.message)
            }
        }

    }
    fun DeletePet(id: String) {

        viewModelScope.launch {
            try {

                val petRequest = PetRequest(

                    petId=id

                )
                val response = petRepo.DeletePet(petRequest = petRequest)
                if (response?.code() == 200) {

                    Deleted.value = BaseResponse.Success(response.body())
                } else {
                    Deleted.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                getSinglePetResult.value = BaseResponse.Error(ex.message)
            }
        }

    }




}