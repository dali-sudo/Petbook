package com.example.petbook.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petbook.R
import com.example.petbook.databinding.ChoiceChipBinding
import com.example.petbook.databinding.PostSingleItemBinding
import com.example.petbook.model.Post
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
class   PostAdpater(val context:Context,val PostList: MutableList<PostResponse>,private val postViewModel: PostViewModel):RecyclerView.Adapter<PostAdpater.PostViewHolder>(){

  inner class PostViewHolder(val itemBinding:PostSingleItemBinding):RecyclerView.ViewHolder(itemBinding.root)
  {


      //TODO pass the id of the pet as a parameter and send it as extra instead of name of pet
      private fun createChip(label: String, petId : String)  {

          val chip = Chip(context)
          chip.text = label

          itemBinding.tagLayout.addView(chip)
          println(chip.text)

          chip.setOnClickListener() {
              val intent = Intent(context, singlePetProfile::class.java)
              intent.putExtra("nameOfpet",chip.text)
              intent.putExtra("petId",petId)
              context.startActivity(intent)
            println("clicked successfully")
          }


      }

      // TODO the tagged List needs to be of type petResponse and i get the petname and the pet id
      fun setupChip(TaggedList : Map<String,String>) {
          //val nameList = TaggedList.mapTo(arrayListOf()) { it.petName }


          for (key in TaggedList.keys)
          {
              createChip(TaggedList[key]!!, key)
              println(key + TaggedList[key])
          }
      }



    fun bindItem(post:Post){
var liked=false
      var i=0

        if(post.PostUsername!=null) {
            itemBinding.PostUsername.text = post.PostUsername
        }

        if (post.tags?.size!!>0)  {

        setupChip(post.tags)

        }




if(post.PostImage!!.size>0)
{
      if(post.PostImage.size>1) {
        itemBinding.imageView16.visibility = View.VISIBLE
        itemBinding.imageView15.visibility = View.VISIBLE

        itemBinding.imageView16.setOnClickListener() {
          if (i == post.PostImage.size - 1) {
            i = 0
          } else {
            i++
          }

            val imageBytes = Base64.decode(post.PostImage.get(i), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
          itemBinding.PostImage.setImageBitmap(decodedImage)
            itemBinding.PostImage.setScaleType(ImageView.ScaleType.FIT_XY)
        }
        itemBinding.imageView15.setOnClickListener() {
          if (i == 0) {
            i = post.PostImage.size - 1
          } else {
            i--
          }

            val imageBytes = Base64.decode(post.PostImage.get(i), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            itemBinding.PostImage.setImageBitmap(decodedImage)
            itemBinding.PostImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
      }
      else{
          val imageBytes = Base64.decode(post.PostImage.get(i), Base64.DEFAULT)
          val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
          itemBinding.PostImage.setImageBitmap(decodedImage)
          itemBinding.PostImage.setScaleType(ImageView.ScaleType.FIT_XY);}
    val imageBytes = Base64.decode(post.PostImage.get(i), Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    itemBinding.PostImage.setImageBitmap(decodedImage)
    itemBinding.PostImage.setScaleType(ImageView.ScaleType.FIT_XY);
    }
        else{
    itemBinding.PostImage.visibility = View.GONE
        }

  if(post.PostUserImage!=null) {


        val imageBytes = Base64.decode(post.PostUserImage, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        Glide.with(context).load(decodedImage).override(600, 200).into(itemBinding.userIcon)
       // itemBinding.userIcon.setImageBitmap(decodedImage)



    }



      itemBinding.PostDescreption.text=post.PostDesc
      val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
      val date: Date = sdf.parse(post.PostDate)
      val g= SimpleDateFormat("yyyy-MM-dd" )
      val d = g.format(date)
      println("date"+date)
      val daten = Date()
      val current = sdf.format(daten)

      itemBinding.postdateId.text=getTimeAgo(date)

      itemBinding.likes.text=post.likesCount+" likes"
        if(post.likes!=null) {
            for (item in post.likes) {
                if(SessionManager.getString(context, "id")!=null) {
                    if (item.equals(SessionManager.getString(context, "id")!!)) {

                        itemBinding.imageView2.setImageResource(R.drawable.liked_icon)
                        itemBinding.textView8.text = "Liked"
                        liked = true

                    }
                }
            }
        }

            var count = post.likesCount!!.toInt()

itemBinding.imageView2.setOnClickListener() {
    if (liked) {
        if (post.id != null) {
            count--
            itemBinding.imageView2.setImageResource(R.drawable.like_icon)
            itemBinding.textView8.text = "Like"
            postViewModel.UnlikePost(
                post.id,
                count.toString(),
                SessionManager.getString(context, "id")!!
            )
            itemBinding.likes.text = count.toString() + " likes"
            liked = false
        }
    } else {
        count++
        itemBinding.imageView2.setImageResource(R.drawable.liked_icon)
        itemBinding.textView8.text = "Liked"
        if (post.id != null) {
            postViewModel.LikePost(
                post.id,
                count.toString(),
                SessionManager.getString(context, "id")!!
            )
            itemBinding.likes.text = count.toString() + " likes"
            liked = true
        }
    }
}
    if (SessionManager.getString(context,"id") !=null ) {
if(post.ownerid==SessionManager.getString(context,"id"))
    itemBinding.deleteIcon.visibility=VISIBLE
    }
        itemBinding.deleteIcon.setOnClickListener() {
            val builder1: AlertDialog.Builder = AlertDialog.Builder(context)
            builder1.setTitle("Delete Post")
            builder1.setMessage("Are you sure you want to delete this Post?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, id ->

                 postViewModel.DeletePost(post.id.toString())
                   PostList.removeAt(position)
                    notifyDataSetChanged()
                    dialog.cancel()})

            builder1.setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            val alert11: AlertDialog = builder1.create()
            alert11.show()
        }




  }
  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
   return  PostViewHolder(PostSingleItemBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) {


      var post = Post(
        PostList[position].id,
        PostList[position].images,
        PostList[position].owner.username,
        PostList[position].date,
        PostList[position].description,
        PostList[position].likescount,
        PostList[position].likes,
        PostList[position].owner.avatar,
        PostList[position].tags.map { it.id to it.petname }.toMap(), // here i need a map of key : id and value petname
          PostList[position].owner.id
      )
     holder.bindItem(post)


  }

  override fun getItemCount(): Int {
    return PostList.size
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