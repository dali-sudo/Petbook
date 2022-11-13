package com.example.petbook.views

import android.content.Context
import android.graphics.Color
import android.net.Uri
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
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class   PostAdpater(val context:Context,val PostList: MutableList<PostResponse>,private val postViewModel: PostViewModel):RecyclerView.Adapter<PostAdpater.PostViewHolder>(){

  inner class PostViewHolder(val itemBinding:PostSingleItemBinding):RecyclerView.ViewHolder(itemBinding.root)
  {
    fun bindItem(post:Post){
var liked:Boolean=false
      var i=0
      itemBinding.PostUsername.text=post.PostUsername
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

          itemBinding.PostImage.setImageURI(Uri.parse(post.PostImage.get(i)))
        }
        itemBinding.imageView15.setOnClickListener() {
          if (i == 0) {
            i = post.PostImage.size - 1
          } else {
            i--
          }

          itemBinding.PostImage.setImageURI(Uri.parse(post.PostImage.get(i)))
        }
      }
      else{
      itemBinding.PostImage.setImageURI(Uri.parse(post.PostImage.get(i)))}
    itemBinding.PostImage.setImageURI(Uri.parse(post.PostImage.get(i)))
    }
      itemBinding.PostDescreption.text=post.PostDesc
      val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
      val date: Date = sdf.parse(post.PostDate)
      val g= SimpleDateFormat("yyyy-MM-dd" )
      val d = g.format(date)
      println("date"+date)
      val daten = Date()
      val current = sdf.format(daten)

      itemBinding.postdateId.text=d.toString()

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
        PostList[position].likes
      )
      holder.bindItem(post)


  }

  override fun getItemCount(): Int {
    return PostList.size
  }


}