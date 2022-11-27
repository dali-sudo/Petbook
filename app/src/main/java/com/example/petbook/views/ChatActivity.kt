package com.example.petbook.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.dataapi.SocketHandler
import com.example.petbook.databinding.ActivityChatBinding
import com.example.petbook.model.ChatRoomResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.ChatViewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel by viewModels<ChatViewModel>()
    lateinit var List : ChatRoomResponse
    lateinit var Chat1 : List<ChatRoomResponse.Data>
    lateinit var Users : List<ChatRoomResponse.Data2>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        val intent = intent
        val value = intent.getStringExtra("id")
        setContentView(view)
SocketHandler.setSocket()
        val mSocket =SocketHandler.getSocket()

        mSocket.connect()

        mSocket.emit("storeClientInfo",SessionManager.getString(this,"id").toString() );

mSocket.on("refresh"){
    if(value!=null) {
        viewModel.get(value)
        binding.chatRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}




        Chat1=ArrayList()
       Users=ArrayList()


List= ChatRoomResponse("0",Chat1,Users)
        if(value!=null) {
            viewModel.get(value)
        }
        var chatAdpater = ChatAdapter(this, List.chat as MutableList<ChatRoomResponse.Data>, List.Users as MutableList<ChatRoomResponse.Data2>)
        viewModel.list.observe(this) {
            List = it

            var chatAdpater = ChatAdapter(this,
                List.chat as MutableList<ChatRoomResponse.Data>,  List.Users as MutableList<ChatRoomResponse.Data2>)
            chatAdpater.notifyDataSetChanged()
            binding.chatRv.adapter = chatAdpater
            binding.chatRv.scrollToPosition(List.chat.size-1)
        }

        binding.chatRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.chatRv.adapter = chatAdpater

        binding.sendBtn.setOnClickListener(){
            val msg=binding.messageEdit.text
            if(!msg.isEmpty()){
                if (SessionManager.getString(this,"id") !=null ) {
                    viewModel.sendmessage(value.toString(), msg.toString(),  SessionManager.getString(this, "id").toString())
                    binding.messageEdit.setText("")
                    mSocket.emit("send",List.chat[0].sender_id)
                    if (value != null) {
                        viewModel.get(value)
                    }
                }
            }
        }
    }

}