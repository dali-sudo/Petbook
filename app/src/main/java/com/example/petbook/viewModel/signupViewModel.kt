package com.example.petbook.viewModel


import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.petbook.dataapi.ApiInterface
import com.example.petbook.model.User
import com.example.petbook.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class signupViewModel : ViewModel() {


    fun SignUp(name: String, mail: String, passwd: String): Int {
val SignupResponse =UserRepository().userSignup(name,mail,passwd)
        return SignupResponse
    }
}
