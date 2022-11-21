package com.example.petbook.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.petbook.databinding.ActivityPetsProfilesBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.PetResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.petProfilesviewModel


class PetsProfiles : AppCompatActivity() {
    // on below line we are creating variable for view pager,
    // viewpager adapter and the image list.

    private val viewModel by viewModels<petProfilesviewModel>()
    private lateinit var binding:ActivityPetsProfilesBinding
    lateinit var viewPagerAdapter: PetsViewPagerAdapter
    lateinit var imageList: List<Int>
    lateinit var PetsList: MutableList<PetResponse>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetsProfilesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // on below line we are initializing
        // our image list and adding data to it.

        viewModel.getPetsResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()

                }

                is BaseResponse.Error -> {

                    stopLoading()
                }
                else -> {
                    stopLoading()
                }
            }
        }




        viewModel.getPets( SessionManager.getString(this,"id")!!)
        viewModel.Petslist.observe(this) {
            println("data in response holder has changed!!!!!")
            PetsList = it
            // on below line we are initializing our view
            // pager adapter and adding image list to it.
            viewPagerAdapter  = PetsViewPagerAdapter(this@PetsProfiles, PetsList)

            // on below line we are setting
            // adapter to our view pager.


            viewPagerAdapter.notifyDataSetChanged()
            binding.petsViewpager.adapter = viewPagerAdapter
            println(PetsList)
        }







        // on below line we are initializing our view
        // pager adapter and adding image list to it.



        binding.back.bringToFront()
       binding.back.setOnClickListener()
       {

           finish()
       }
        binding.addPetButton.setOnClickListener()
        {
            val intent = Intent(this, AddPet::class.java)
            startActivity(intent)

        }




    }

    fun showLoading() {
        binding.loadingAnimation.isVisible=true

    }
    fun stopLoading() {


        binding.loadingAnimation.isVisible=false
    }

}