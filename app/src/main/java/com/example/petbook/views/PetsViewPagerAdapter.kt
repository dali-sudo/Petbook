package com.example.petbook.views

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.petbook.R
import com.example.petbook.model.Pet
import com.example.petbook.model.PetResponse
import java.util.*
import android.provider.MediaStore

import android.graphics.Bitmap
import android.net.Uri
import com.example.petbook.repository.SessionManager
import java.io.ByteArrayOutputStream


class PetsViewPagerAdapter (val context: Context, val petList: MutableList<PetResponse>) : PagerAdapter() {

    // on below line we are creating a method
    // as get count to return the size of the list.
    override fun getCount(): Int {
        return petList.size
    }

    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    // on below line we are initializing
    // our item and inflating our layout file
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // on below line we are inflating our custom
        // layout file which we have created.
        val itemView: View = mLayoutInflater.inflate(R.layout.profil_slider_item, container, false)

        // on below line we are initializing
        // our image view with the id.

        var pet = Pet(
            petList[position].id,
            petList[position].petName,
            petList[position].petType,
            petList[position].petOwner,
            petList[position].petPic,
            petList[position].sexe,
            petList[position].petAge

        )


        val imageView: ImageView = itemView.findViewById<View>(R.id.bannerImageView) as ImageView
        val title: TextView = itemView.findViewById<View>(R.id.titleTv) as TextView
        val race: TextView = itemView.findViewById<View>(R.id.descriptionTv) as TextView
        val gender: TextView = itemView.findViewById<View>(R.id.genderTv) as TextView

        val singleProfile: LinearLayout = itemView.findViewById<LinearLayout>(R.id.clickableProfile) as LinearLayout

        // on below line we are setting
        // image resource for image view.
      //  imageView.setImageResource(imageList.get(position))
        if (petList.get(position).petPic!=null )
        {
            val imageBytes = Base64.decode(petList.get(position).petPic, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(decodedImage)


        }
        title.setText(petList.get(position).petName)
        race.setText(petList.get(position).race)
        gender.setText(petList.get(position).sexe)



        singleProfile.setOnClickListener()  {

            val intent = Intent(context, singlePetProfile::class.java)
            intent.putExtra("petName", petList.get(position).petName)
            intent.putExtra("petRace", petList.get(position).petName)
            intent.putExtra("idPet", petList.get(position).id)

         if ( petList.get(position).petPic !=null){
             SessionManager.saveString(context,"petPic",petList.get(position).petPic!!)
         }

         //   intent.putExtra("petPic", getImageUriString(context,decodedImage))


            //bitmap to uri



          //
            context.startActivity(intent)



        }
        // on the below line we are adding this
        // item view to the container.






        Objects.requireNonNull(container).addView(itemView)





        // on below line we are simply
        // returning our item view.
        return itemView
    }

    // on below line we are creating a destroy item method.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as LinearLayout)
    }

    fun getImageUriString(inContext: Context, inImage: Bitmap): String? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return path.toString()
    }
}

