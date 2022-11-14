package com.example.petbook.views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.activity.viewModels
import com.example.petbook.MainActivity
import com.example.petbook.R
import com.example.petbook.databinding.ActivityAddPostBinding
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.Post
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
import com.example.petbook.viewModel.SigninViewModel
import java.util.*

class AddPost : AppCompatActivity() {
    lateinit var images : MutableList<String>
    lateinit var posts :MutableList< PostResponse>
    private lateinit var binding: ActivityAddPostBinding
    var imageUri: Uri? = null
    val pickImage = 100
    var index=0
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
         images= ArrayList()
        binding.button.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }
        val toolbar: Toolbar = findViewById(R.id.app_bar2)

        setSupportActionBar(toolbar)
toolbar.setNavigationOnClickListener(){
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
}


binding.username.text=SessionManager.getString(this,"username")!!

            binding.imageView18.setOnClickListener() {
                if (index == images.size - 1) {
                    index = 0
                } else index++
                binding.addpostImageView.setImageURI(Uri.parse(images.get(index)))
                println(images.get(index))

            }
            binding.imageView20.setOnClickListener() {
                if (index == 0) {
                    index = images.size - 1
                } else index--
                binding.addpostImageView.setImageURI(Uri.parse(images.get(index)))
                println(images.get(index))

            }
            binding.button2.setOnClickListener() {
                viewModel.AddPost(
                    binding.editTextTextMultiLine.text.toString(),
                    images,
                    SessionManager.getString(this,"id")!!
                )


            }


        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            getContentResolver().takePersistableUriPermission(imageUri !!, Intent.FLAG_GRANT_READ_URI_PERMISSION);
           binding.addpostImageView.setImageURI(imageUri )
            images.add(imageUri.toString())
            println(images.size)
            images.forEach{ println(it)
                if(images.size>1) {
                    println("aaaaaaaaaaaaaa")
                    binding.imageView18.visibility = View.VISIBLE
                    binding.imageView20.visibility = View.VISIBLE
                }
            index=images.size-1}
        }
    }
}