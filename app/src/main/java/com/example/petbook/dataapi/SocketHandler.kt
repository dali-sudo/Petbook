package com.example.petbook.dataapi

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket:Socket
    @Synchronized
    fun setSocket() {
        try{
        mSocket = IO.socket("http://192.168.1.10:9090/")


    }catch(e:URISyntaxException){

    }
    }
    @Synchronized
    fun getSocket():Socket{
        return  mSocket
    }
}