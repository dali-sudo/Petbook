package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*
import com.example.petbook.repository.PostRepository
import com.example.petbook.repository.UserRepository
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class PostViewModel(application: Application) : AndroidViewModel(application) {


    val postRepo = PostRepository()
    val postResult: MutableLiveData<BaseResponse<PostResponse>> = MutableLiveData()
    val getpostResult: MutableLiveData<BaseResponse<PostResponse>> = MutableLiveData()
val list:MutableLiveData<MutableList<PostResponse>> = MutableLiveData()
    fun AddPost(desc: String, List:List<String>,own:String) {

        postResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val postRequest = PostRequest(

                    descreption = desc,

                    images = List,

                    owner = own
                )
                val response = postRepo.addPost(postRequest = postRequest)
                if (response?.code() == 200) {
                    postResult.value = BaseResponse.Success(response.body())
                } else {
                    postResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                postResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
        fun getPosts() {

            getpostResult.value = BaseResponse.Loading()
            viewModelScope.launch {
                try {

                    val response = postRepo.getPosts()
                    if (response?.code() == 200) {
                        list.value= response.body()
                    } else {
                        getpostResult.value = BaseResponse.Error(response?.message())
                    }

                } catch (ex: Exception) {
                    postResult.value = BaseResponse.Error(ex.message )
                    println(BaseResponse.Error(ex.message ))
                }
            }
        //ex.message
    }
    fun LikePost(id1: String,likesc:String,li:String) {

        postResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val likeRequest = LikeRequest(

                    id = id1,

                    likescount = likesc,

                    likes = li
                )
                val response = postRepo.addLike(likeRequest = likeRequest)
                if (response?.code() == 200) {
                    postResult.value = BaseResponse.Success(response.body())
                } else {
                    postResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                postResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
        fun UnlikePost(id1: String,likesc:String, li:String) {

            postResult.value = BaseResponse.Loading()
            viewModelScope.launch {
                try {

                    val likeRequest = LikeRequest(

                        id = id1,

                        likescount = likesc,

                        likes = li
                    )
                    val response = postRepo.removeLike(likeRequest = likeRequest)
                    if (response?.code() == 200) {
                        postResult.value = BaseResponse.Success(response.body())
                    } else {
                        postResult.value = BaseResponse.Error(response?.message())
                    }

                } catch (ex: Exception) {
                    postResult.value = BaseResponse.Error(ex.message)
                }
            }
    }


    fun getPostsByUser(id1:String) {

        getpostResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val postRequest = UserPostRequest(

                    id = id1,


                )

                val response = postRepo.getPostByUser(postRequest)
                if (response?.code() == 200) {
                    list.value= response.body()
                } else {
                    getpostResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                postResult.value = BaseResponse.Error(ex.message )
                println(BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }

}




