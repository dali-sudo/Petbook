package com.example.petbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.example.petbook.databinding.ActivityMainBinding
import com.example.petbook.databinding.ActivitySignupBinding
import com.example.petbook.views.HomeFragment
import com.example.petbook.views.profil
import com.example.petbook.views.signin
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.app_bar))

      binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.page_1 -> println("11111")

                R.id.page_2 -> println("222222")

                else -> {

                }
            }
          true
            }



        supportFragmentManager.beginTransaction().add(R.id.MainfragmentContainerView, HomeFragment()).commit()
        }






    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.MainfragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){


            R.id.profile_iconid -> {
                val intent = Intent(this, profil::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}




