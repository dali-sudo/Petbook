package com.example.petbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.example.petbook.databinding.ActivityMainBinding
import com.example.petbook.databinding.ActivitySignupBinding
import com.example.petbook.views.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
      binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.page_1 -> println("11111")

                R.id.page_2 -> println("222222")

                else -> {

                }
            }
          true
            }
        }






    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.MainfragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}




