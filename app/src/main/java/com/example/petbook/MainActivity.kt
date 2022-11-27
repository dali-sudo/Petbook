package com.example.petbook

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.petbook.databinding.ActivityMainBinding
import com.example.petbook.viewModel.PostViewModel
import com.example.petbook.viewModel.SearchUsersViewModel
import com.example.petbook.views.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.app_bar))

binding.floatingActionButton.setOnClickListener(){
    val intent = Intent(this, AddPost::class.java)
    startActivity(intent)
}

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

var bool=true



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
            R.id.chat_iconid -> {
                val intent = Intent(this, ChatContatcsActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}




