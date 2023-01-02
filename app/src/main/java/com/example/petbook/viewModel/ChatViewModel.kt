package com.example.petbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*
import com.example.petbook.repository.ChatRepository
import com.example.petbook.repository.PostRepository
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    val chatRepo = ChatRepository()

    val list: MutableLiveData<ChatRoomResponse> = MutableLiveData()
    val sent: MutableLiveData<BaseResponse<ChatRoomResponse>> = MutableLiveData()
    val Contacs: MutableLiveData<MutableList<ChatContactsResponse>> = MutableLiveData()

val chatid: MutableLiveData<GetChatResponse> = MutableLiveData()
    fun get(id1:String) {


        viewModelScope.launch {
            try {
                val chatRoomRequest = ChatRoomRequest (

                    id = id1,


                    )

                val response = chatRepo.get(chatRoomRequest)
                if (response?.code() == 200) {
                    list.value= response.body()
                } else {

                }

            } catch (ex: Exception) {

                println(BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }
    fun getContatcs(id1:String) {


        viewModelScope.launch {
            try {
                val chatRoomRequest = ChatRoomRequest (

                    id = id1,


                    )

                val response = chatRepo.getContacts(chatRoomRequest)
                if (response?.code() == 200) {
                    Contacs.value= response.body()
                } else {

                }

            } catch (ex: Exception) {

                println(BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }


    fun sendmessage(mid:String,msg:String,sender:String,type:String) {

       sent.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val messageRequest= MessageRequest (

                    id = mid,
                    message=msg,
                    type=type,
                    senderid = sender
                    )

                val response = chatRepo.sendMessage(messageRequest)
                if (response?.code() == 200) {
                    sent.value = BaseResponse.Success(response.body())
                } else {
                    sent.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {

                println(BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }
    fun findOrCreate(id1:String,id2:String) {


        viewModelScope.launch {
            try {
                val getChatRequest = GetChatRequest (

                    mid = id1,
                    id=id2

                    )

                val response = chatRepo.findorcreate(getChatRequest)
                if (response?.code() == 200) {
                    chatid.value= response.body()
                } else {

                }

            } catch (ex: Exception) {

                println(BaseResponse.Error(ex.message ))
            }
        }
        //ex.message
    }
}