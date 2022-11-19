package com.example.petbook.views

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.databinding.ActivityMainBinding
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.databinding.ActivityProfilPostsBinding
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel

class ProfilPosts : AppCompatActivity() {
    private lateinit var binding: ActivityProfilPostsBinding
    lateinit var PostList : MutableList<PostResponse>
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilPostsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        PostList = ArrayList()

        if (SessionManager.getString(this,"id") !=null )
        {




            viewModel.getPostsByUser(SessionManager.getString(this,"id").toString())
            var postAdpater = PostAdpater(this,PostList, viewModel)
            viewModel.list.observe(this) {
                PostList = it
                postAdpater = PostAdpater(this,PostList, viewModel)
                postAdpater.notifyDataSetChanged()
                binding.PostRv.adapter = postAdpater
            }


            binding.PostRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.PostRv.adapter = postAdpater
            binding.swiperefresh.setOnRefreshListener {
                viewModel.getPostsByUser(SessionManager.getString(this,"id").toString())
                postAdpater = PostAdpater(this,PostList, viewModel)
                postAdpater.notifyDataSetChanged()
                binding.PostRv.adapter = postAdpater
                binding.swiperefresh.setRefreshing(false)

                binding.profilicon.set
            }

        }


    }
}