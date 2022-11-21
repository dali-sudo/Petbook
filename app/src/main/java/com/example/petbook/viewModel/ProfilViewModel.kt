package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*
import com.example.petbook.repository.UserRepository
import kotlinx.coroutines.launch

class ProfilViewModel(application: Application) : AndroidViewModel(application){
    val userRepo = UserRepository()
    val followResult: MutableLiveData<BaseResponse<FollowResponse>> = MutableLiveData()
    val unfollowResult: MutableLiveData<BaseResponse<FollowResponse>> = MutableLiveData()
    val userResult: MutableLiveData<BaseResponse<UserPostResponse>> = MutableLiveData()


    fun Follow(myid: String, followedid: String) {

        followResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val followRequest = FollowRequest(
                    id = myid,
                    followed= followedid
                )
                val response = userRepo.FollowUser(followRequest)
                if (response?.code() == 200) {
                    followResult.value = BaseResponse.Success(response.body())
                } else {
                    followResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                followResult.value = BaseResponse.Error(ex.message )
            }
        }
        //ex.message
    }


    fun UnFollow(myid: String, followedid: String) {

        unfollowResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val followRequest = FollowRequest(
                    id = myid,
                    followed= followedid
                )
                val response = userRepo.UnFollowUser(followRequest)
                if (response?.code() == 200) {
                    unfollowResult.value= BaseResponse.Success(response.body())
                } else {
                    unfollowResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                unfollowResult.value = BaseResponse.Error(ex.message )
            }
        }
        //ex.message
    }
    fun GetUser(id: String) {

        userResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val userPostRequest= UserPostRequest(
                    id = id,

                )
                val response = userRepo.getUser(userPostRequest)
                if (response?.code() == 200) {
                    userResult.value= BaseResponse.Success(response.body())
                } else {
                    userResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                userResult.value = BaseResponse.Error(ex.message )
            }
        }
        //ex.message
    }
}