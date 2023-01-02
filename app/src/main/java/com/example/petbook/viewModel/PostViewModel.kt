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
    val newlist:MutableLiveData<MutableList<PostResponse>> = MutableLiveData()

    val userRepo = UserRepository()
    val followResult: MutableLiveData<BaseResponse<FollowResponse>> = MutableLiveData()
    val unfollowResult: MutableLiveData<BaseResponse<FollowResponse>> = MutableLiveData()
    val Deleted : MutableLiveData<BaseResponse<DeleteResponse>> = MutableLiveData()
    val getSinglePostResult: MutableLiveData<BaseResponse<PostResponse>> = MutableLiveData()
    fun AddPost(desc: String, List:List<String>,own:String, TaggedList: ArrayList<String>) {

        postResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val postRequest = PostRequest(

                    descreption = desc,

                    images = List,

                    owner = own,

                    tags = TaggedList
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
                val response = userRepo.FollowUser(followRequest)
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
    fun getPagination(limit:String,skip:String,id: String) {

        getpostResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
val Request=getPaginationRequest(
    id=id,
    limit=limit,
    skip=skip
)
                val response = postRepo.getPagination(Request)
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
    fun getnew(id1: String) {

        getpostResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val Request=getPaginationRequest(
                    id=id1,
                    limit="3",
                    skip="0"
                )
                val response = postRepo.getPagination(Request)
                if (response?.code() == 200) {
                    newlist.value= response.body()
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
    fun DeletePost(id: String) {

        viewModelScope.launch {
            try {

                val userpostRequest=UserPostRequest(

                    id=id

                )
                val response = postRepo.deletePost(userpostRequest = userpostRequest)
                if (response?.code() == 200) {

                    Deleted.value = BaseResponse.Success(response.body())
                } else {
                    Deleted.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                getSinglePostResult.value = BaseResponse.Error(ex.message)
            }
        }

    }


}






