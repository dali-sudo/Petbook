package com.example.petbook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.R
import com.example.petbook.databinding.ActivityNotificationBinding
import com.example.petbook.databinding.ActivityProfilPostsBinding
import com.example.petbook.model.ChatRoomResponse
import com.example.petbook.model.NotificationResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.NotificationViewModel
class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val viewModel by viewModels<NotificationViewModel>()
    private  lateinit var list : MutableList<NotificationResponse>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar: Toolbar = findViewById(R.id.app_bar2)

        setSupportActionBar(toolbar)

        toolbar.setTitle("Notifications")
        toolbar.setNavigationOnClickListener(){
            finish()
        }
        list = ArrayList()
        if (SessionManager.getString(this, "id") != null) {
            viewModel.getNotifications(SessionManager.getString(this, "id").toString())
}
            var notiAdpater = NotificationAdapter(this,  list)
            viewModel.list.observe(this) {
                list = it
                notiAdpater = NotificationAdapter(this,  list)
                notiAdpater.notifyDataSetChanged()
                binding.NotiRv.adapter = notiAdpater
            }

            binding.NotiRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.NotiRv.adapter = notiAdpater
        }
    }