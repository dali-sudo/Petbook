package com.example.petbook.viewModel

import android.app.Application
import android.content.Context
import android.util.Patterns
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petbook.dataapi.ApiInterface
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.LoginRequest
import com.example.petbook.model.LoginResponse
import com.example.petbook.repository.UserRepository
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.petbook.util.toast
import kotlinx.coroutines.launch


class SigninViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    // manage loading by setting the loginresult.value each case and sending it via livedata, when req/res set to loading, when code good get body, when not get error msg
    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())

                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message )
            }
        }
        //ex.message
    }

}