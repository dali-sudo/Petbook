package com.example.petbook

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem

import android.view.MotionEvent
import android.view.View
import android.view.View.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.petbook.databinding.ActivityMainBinding

import com.example.petbook.viewModel.PostViewModel
import com.example.petbook.viewModel.SearchUsersViewModel
import com.example.petbook.views.*

import com.example.petbook.databinding.ActivitySignupBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.EditUserViewModel
import com.example.petbook.viewModel.SigninViewModel


class MainActivity : AppCompatActivity() {
    companion object {
  var  currentFragment="home"
    }
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<PostViewModel>()

 //   private val viewModel by viewModels<EditUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setSupportActionBar(findViewById(R.id.app_bar))

        val serviceintent = Intent(this, BackgroundService::class.java)
        startService(serviceintent)

binding.floatingActionButton.setOnClickListener(){
    val intent = Intent(this, AddPost::class.java)
    startActivity(intent)
}

      binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.page_1 ->{

                    binding.floatingActionButton.visibility = VISIBLE

                    binding.SearchTextField.visibility = VISIBLE


                    supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView, HomeFragment()).commit()
                    currentFragment="home"

                }

            

                R.id.page_2 -> { supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView,DiscoverFragment()).commit()
                    binding.SearchTextField.visibility= GONE
                    binding.floatingActionButton.visibility=GONE
                    binding.backToHome.visibility= GONE
                    currentFragment="discover"}
                R.id.page_3 ->{
                    currentFragment="map"
                    binding.SearchTextField.visibility= GONE
                    binding.backToHome.visibility= GONE
                    binding.floatingActionButton.visibility= GONE

                    supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView, MapsFragment()).commit()

                 }
                R.id.page_4 -> {
                    currentFragment="chat"
                    supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView,ChatContactsFragment()).commit()
                    binding.SearchTextField.visibility= GONE
                    binding.floatingActionButton.visibility=GONE
                    binding.backToHome.visibility= GONE}
                R.id.page_5-> { supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView,ProfilFragment()).commit()
                    binding.SearchTextField.visibility= GONE
                    binding.floatingActionButton.visibility=GONE
                    binding.backToHome.visibility= GONE
                    currentFragment="profil"}
                else -> {

                }
            }
          true
            }

      if(currentFragment=="home")
        supportFragmentManager.beginTransaction().add(R.id.MainfragmentContainerView, HomeFragment()).commit()
        if(currentFragment=="map") {
            supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView, MapsFragment()).commit()
            binding.SearchTextField.visibility= GONE
            binding.floatingActionButton.visibility=GONE
            binding.backToHome.visibility= GONE
        }
        if(currentFragment=="discover") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainfragmentContainerView, DiscoverFragment()).commit()
            binding.SearchTextField.visibility= GONE
            binding.floatingActionButton.visibility=GONE
            binding.backToHome.visibility= GONE
        }
        if(currentFragment=="chat") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainfragmentContainerView, ChatContactsFragment()).commit()
            binding.SearchTextField.visibility= GONE
            binding.floatingActionButton.visibility=GONE
            binding.backToHome.visibility= GONE
        }
        if(currentFragment=="profil") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainfragmentContainerView, ProfilFragment()).commit()
            binding.SearchTextField.visibility= GONE
            binding.floatingActionButton.visibility=GONE
            binding.backToHome.visibility= GONE
        }




binding.backToHome.setOnClickListener(){
    binding.backToHome.visibility = View.GONE
    supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView, HomeFragment()).commit()
    binding.SearchTextField2.setText("")
    binding.SearchTextField2.clearFocus();

}
        var value = ""
    binding.SearchTextField2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                value=s.toString()
if(value!="") {
    // Set the computed value to the other EditText
    binding.backToHome.visibility = View.VISIBLE
    val bundle = Bundle()
    bundle.putString("search", value)

    val fragobj = SearchFragment()
    fragobj.setArguments(bundle)
    supportFragmentManager.beginTransaction().replace(R.id.MainfragmentContainerView, fragobj)
        .commit()
}
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }




    override fun onResume() {
        super.onResume()

     /*   if (SessionManager.getString(this,"profilePic") !=null )
        {
            val imageBytes = Base64.decode(SessionManager.getString(this,"profilePic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.userIcon.setImageBitmap(decodedImage)


        }
*/
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


            R.id.notif_iconid -> {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)

            }


        }
        return super.onOptionsItemSelected(item)
    }

}




