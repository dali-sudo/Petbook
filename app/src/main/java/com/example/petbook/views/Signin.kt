package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.petbook.R
import com.example.petbook.databinding.ActivitySigninBinding

import com.example.petbook.model.BaseResponse
import com.example.petbook.model.LoginResponse
import com.example.petbook.util.toast
import com.example.petbook.viewModel.loginListener
import com.example.petbook.viewModel.SigninViewModel

class signin : AppCompatActivity(){
lateinit var email:String
lateinit var password: String

    private lateinit var binding: ActivitySigninBinding
    private val viewModel by viewModels<SigninViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    binding = ActivitySigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.loginButton.setOnClickListener()
        {
            doLogin()
        }


    }
    fun doLogin() {
      email=binding.emailInput.text.toString()
         password=binding.passwordInput.text.toString()

        if (validate())
        {
            viewModel.loginUser(email = email, pwd = password)

        }



    }
    fun showLoading() {
        toast("loading")
    }
    fun stopLoading() {
       toast("stopeed loading")
    }

    fun processLogin(data: LoginResponse?) {
        toast("Success:" + data?.user)

    }
    fun processError(msg: String?) {
      toast("Error:" + msg)
    }
    fun  validate():Boolean{


        if (email.isEmpty()){
            binding.outlinedEmail.error = "Must not be empty!"
            return false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.outlinedEmail.error = "Check your email!"
            return false
        }
        else {
            binding.outlinedEmail.error = null
        }

        if (password.isEmpty()){
            binding.outlinedPassword.error = "Must not be empty!"
            return false
        }

        else {
            binding.outlinedPassword.error = null
        }


        return true
    }

}



