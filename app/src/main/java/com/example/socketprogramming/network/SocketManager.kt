package com.example.socketprogramming.network


import com.example.socketprogramming.data.response.SocketAuctionResponse
import com.example.socketprogramming.data.response.SocketBaseResponse
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import timber.log.Timber

class SocketManager {
    private val gson = Gson()
    private val socket: Socket

    init {
        socket = IO.socket(BASE_URL)
        socket.connect()
    }

    fun joinRoom(goodsId: Int){
        connectSocket()
        socket.emit("enter", goodsId)

    }
    fun getAuctionPrice(goodsId: Int,  onData : (Array<String>)-> Unit) {
        connectSocket()
        socket.on("bid", Emitter.Listener {
            onData(arrayOf(it[0].toString()))
        })

    }

    fun leaveRoom(goodsId: Int) {
        connectSocket()
        socket.emit("left", gson.toJson(goodsId))
    }

    fun disconnectSocket() {
        socket.disconnect()
    }

    private fun connectSocket() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    private companion object {
        const val BASE_URL = "http://3.37.7.7:3000"
    }
}