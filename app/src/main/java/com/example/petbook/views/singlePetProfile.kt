package com.example.petbook.views

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.petbook.databinding.ActivitySinglePetProfileBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.util.LoadingDialog
import com.example.petbook.util.toast
import com.example.petbook.viewModel.SigninViewModel
import com.example.petbook.viewModel.petProfilesviewModel


class singlePetProfile : AppCompatActivity() {
    private lateinit var binding : ActivitySinglePetProfileBinding
    private val viewModel by viewModels<petProfilesviewModel>()
    private lateinit var loadingDialog: LoadingDialog
    companion object {

        var id :String = ""
    }
    lateinit var petname : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
        binding = ActivitySinglePetProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
     //   setContentView(R.layout.activity_single_pet_profile)

        val extras = intent.extras


        if ((extras?.getString("petId")!=null) && (extras.getString("nameOfpet")!=null) )

        {
             id = extras.getString("petId")!!
             petname = extras.getString("nameOfpet")!!

        }
        else if( (extras?.getString("petName")!=null) && (extras.getString("idPet")!=null ))

        {
                println("here from the pager intent")
                id = extras.getString("idPet")!!
            petname=extras.getString("petName")!!
            }


        binding.fullnameTxtView.setText(petname)
        //  viewModel.getPetImages(id!!)

        viewModel.getImageResult.observe(this) {

            println(it)

        }

        viewModel.getSinglePet(id)
        viewModel.SinglePet.observe(this) {
            if ( !it.petOwner.equals(SessionManager.getString(this,"id"))) {

                binding.DeleteButton.visibility = GONE

            }
            val imageBytes = Base64.decode(it.petPic, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.petProfilePicHolder.setImageBitmap(decodedImage)

        }

        viewModel.getSinglePetResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    loadingDialog.startLoading()
                }

                is BaseResponse.Success -> {
                    loadingDialog.stopLoading()

                }

                is BaseResponse.Error -> {
                    loadingDialog.stopLoading()

                }
                else -> {
                    loadingDialog.stopLoading()

                }
            }
        }

       /* if (SessionManager.getString(this,"petPic") !=null)
        {
            val imageBytes = Base64.decode(SessionManager.getString(this,"petPic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.petProfilePicHolder.setImageBitmap(decodedImage)

        }
*/
        binding.DeleteButton.setOnClickListener {
            DeletePet(id)

        }

        binding.profileBack.setOnClickListener {
            finish()
        }
    }
    
    fun DeletePet(PetId: String) {

        viewModel.DeletePet(PetId)

        viewModel.Deleted.observe(this) {

            when (it) {
                is BaseResponse.Loading -> {
                    toast("Deleting !!")
                }

                is BaseResponse.Success -> {
                    toast("Deleted Successfully!")
                }
                else -> toast("Couldnt Delete")

            }

        }

finish()
    }


}