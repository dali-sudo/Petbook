package com.example.petbook.views

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.R
import com.example.petbook.databinding.PostSingleItemBinding
import com.example.petbook.databinding.UserSingleItemBinding
import com.example.petbook.model.Post
import com.example.petbook.model.PostResponse
import com.example.petbook.model.SearchResponse
import com.example.petbook.model.User
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.PostViewModel
import com.example.petbook.viewModel.SearchUsersViewModel
import java.text.SimpleDateFormat
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


        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return    SearchViewHolder(UserSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder:  SearchViewHolder, position: Int) {

        println("222222")
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