package com.example.petbook.views

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.UserSingleItemBinding
import com.example.petbook.model.ChatContactsResponse
import com.example.petbook.model.SearchResponse
import com.example.petbook.viewModel.SearchUsersViewModel

class ChatContactsAdapter(val context: Context, val List: MutableList<ChatContactsResponse>):
    RecyclerView.Adapter<ChatContactsAdapter.ChatContactsViewHolder>(){

    inner class ChatContactsViewHolder(val itemBinding: UserSingleItemBinding): RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bindItem(contact: ChatContactsResponse){
            if(contact.Users[0].avatar!=null) {
                val imageBytes = Base64.decode(contact.Users[0].avatar, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                itemBinding.userIcon.setImageBitmap(decodedImage)
            }

            itemBinding.searchUsername.text=contact.Users[0].username

            itemBinding.userSearchlayout.setOnClickListener(){

                val myIntent = Intent(context, ChatActivity::class.java)
                myIntent.putExtra("id", contact.id) //Optional parameters

                context.startActivity(myIntent)
            }

        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatContactsViewHolder {
        return    ChatContactsViewHolder(UserSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder:  ChatContactsViewHolder, position: Int) {


        var contact= ChatContactsResponse(
          List[position].id,
            List[position].Users,
        )
        holder.bindItem(contact)


    }

    override fun getItemCount(): Int {
        return List.size
    }

}