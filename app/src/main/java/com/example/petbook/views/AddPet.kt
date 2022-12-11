package com.example.petbook.views

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import com.example.petbook.databinding.ActivityAddPetBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.util.toast
import com.example.petbook.viewModel.petProfilesviewModel
import java.io.ByteArrayOutputStream

class AddPet : AppCompatActivity() {
    private lateinit var binding: ActivityAddPetBinding
    var imageUri: Uri? = null
    val pickImage = 100
    private val viewModel by viewModels<petProfilesviewModel>()
    private var encodedImage:String = ""
    private lateinit var name: String
    private lateinit var type:String
    private lateinit var race : String
    private lateinit var age : String
    private lateinit var gender : String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.profilePicHolder.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }

        viewModel.addpetResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    toast("loading!!!!!")
                }

                is BaseResponse.Success -> {
                    toast("Successss!!!!!")
                    toast(it.data.toString())
                }

                is BaseResponse.Error -> {
                    toast("erooooorr!!!")
                   toast(it.msg.toString())
                }
                else -> {
                   toast("no state done!!!")
                }
            }
        }

        binding.addPetButton.setOnClickListener() {

            name=binding.nameTextField.text.toString()
            type=binding.typeTextField.text.toString()
            race=binding.raceTextField.text.toString()
            age=binding.ageTextField.text.toString()
            if ( binding.MaleCheckbox.isChecked) {
                gender= "Male"
            }  else if ( binding.FemaleCheckbox.isChecked) {
                gender= "Female"
            }



            viewModel.AddPet(name,type,gender,race,age,encodedImage,SessionManager.getString(this,"id")!!)


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
            //SessionManager.saveString(this,"profilePic",encodedImage)
        }
    }


}