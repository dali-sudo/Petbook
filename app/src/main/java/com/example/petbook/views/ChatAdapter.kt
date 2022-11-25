package com.example.petbook.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.ItemReceivedMessageBinding
import com.example.petbook.model.ChatRoomResponse
import com.example.petbook.model.message
import com.example.petbook.repository.SessionManager


class ChatAdapter(val context: Context, val List: MutableList<ChatRoomResponse.Data>):
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    inner class ChatViewHolder(val itemBinding: ItemReceivedMessageBinding ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(chat: message) {

            val id = SessionManager.getString(context, "id")
            if(chat.id==id){
                itemBinding.nameTxt.visibility=GONE
                itemBinding.receivedTxt.visibility= GONE
                itemBinding.sentTxt.visibility= VISIBLE

                itemBinding.sentTxt.setText(chat.message)
            }
            else {
                itemBinding.nameTxt.visibility = VISIBLE
                itemBinding.receivedTxt.visibility = VISIBLE
                itemBinding.sentTxt.visibility= GONE
                itemBinding.receivedTxt.setText(chat.message)
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
            List[position].message
        )
        holder.bindItem(chat)


    }

    override fun getItemCount(): Int {
        return List.size
    }


}