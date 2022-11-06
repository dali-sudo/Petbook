package com.example.petbook.repository

import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.petbook.dataapi.ApiInterface
import com.example.petbook.model.User
import com.example.petbook.views.signup
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun userSignup(fullname: String, email: String, password: String): Int {
        val apiInterface = ApiInterface.create()

var result:Int =0

        apiInterface.Register(createJsonRequestBody(    "username" to fullname, "password" to password, "email" to email)).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                  result =1
                }else{
                 result= 2
                }



            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                result= 3
            }

        })
return result
    }



    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(mapOf(*params)).toString())


}