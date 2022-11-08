package com.example.petbook.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.petbook.MainActivity
import com.example.petbook.R
import com.example.petbook.repository.SessionManager

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 10000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, SPLASH_TIME_OUT)
        }
        else {

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, signin::class.java))
                finish()
            }, SPLASH_TIME_OUT)


        }

    }

    }
