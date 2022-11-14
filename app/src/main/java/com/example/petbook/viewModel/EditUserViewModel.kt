package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.LoginRequest
import com.example.petbook.model.LoginResponse
import com.example.petbook.repository.UserRepository
import kotlinx.coroutines.launch

class EditUserViewModel (application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val EditResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun EditUser(token:String, username :String, email: String, pwd: String,img:String) {

        EditResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val loginRequest = LoginRequest(
                    username = username,
                    password = pwd,
                    email = email,
                    token = token,
                    profilePic = img

                )
                val response = userRepo.EditUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    EditResult.value = BaseResponse.Success(response.body())
                } else {
                    EditResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                EditResult.value = BaseResponse.Error(ex.message )
            }
        }
        //ex.message
    }

}