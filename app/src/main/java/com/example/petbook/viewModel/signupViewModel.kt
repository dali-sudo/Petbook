package com.example.petbook.viewModel


import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petbook.dataapi.ApiInterface
import com.example.petbook.model.User
import com.example.petbook.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class signupViewModel : ViewModel() {

    val SignuResult= MutableLiveData<Int>()

    fun userSignup(fullname: String, email: String, password: String)  {
        SignuResult.postValue(4)
        val apiInterface = ApiInterface.create()


        apiInterface.Register(createJsonRequestBody(    "username" to fullname, "password" to password, "email" to email)).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    SignuResult.postValue(1)
                }else{
                    SignuResult.postValue(2)
                }



            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                SignuResult.postValue(3)
            }

        })

    }
    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(mapOf(*params)).toString())
}
