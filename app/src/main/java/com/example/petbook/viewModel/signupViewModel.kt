package com.example.petbook.viewModel


import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.petbook.dataapi.ApiInterface
import com.example.petbook.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class signupViewModel : ViewModel() {


    fun SignUp(name:String,mail:String,passwd:String):Boolean{
        val apiInterface = ApiInterface.create()

var test:Boolean=false

        apiInterface.Register(name, mail,passwd).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val  user = response.body()!!
                if (user != null){
                    test=true
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
test=false

            }


          })
return test
    }
}