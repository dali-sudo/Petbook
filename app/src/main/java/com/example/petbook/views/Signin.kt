package com.example.petbook.views

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.petbook.databinding.ActivitySigninBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.LoginResponse
import com.example.petbook.util.toast
import com.example.petbook.viewModel.SigninViewModel
import com.example.petbook.viewModel.signupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class signin : AppCompatActivity(){
lateinit var email:String
lateinit var password: String

    private lateinit var binding: ActivitySigninBinding
    private val viewModel by viewModels<SigninViewModel>()
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.goToSignUp.setOnClickListener(){

            val intent = Intent(this,signup::class.java)
            startActivity(intent)

        }
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken("196512096964-98hd93oa1f64t27nrttk5364j5h9h6gr.apps.googleusercontent.com").build()
        gsc = GoogleSignIn.getClient(this, gso!!)
        gsc?.signOut()
        binding.googleIcon.setOnClickListener()
        {
            println("google")
            signIn();

        }


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
    fun signIn() {
        val signInIntent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val SignupViewModel = signupViewModel()
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)

                val acct = GoogleSignIn.getLastSignedInAccount(this)
                val personName = acct?.displayName
                val personEmail = acct?.email
                val token =acct?.idToken
                println("token")
                println(token.toString())
             //   val res=SignupViewModel.userSignup(personName.toString(),personEmail.toString(),"googleacount")
                println(personName.toString())
                println(personEmail.toString())
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
                println("error")
                println(e)
            }
        }
        else println("error")
    }
}



