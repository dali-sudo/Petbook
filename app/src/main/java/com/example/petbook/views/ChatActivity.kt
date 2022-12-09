package com.example.petbook.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.emoji2.bundled.BundledEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.dataapi.SocketHandler
import com.example.petbook.databinding.ActivityChatBinding
import com.example.petbook.model.ChatRoomResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.ChatViewModel
import io.socket.client.Socket
import java.io.ByteArrayOutputStream

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel by viewModels<ChatViewModel>()
    lateinit var List : ChatRoomResponse
    lateinit var Chat1 : List<ChatRoomResponse.Data>
    lateinit var Users : List<ChatRoomResponse.Data2>
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var encodedImage:String = "";
   private lateinit var  value:String
    private lateinit var mSocket: Socket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EmojiCompat.init(BundledEmojiCompatConfig(this))

        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        val intent = intent
        value = intent.getStringExtra("id").toString()
        setContentView(view)
SocketHandler.setSocket()
         mSocket =SocketHandler.getSocket()

        mSocket.connect()

        mSocket.emit("storeClientInfo",SessionManager.getString(this,"id").toString() );

mSocket.on("refresh"){
    if(value!=null) {
        viewModel.get(value)

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
                    viewModel.sendmessage(value.toString(), msg.toString(),  SessionManager.getString(this, "id").toString(),"string")
                    binding.messageEdit.setText("")
                    for(i in List.chat){
                        if(i.sender_id!= SessionManager.getString(this, "id").toString()){
                        mSocket.emit("send", i.sender_id)
                         break;}
                    }
                    if (value != null) {
                        viewModel.get(value)
                    }
                }
            }
        }

       binding.sendImage.setOnClickListener(){
           if (SessionManager.getString(this,"id") !=null ) {
               val gallery =
                   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
               startActivityForResult(gallery, pickImage)
           }

       }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            val baos = ByteArrayOutputStream()
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver,imageUri!!))
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

            viewModel.sendmessage(value.toString(), encodedImage,  SessionManager.getString(this, "id").toString(),"image")
            binding.messageEdit.setText("")
            for(i in List.chat){
                if(i.sender_id!= SessionManager.getString(this, "id").toString()){
                    mSocket.emit("send", i.sender_id)
                    break;}
            }
            if (value != null) {
                viewModel.get(value)
            }

        }
    }

}