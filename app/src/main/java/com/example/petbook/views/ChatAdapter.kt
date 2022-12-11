package com.example.petbook.views

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.ItemReceivedMessageBinding
import com.example.petbook.model.ChatRoomResponse
import com.example.petbook.model.message
import com.example.petbook.repository.SessionManager


class ChatAdapter(val context: Context, val List: MutableList<ChatRoomResponse.Data>,val users:MutableList<ChatRoomResponse.Data2>):
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    inner class ChatViewHolder(val itemBinding: ItemReceivedMessageBinding ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(chat: message) {

            val id = SessionManager.getString(context, "id")
            if (chat.id == id) {
                itemBinding.nameTxt.visibility = GONE
                itemBinding.receivedTxt.visibility = GONE
                itemBinding.receivedImg.visibility = GONE
                if(chat.type=="string") {
                    itemBinding.sentImg.visibility = GONE
                    itemBinding.sentTxt.visibility= VISIBLE
                    itemBinding.sentTxt.setText(chat.message)
                }

                else if (chat.type == "image"){
                    if(chat.message!=null) {
                        itemBinding.sentTxt.visibility=GONE
                        itemBinding.sentImg.visibility= VISIBLE
                        val imageBytes = Base64.decode(chat.message, Base64.DEFAULT)
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        itemBinding.sentImg.setImageBitmap(decodedImage)
                    }
                }

            } else {
                itemBinding.sentTxt.visibility = GONE
                itemBinding.sentImg.visibility = GONE
                itemBinding.nameTxt.visibility = VISIBLE
                if (chat.type == "string") {
                    itemBinding.receivedImg.visibility=GONE
                    itemBinding.receivedTxt.visibility = VISIBLE
                    itemBinding.receivedTxt.setText(chat.message)
                } else if (chat.type == "image"){
                    if(chat.message!=null) {
                        itemBinding.receivedTxt.visibility=GONE
                        itemBinding.receivedImg.visibility= VISIBLE
                        val imageBytes = Base64.decode(chat.message, Base64.DEFAULT)
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        itemBinding.receivedImg.setImageBitmap(decodedImage)
                    }

            }
            }
            users.forEach {
                if (it.id == chat.id) {
                    itemBinding.nameTxt.setText(it.username)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatViewHolder {


            return ChatViewHolder(
                ItemReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    }

    override fun onBindViewHolder(holder: ChatAdapter.ChatViewHolder, position: Int) {


        var chat = message(
            List[position].sender_id,
            List[position].type,
            List[position].message
        )
        holder.bindItem(chat)


    }

    override fun getItemCount(): Int {
        return List.size
    }


}