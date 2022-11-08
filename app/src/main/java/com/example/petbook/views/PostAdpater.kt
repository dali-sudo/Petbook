package com.example.petbook.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.databinding.PostSingleItemBinding
import com.example.petbook.model.Post
import com.google.android.gms.tasks.Task

class   PostAdpater(val PostList: MutableList<Post>):RecyclerView.Adapter<PostAdpater.PostViewHolder>(){

  inner class PostViewHolder(val itemBinding:PostSingleItemBinding):RecyclerView.ViewHolder(itemBinding.root)
  {
    fun bindItem(post:Post){
      itemBinding.PostUsername.text=post.PostUsername
      itemBinding.PostImage.setImageResource(post.PostImage)
      itemBinding.PostDescreption.text=post.PostDesc
      itemBinding.postdateId.text=post.PostDate

    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
   return  PostViewHolder(PostSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
  val post =PostList[position]
    holder.bindItem(post)
  }

  override fun getItemCount(): Int {
    return PostList.size
  }


}