package com.example.petbook.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.databinding.ActivitySignupBinding
import com.example.petbook.repository.SessionManager

class profil : AppCompatActivity() {
    private lateinit var binding:ActivityProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fullnametxt.text = SessionManager.getString(this,"username")
        binding.linearLayout5.setOnClickListener()
        {

            SessionManager.clearData(this)
            val intent = Intent(this, signin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)


        }

    }
}