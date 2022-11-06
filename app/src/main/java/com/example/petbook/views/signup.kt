package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
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
        binding.progBar.visibility = View.INVISIBLE
        binding.register.setOnClickListener {
            if (validate()) {
                val apiInterface = ApiInterface.create()

                val email = binding.emailInput.text.toString()

                val fullname = binding.NameInput.text.toString()
                val password = binding.passwordInput.text.toString()
                val cpassword = binding.CpasswordInput.text.toString()
          val res=SignupViewModel.userSignup(fullname,email,password)


                SignupViewModel.SignuResult.observe(this) {
                   toast(it)
                    println(it)
                }

            }
        }
    }

    fun validate(): Boolean {
        val email = binding.emailInput.text.toString()

        val fullname = binding.NameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val cpassword = binding.CpasswordInput.text.toString()
        var er1:Boolean =false
        var er2:Boolean =false
        var er3:Boolean =false
        if (fullname.isEmpty()) {
            binding.outlinedUsername.error = "Must not be empty!"

            er1 =false
        }
        else {
            binding.outlinedUsername.error = null
            er1 =true
        }
        if (email.isEmpty()) {
            binding.outlinedEmail.error = "Must not be empty!"
            er2 =false
        } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
            binding.outlinedEmail.error = "Check your email!"
            er2 =false
        } else {
            binding.outlinedEmail.error = null
            er2 =true
        }

        if (password.isEmpty()) {
            binding.outlinedPassword.error = "Must not be empty!"
            er3 =false

        } else if (password != cpassword) {
            binding.outlinedPassword.error = "Password is not matching"
            binding.outlinedCpassword.error = "Password is not matching"
            er3 =false
        } else {
            binding.outlinedPassword.error = null
            binding.outlinedCpassword.error = null
            er3 =true
        }

if(er1&&er2&&er3)
        return true
        else return false
    }

    fun toast(res:Int){

if(res==4){

    binding.progBar.visibility = View.VISIBLE

    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}
        if(res==1){
            binding.progBar.visibility = View.INVISIBLE
            window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            Toast.makeText(this@signup, "Account Created", Toast.LENGTH_SHORT).show()

        }
        if(res==2){
            Toast.makeText(this@signup, "acc", Toast.LENGTH_SHORT).show()
            binding.progBar.visibility = View.INVISIBLE
            window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        if(res==3){
            Toast.makeText(this@signup, "connection Error", Toast.LENGTH_SHORT).show()
            binding.progBar.visibility = View.INVISIBLE
            window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

    }
}


