package com.example.petbook.views
import android.content.Intent
import com.example.petbook.viewModel.PostViewModel
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.example.petbook.R
import com.example.petbook.databinding.ActivityAddPostBinding
import com.example.petbook.databinding.ActivityDiscoverProfileBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.DiscoverViewModel
import java.text.SimpleDateFormat
import java.util.*
private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
class DiscoverProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiscoverProfileBinding
    private val viewModel by viewModels<DiscoverViewModel>()
    private val postViewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar: Toolbar = findViewById(R.id.app_bar2)

        setSupportActionBar(toolbar)

        toolbar.setTitle("Explore")
        val intent = intent
        val value = intent.getStringExtra("id")
        toolbar.setNavigationOnClickListener(){
            finish()
        }


            viewModel.getDiscoverPost(value.toString())

        viewModel.DiscoverPost.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {

                }

                is BaseResponse.Success -> {
                    var liked:Boolean=false
                    var i=0
                    var size=0
                    size =it.data?.images?.size!!
                    var imgs=it.data?.images
                    val post=it.data
                    if(it.data?.owner?.username!=null) {
                        binding.PostUsername.text= it.data?.owner?.username
                    }
                    if(it.data?.images?.size!! >0)
                    {
                        if(it.data?.images?.size!! >1) {
                            binding.imageView16.visibility = View.VISIBLE
                            binding.imageView15.visibility = View.VISIBLE
                            val imageBytes = Base64.decode(imgs?.get(i), Base64.DEFAULT)
                            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            binding.PostImage.setImageBitmap(decodedImage)
                            binding.imageView16.setOnClickListener() {
                              if (i == size!!- 1) {
                                    i = 0
                                } else {
                                    i++
                                }

                                if (imgs != null) {
                                    val imageBytes = Base64.decode(imgs.get(i), Base64.DEFAULT)
                                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                    binding.PostImage.setImageBitmap(decodedImage)
                                }
                            }
                            binding.imageView15.setOnClickListener() {
                                if (i == 0) {
                                    i = size- 1
                                } else {
                                    i--
                                }

                                if (imgs != null) {
                                    val imageBytes = Base64.decode(imgs.get(i), Base64.DEFAULT)
                                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                    binding.PostImage.setImageBitmap(decodedImage)
                                }
                            }
                        }
                        else{
                            if (imgs != null) {
                                val imageBytes = Base64.decode(imgs.get(i), Base64.DEFAULT)
                                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                binding.PostImage.setImageBitmap(decodedImage)
                            }


                        }

                    }
                    else{
                        binding.PostImage.visibility = View.GONE
                    }

                    if(post.owner.avatar!=null) {
                        val imageBytes = Base64.decode(post.owner.avatar, Base64.DEFAULT)
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        binding.userIcon.setImageBitmap(decodedImage)
                    }
                    binding.PostDescreption.text=post.description
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
                    val date: Date = sdf.parse(post.date)
                    val g= SimpleDateFormat("yyyy-MM-dd" )
                    val d = g.format(date)
                    println("date"+date)
                    val daten = Date()
                    val current = sdf.format(daten)

                    binding.postdateId.text=getTimeAgo(date)

                    binding.likes.text=post.likescount+" likes"
                    for (item in post.likes) {
                        if (item.equals( SessionManager.getString(this,"id")!!)) {

                            binding.imageView2.setImageResource(R.drawable.liked_icon)
                            binding.textView8.text = "Liked"
                            liked=true
                            break
                        }
                    }
                    binding.imageView2.setOnClickListener(){

                        var count=post.likescount.toInt()
                        if(liked)
                        {
                            binding.imageView2.setImageResource(R.drawable.like_icon)
                            binding.textView8.text="Like"
                            postViewModel.UnlikePost(post.id,count.toString(), SessionManager.getString(this,"id")!!)
                            binding.likes.text=count.toString()+" likes"
                            liked=false

                        }
                        else
                        {
                            println("yes")
                            count++
                            binding.imageView2.setImageResource(R.drawable.liked_icon)
                            binding.textView8.text="Liked"
                            postViewModel.LikePost(post.id,count.toString(), SessionManager.getString(this,"id")!!)
                            binding.likes.text=count.toString()+" likes"
                            liked=true
                        }


                    }
                    binding.PostUsername.setOnClickListener(){

                        val myIntent = Intent(this, ProfilPosts::class.java)
                        myIntent.putExtra("id", post.owner.id) //Optional parameters
                        startActivity(myIntent)

                    }
                    binding.userIcon.setOnClickListener(){

                        val myIntent = Intent(this, ProfilPosts::class.java)
                        myIntent.putExtra("id", post.owner.id) //Optional parameters
                        startActivity(myIntent)

                    }

                }
                else -> {}
            }
        }



    }
    fun getTimeAgo(date: Date): String {
        var time = date.time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val daten = Date()
        val now = daten.time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        println(diff)
        return when {
            diff < MINUTE_MILLIS -> "moments ago"
            diff < 2 * MINUTE_MILLIS -> "a minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
            diff < 2 * HOUR_MILLIS -> "an hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
            diff < 48 * HOUR_MILLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }

    }
}
