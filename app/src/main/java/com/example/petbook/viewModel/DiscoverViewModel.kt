package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*
import com.example.petbook.repository.PostRepository
import kotlinx.coroutines.launch

class DiscoverViewModel (application: Application) : AndroidViewModel(application) {
    val Repo = PostRepository()
    val list: MutableLiveData<MutableList<DiscoverResponse>> = MutableLiveData()
    val getDiscoverResult: MutableLiveData<BaseResponse<DiscoverResponse>> = MutableLiveData()
    val DiscoverPost: MutableLiveData<BaseResponse<PostResponse>> = MutableLiveData()
    fun getDiscover() {

        getDiscoverResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val response = Repo.getDiscover()
                if (response?.code() == 200) {
                    getDiscoverResult.value = BaseResponse.Success()
                    list.value = response.body()
                } else {
                    getDiscoverResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                getDiscoverResult.value = BaseResponse.Error(ex.message)
                println(BaseResponse.Error(ex.message))
            }
        }
    }
        fun getDiscoverPost(id: String) {

            getDiscoverResult.value = BaseResponse.Loading()
            viewModelScope.launch {
                try {
                    val request = UserPostRequest(

                        id = id,


                        )
                    val response = Repo.getDiscoverPost(request)
                    if (response?.code() == 200) {
                        getDiscoverResult.value = BaseResponse.Success()
                        DiscoverPost.value = BaseResponse.Success(response.body())
                    } else {
                        getDiscoverResult.value = BaseResponse.Error(response?.message())
                    }

                } catch (ex: Exception) {
                    getDiscoverResult.value = BaseResponse.Error(ex.message)
                    println(BaseResponse.Error(ex.message))
                }
            }
        }


    }
