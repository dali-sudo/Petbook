package com.example.petbook.views

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.UserSingleItemBinding
import com.example.petbook.model.SearchResponse
import com.example.petbook.viewModel.ProfilViewModel
import com.example.petbook.viewModel.SearchUsersViewModel
import java.util.*


class SearchAdapter(val context: Context, val SearchList: MutableList<SearchResponse>, private val searchUsersViewModel: SearchUsersViewModel):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    inner class SearchViewHolder(val itemBinding: UserSingleItemBinding): RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bindItem(user: SearchResponse){
            if(user.avatar!=null) {
                val imageBytes = Base64.decode(user.avatar, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                itemBinding.userIcon.setImageBitmap(decodedImage)
            }

                itemBinding.searchUsername.text=user.username

            itemBinding.userSearchlayout.setOnClickListener(){

                val myIntent = Intent(context, ProfilPosts::class.java)
                myIntent.putExtra("id", user.id) //Optional parameters

                context.startActivity(myIntent)
            }

        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return    SearchViewHolder(UserSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder:  SearchViewHolder, position: Int) {


        var user = SearchResponse(
            SearchList[position].id,
            SearchList[position].username,
            SearchList[position].avatar
        )
        holder.bindItem(user)


    }

    override fun getItemCount(): Int {
        return SearchList.size
    }

}