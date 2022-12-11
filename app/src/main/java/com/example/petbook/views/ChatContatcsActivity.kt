package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.R
import com.example.petbook.databinding.ActivityChatContatcsBinding
import com.example.petbook.model.ChatContactsResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.ChatViewModel

class ChatContatcsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatContatcsBinding
    lateinit var list : MutableList<ChatContactsResponse>
    private val viewModel by viewModels<ChatViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatContatcsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        list=ArrayList()
        if (SessionManager.getString(this,"id") !=null ) {
            viewModel.getContatcs(SessionManager.getString(this,"id").toString())
        }
        var chatAdpater =ChatContactsAdapter(this,list)
        viewModel.Contacs.observe(this) {
            list = it
            chatAdpater =ChatContactsAdapter(this,list)
            chatAdpater.notifyDataSetChanged()
            binding.SearchRv.adapter = chatAdpater
        }

        binding.SearchRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.SearchRv.adapter = chatAdpater

        val toolbar: Toolbar = findViewById(R.id.app_bar2)

        setSupportActionBar(toolbar)

        toolbar.setTitle("Contacts")
        toolbar.setNavigationOnClickListener(){
            finish()
        }

    }
}