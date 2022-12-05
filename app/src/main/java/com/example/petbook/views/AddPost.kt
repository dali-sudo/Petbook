package com.example.petbook.views

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.activity.viewModels
import com.example.petbook.R
import com.example.petbook.databinding.ActivityAddPostBinding
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
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
        binding.addpostImageView.visibility = View.GONE
      if (SessionManager.getString(this,"profilePic") !=null )
        {
            val imageBytes = Base64.decode(SessionManager.getString(this,"profilePic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.postProfileIcon.setImageBitmap(decodedImage)

        }
        

         images= ArrayList()
        binding.button.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }
        val toolbar: Toolbar = findViewById(R.id.app_bar2)

        setSupportActionBar(toolbar)
toolbar.setNavigationOnClickListener(){
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

                finish()


            }


        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            getContentResolver().takePersistableUriPermission(imageUri !!, Intent.FLAG_GRANT_READ_URI_PERMISSION);
           binding.addpostImageView.setImageURI(imageUri )
            binding.addpostImageView.visibility = View.VISIBLE
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