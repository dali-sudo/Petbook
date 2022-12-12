package com.example.petbook.views

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.petbook.databinding.ActivitySinglePetProfileBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.SigninViewModel
import com.example.petbook.viewModel.petProfilesviewModel


class singlePetProfile : AppCompatActivity() {
    private lateinit var binding : ActivitySinglePetProfileBinding
    private val viewModel by viewModels<petProfilesviewModel>()
    companion object {

        var name :String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinglePetProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
     //   setContentView(R.layout.activity_single_pet_profile)

        val extras = intent.extras
        name = extras?.getString("nameOfpet")!!
        if ((extras.getString("nameOfpet")!=null) )
        {

            viewModel.getPetImages(extras.getString("nameOfpet")!!)
            viewModel.getImageResult.observe(this) {

                println(it)


            }

            viewModel.getSinglePet(extras.getString("nameOfpet")!!)

            viewModel.SinglePet.observe(this) {

                val imageBytes = Base64.decode(it.petPic, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.petProfilePicHolder.setImageBitmap(decodedImage)

            }

        }

        if (extras != null)
        {
           binding.fullnameTxtView.setText(extras.getString("petName"))
            if (SessionManager.getString(this,"petPic") !=null)
            {
                val imageBytes = Base64.decode(SessionManager.getString(this,"petPic"), Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.petProfilePicHolder.setImageBitmap(decodedImage)

            }


        }




        binding.profileBack.setOnClickListener {
            finish()
        }
    }


}