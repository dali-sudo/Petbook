package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.databinding.ActivitySignupBinding

class profil : AppCompatActivity() {
    private lateinit var binding:ActivityProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}