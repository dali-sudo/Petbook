package com.example.petbook.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import com.example.petbook.MainActivity
import com.example.petbook.databinding.ActivityEditProfileBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.LoginResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.util.toast
import com.example.petbook.viewModel.EditUserViewModel
import java.io.ByteArrayOutputStream

class EditProfile : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var email: String
    private lateinit var password:String
    private lateinit var username : String
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var encodedImage:String = "";


    private val viewModel by viewModels<EditUserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        binding.loadingAnimation.isVisible=false
        val view = binding.root
        setContentView(view)
        if (SessionManager.getString(this,"profilePic") !=null )
        {
            val imageBytes = Base64.decode(SessionManager.getString(this,"profilePic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.profilePicHolder.setImageBitmap(decodedImage)

        }


        binding.fullnameTxtView.setText(SessionManager.getString(this,"username"))
        binding.usernameTextField.setText(SessionManager.getString(this,"username"), TextView.BufferType.EDITABLE)
        binding.emailTextField.setText(SessionManager.getString(this,"email"), TextView.BufferType.EDITABLE)

        binding.profilePicHolder.setOnClickListener() {

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }

        viewModel.EditResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processEdit(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                    stopLoading()
                }
                else -> {
                    stopLoading()
                }
            }
        }
        binding.profileBack.setOnClickListener() {

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }

        binding.updateUserButton.setOnClickListener ()
        {
            doUpdate()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.profilePicHolder.setImageURI(imageUri)
            val baos = ByteArrayOutputStream()
            val bitmap = binding.profilePicHolder.drawable.toBitmap()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
             SessionManager.saveString(this,"profilePic",encodedImage)
        }
    }

    fun doUpdate() {

        email=binding.emailTextField.text.toString()
        password=binding.epasswordTextField.text.toString()
        username=binding.usernameTextField.text.toString()

        if (validate())
        {
            viewModel.EditUser(username=username,email = email, pwd = password,token = SessionManager.getToken(this)!!, img = SessionManager.getString(this,"profilePic")!!)
            SessionManager.saveString(this, "username" , username)
            SessionManager.saveString(this,"email", email)
            navigateToProfile()
        }

    }
    private fun navigateToProfile() {


        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)

    }
    fun showLoading() {
        binding.loadingAnimation.isVisible=true

    }
    fun stopLoading() {
        binding.loadingAnimation.isVisible=false
    }
    // save  user/token in shared pref
    fun processEdit(data: LoginResponse?) {
      //  toast("Success:" + data?.user)
        if (data != null) {
            SessionManager.saveString(this, "username" , data.user.username)
            SessionManager.saveString(this,"email", data.user.email)
           SessionManager.saveString(this,"profilePic",data.user.img!!)
        }
        // if user gotback we save its token for authentification
    }


    fun processError(msg: String?) {
        toast("Error:" + msg)
    }
    fun validate():Boolean {

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

        if (username.isEmpty()){
            binding.outlinedUsername.error = "Must not be empty!"
            return false
        }

        else {
            binding.outlinedUsername.error = null
        }


        return true



    }

}