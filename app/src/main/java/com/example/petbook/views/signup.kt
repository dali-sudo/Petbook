package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.WindowManager
import android.widget.Toast
import com.example.petbook.dataapi.ApiInterface


import com.example.petbook.databinding.ActivitySignupBinding
import com.example.petbook.model.User
import com.example.petbook.viewModel.signupViewModel
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root


        setContentView(view)
        val SignupViewModel = signupViewModel()

        binding.register.setOnClickListener {
            if (validate()) {
                val apiInterface = ApiInterface.create()

                val email = binding.emailInput.text.toString()

                val fullname = binding.NameInput.text.toString()
                val password = binding.passwordInput.text.toString()
                val cpassword = binding.CpasswordInput.text.toString()
          val res=SignupViewModel.SignUp(fullname,email,password)
          if(res==1){
              Toast.makeText(this@signup, "Account Created", Toast.LENGTH_SHORT).show()
          }
                if(res==2){
                    Toast.makeText(this@signup, "acc", Toast.LENGTH_SHORT).show()
                }
                if(res==3){
                    Toast.makeText(this@signup, "connection Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun validate(): Boolean {
        val email = binding.emailInput.text.toString()

        val fullname = binding.NameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val cpassword = binding.CpasswordInput.text.toString()
        if (fullname.isEmpty()) {
            binding.outlinedUsername.error = "Must not be empty!"

            return false
        }
        if (email.isEmpty()) {
            binding.outlinedEmail.error = "Must not be empty!"
            return false
        } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
            binding.outlinedEmail.error = "Check your email!"
            return false
        } else {
            binding.outlinedEmail.error = null
        }

        if (password.isEmpty()) {
            binding.outlinedPassword.error = "Must not be empty!"

            return false
        } else if (password != cpassword) {
            binding.outlinedPassword.error = "Password is not matching"
            binding.outlinedCpassword.error = "Password is not matching"
            return false
        } else {
            binding.outlinedPassword.error = null
        }


        return true
    }
}


