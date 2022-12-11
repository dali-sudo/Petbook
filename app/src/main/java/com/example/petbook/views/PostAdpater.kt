package com.example.petbook.views

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.R
import com.example.petbook.databinding.PostSingleItemBinding
import com.example.petbook.model.Post
import com.example.petbook.model.PostResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
class   PostAdpater(val context:Context,val PostList: MutableList<PostResponse>,private val postViewModel: PostViewModel):RecyclerView.Adapter<PostAdpater.PostViewHolder>(){

  inner class PostViewHolder(val itemBinding:PostSingleItemBinding):RecyclerView.ViewHolder(itemBinding.root)
  {
    fun bindItem(post:Post){
var liked=false
      var i=0
        if(post.PostUsername!=null) {
            itemBinding.PostUsername.text = post.PostUsername
        }
        println(post.PostImage.get(0))
if(post.PostImage.size>0)
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
        }
      }
      else{
          val imageBytes = Base64.decode(post.PostImage.get(i), Base64.DEFAULT)
          val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
          itemBinding.PostImage.setImageBitmap(decodedImage)}
    val imageBytes = Base64.decode(post.PostImage.get(i), Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    itemBinding.PostImage.setImageBitmap(decodedImage)
    }
        else{
    itemBinding.PostImage.visibility = View.GONE
        }

    if(post.PostUserImage!=null) {
        val imageBytes = Base64.decode(post.PostUserImage, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        itemBinding.userIcon.setImageBitmap(decodedImage)
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
      for (item in post.likes) {
        if (item.equals( SessionManager.getString(context,"id")!!)) {

          itemBinding.imageView2.setImageResource(R.drawable.liked_icon)
          itemBinding.textView8.text = "Liked"
          liked=true
          break
        }
      }
itemBinding.imageView2.setOnClickListener(){

var count=post.likesCount.toInt()
  if(liked)
  {
      itemBinding.imageView2.setImageResource(R.drawable.like_icon)
    itemBinding.textView8.text="Like"
    postViewModel.UnlikePost(post.id,count.toString(), SessionManager.getString(context,"id")!!)
      itemBinding.likes.text=count.toString()+" likes"
    liked=false

  }
    else
    {
println("yes")
count++
      itemBinding.imageView2.setImageResource(R.drawable.liked_icon)
      itemBinding.textView8.text="Liked"
        postViewModel.LikePost(post.id,count.toString(), SessionManager.getString(context,"id")!!)
        itemBinding.likes.text=count.toString()+" likes"
      liked=true
    }


}



  }}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
   return  PostViewHolder(PostSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

      println("222222")
      var post = Post(
        PostList[position].id,
        PostList[position].images,
        PostList[position].owner.username,
        PostList[position].date,
        PostList[position].description,
        PostList[position].likescount,
        PostList[position].likes,
          PostList[position].owner.avatar
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