package com.example.petbook.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.petbook.R
import com.example.petbook.databinding.ActivityAddPostBinding
import com.example.petbook.databinding.ChoiceChipBinding
import com.example.petbook.model.PetResponse
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
import com.example.petbook.viewModel.petProfilesviewModel
import com.google.android.material.chip.Chip
import java.util.regex.Matcher
import java.util.regex.Pattern
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.util.*

class AddPost : AppCompatActivity() {
    private val petsviewModel by viewModels<petProfilesviewModel>()

    private var encodedImage:String = "";

    lateinit var images : MutableList<String>
    lateinit var posts :MutableList< PostResponse>
    private lateinit var binding: ActivityAddPostBinding
    var imageUri: Uri? = null
    val pickImage = 100
    var index=0
    private val viewModel by viewModels<PostViewModel>()
    lateinit var PetsList: MutableList<PetResponse>
    lateinit var nameList:  ArrayList<String?>
    lateinit var taggedList : ArrayList<String>
    lateinit var taggedList1 : Map<String,String>
    lateinit var IdsList : ArrayList<String>


    companion object {
        private var loaded: Boolean = false
    }


    private fun setupChip() {

        petsviewModel.getPets( SessionManager.getString(this,"id")!!)
        petsviewModel.Petslist.observe(this) {
            PetsList = it

               nameList = it.mapTo(arrayListOf()) { it.petName }


            taggedList1 = it.map { it.petName!! to it.id!!}.toMap()
            println(nameList + "**************")
            if (!loaded) {
                for (name in nameList) {
                    val chip = createChip(name!!)
                    binding.tagLayout.addView(chip)
                }
                loaded = true
            }

        }



    }

    private fun createChip(label: String): Chip {
        val chip = ChoiceChipBinding.inflate(layoutInflater).root
        chip.text = label
        return chip

    }


    private fun getTaggedChips() {

        val chipsIds = binding.tagLayout.checkedChipIds
        for (id in chipsIds)
        {
            val chip = binding.tagLayout.findViewById<Chip>(id)
            taggedList.add(chip.text.toString())
            println(taggedList)

        }

        // check the tagged Ids and remove the rest
        for (i in taggedList.indices)
        {
            for (key in taggedList1.keys)
            {
                if (key != taggedList[i])
                {
                    taggedList1.toMutableMap().remove(key)
                }
                else {
                    IdsList.add(taggedList1[key]!!)
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        taggedList= arrayListOf<String>()
        IdsList= arrayListOf<String>()


        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupChip()
        binding.addpostImageView.visibility = View.GONE
      if (SessionManager.getString(this,"profilePic") !=null )
        {
            val imageBytes = Base64.decode(SessionManager.getString(this,"profilePic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.postProfileIcon.setImageBitmap(decodedImage)
            binding.addpostImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        

         images= ArrayList()
        binding.addPhoto.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }
        binding.addmoreLayout.setOnClickListener(){
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

                val imageBytes = Base64.decode(images.get(index), Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.addpostImageView.setImageBitmap(decodedImage)
                binding.addpostImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            binding.imageView20.setOnClickListener() {
                if (index == 0) {
                    index = images.size - 1
                } else index--

                val imageBytes = Base64.decode(images.get(index), Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.addpostImageView.setImageBitmap(decodedImage)
                binding.addpostImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }

          /*  binding.button2.setOnClickListener() {

                taggedList.clear()
                IdsList.clear()
                getTaggedChips()
                viewModel.AddPost(
                    binding.editTextTextMultiLine.text.toString(),
                    images,
                    SessionManager.getString(this,"id")!!,
                    IdsList

                )


                finish()


            }
*/








        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            getContentResolver().takePersistableUriPermission(imageUri !!, Intent.FLAG_GRANT_READ_URI_PERMISSION);
           binding.addpostImageView.setImageURI(imageUri )
            binding.addpostImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.addpostImageView.visibility = View.VISIBLE
            val baos = ByteArrayOutputStream()
            val bitmap = binding.addpostImageView.drawable.toBitmap()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            images.add(encodedImage)
            if(images.size>0) {
                binding.addPhoto.visibility = GONE
                binding.divider.visibility = GONE
                binding.addmoreLayout.visibility = VISIBLE
            }
            images.forEach{
                if(images.size>1) {

                    binding.imageView18.visibility = View.VISIBLE
                    binding.imageView20.visibility = View.VISIBLE

                }
            index=images.size-1}
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.post_toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){



            R.id.addpost_iconid -> {
                taggedList.clear()
                getTaggedChips()
                viewModel.AddPost(
                    binding.editTextTextMultiLine.text.toString(),
                    images,
                    SessionManager.getString(this,"id")!!,
                    taggedList

                )

                finish()

            }


        }
        return super.onOptionsItemSelected(item)
    }


}