package com.example.petbook.views

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.UserSingleItemBinding
import com.example.petbook.model.NotificationResponse


class NotificationAdapter (val context: Context, val List: MutableList<NotificationResponse>):
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>(){

    inner class NotificationViewHolder(val itemBinding: UserSingleItemBinding): RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bindItem(noti: NotificationResponse){
            if(noti.sender!!.avatar!=null) {
                val imageBytes = Base64.decode(noti.sender!!.avatar, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                itemBinding.userIcon.setImageBitmap(decodedImage)
            }

            itemBinding.searchUsername.text=noti.title


        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):   NotificationAdapter.  NotificationViewHolder {
        return    NotificationViewHolder(UserSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NotificationAdapter.NotificationViewHolder, position: Int) {


        var noti =   NotificationResponse(
         List[position].id,
      List[position].sender,
           List[position].receiver,
            List[position].type,
            List[position].title,
            List[position].content,
            List[position].created

        )
        holder.bindItem(noti)


    }

    override fun getItemCount(): Int {
        return List.size
    }

}