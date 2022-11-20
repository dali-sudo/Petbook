package com.example.petbook.views

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.databinding.ActivityProfilPostsBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
import com.example.petbook.viewModel.ProfilViewModel


class ProfilPosts : AppCompatActivity() {
    private lateinit var binding: ActivityProfilPostsBinding
    lateinit var PostList : MutableList<PostResponse>
    private val viewModel by viewModels<PostViewModel>()
    private val profilviewModel by viewModels<ProfilViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilPostsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        PostList = ArrayList()
        val intent = intent
        val value = intent.getStringExtra("id")
        if (SessionManager.getString(this,"id") !=null )
        {




            viewModel.getPostsByUser(value.toString())
            var postAdpater = PostAdpater(this,PostList, viewModel)
            viewModel.list.observe(this) {
                PostList = it
                postAdpater = PostAdpater(this, PostList, viewModel)
                postAdpater.notifyDataSetChanged()
                binding.PostRv.adapter = postAdpater
                profilviewModel.GetUser(value.toString())
var followed =false
                var userid:String
                profilviewModel.userResult.observe(this) {
                    when (it) {
                        is BaseResponse.Loading -> {

                        }

                        is BaseResponse.Success -> {
                            binding.profileusername.setText(it.data!!.username)
                            binding.followerscount.setText(it.data.followerscount)
                            binding.followingcount.setText(it.data.followingcount)
                            val imageBytes = Base64.decode(it.data!!.avatar, Base64.DEFAULT)
                            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            binding.profilicon.setImageBitmap(decodedImage)

                            if( SessionManager.getString(this,"id")in it.data!!.followers )
                            {
                                followed=true

                                }
                            if(value.toString()==SessionManager.getString(this,"id") ){
                                binding.button3.setText("EDIT PROFILE")}
                                else if(followed){

                                        binding.button3.setText("Unfollow")
                                        binding.button3.setOnClickListener(){
                                            profilviewModel.UnFollow(SessionManager.getString(this,"id").toString(),value.toString())
                                            this.recreate();
                                        }
                                    }
                                    else
                                    {binding.button3.setText("follow")
                                        binding.button3.setOnClickListener(){
                                            profilviewModel.Follow(SessionManager.getString(this,"id").toString(),value.toString())
                                            this.recreate();
                                        }

                                    }

                        }

                        is BaseResponse.Error -> {

                        }
                        else -> {
                        }
                    }

                }

            }




            binding.PostRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.PostRv.adapter = postAdpater
            binding.swiperefresh.setOnRefreshListener {
                viewModel.getPostsByUser(value.toString())
                postAdpater = PostAdpater(this,PostList, viewModel)
                postAdpater.notifyDataSetChanged()
                binding.PostRv.adapter = postAdpater
                binding.swiperefresh.setRefreshing(false)
            }

        }


    }
}