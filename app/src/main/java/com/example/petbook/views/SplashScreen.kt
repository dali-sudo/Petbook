package com.example.petbook.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.petbook.R

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 10000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, signin::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
    }
