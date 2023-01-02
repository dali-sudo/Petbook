package com.example.petbook.views

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.repository.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class profil : AppCompatActivity() {
    private lateinit var binding:ActivityProfilBinding
    var gsc: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (SessionManager.getString(this,"profilePic") !=null )
        {
            val imageBytes = Base64.decode(SessionManager.getString(this,"profilePic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.imageView.setImageBitmap(decodedImage)

        }
        else{
        binding.fullnametxt.text = SessionManager.getString(this, "username")
        }
        binding.logoutLayout.setOnClickListener()
        {

            SessionManager.clearData(this)
            gsc?.signOut()
            val intent = Intent(this, signin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)


            startActivity(intent)

        }

        binding.petsButton.setOnClickListener()
        {


            val intent = Intent(this, PetsProfiles::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)


            startActivity(intent)

        }

        binding.profileBack.setOnClickListener() {
            finish()
        }

        binding.myAccLayout.setOnClickListener()
        {
            val myIntent = Intent(this, ProfilPosts::class.java)
            myIntent.putExtra("id",SessionManager.getString(this,"id") ) //Optional parameters
            this.startActivity(myIntent)

        }

        binding.petlayout.setOnClickListener()
        {


            val intent = Intent(this, PetsProfiles::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)


            startActivity(intent)

        }
    }
}