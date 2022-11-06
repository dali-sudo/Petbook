package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import com.example.petbook.R
import com.example.petbook.databinding.ActivitySignupBinding
import com.example.petbook.viewModel.signupViewModel

class signup : AppCompatActivity() {
    val email=binding.emailInput.text.toString()

    val fullname=binding.NameInput.text.toString()
    val password=binding.passwordInput.text.toString()
    val cpassword=binding.CpasswordInput.text.toString()
   private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val SignupViewModel = signupViewModel()

        binding.register.setOnClickListener {
            if (validate()) {
                if (SignupViewModel.SignUp(email, fullname, password))
                {
                }
            }
        }
    }
fun  validate():Boolean{

    if (fullname.isEmpty()){
        binding.outlinedUsername.error = "Must not be empty!"
        return false
    }
    if (email.isEmpty()){
        binding.outlinedEmail.error = "Must not be empty!"
        return false
    }
    else if(!EMAIL_ADDRESS.matcher(email).matches()) {
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
    else if(password!=cpassword ) {
        binding.outlinedPassword.error = "Password is not matching"
        binding.outlinedCpassword.error = "Password is not matching"
        return false
    }
    else {
        binding.outlinedPassword.error = null
    }


    return true
}
}
