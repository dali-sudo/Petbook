package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*
import com.example.petbook.repository.PostRepository
import com.example.petbook.repository.UserRepository
import kotlinx.coroutines.launch

class SearchUsersViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val searchResult: MutableLiveData<BaseResponse<PostResponse>> = MutableLiveData()
    val list:MutableLiveData<MutableList<SearchResponse>> = MutableLiveData()
    fun FindUsers(name:String) {

        searchResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val searchRequest = SearchRequest(
                      username = name
                )
                val response =  userRepo.FindUsers(searchRequest)
                if (response?.code() == 200) {
                    list.value= response.body()
                } else {
                    searchResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                searchResult.value = BaseResponse.Error(ex.message )
                println(BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }
}