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
    private val socket: io.socket.client.Socket = IO.socket(BASE_URL)

    init {
        socket.connect()
    }

    fun joinRoom(goodsId: Int, onData : (Array<Any>)-> Unit){
        connectSocket()
        socket.on(
            Socket.EVENT_CONNECT,
            Emitter.Listener { args: Array<Any?>? ->
                socket.emit(
                    "enter",
                    gson.toJson(goodsId)
                )
            })

        socket.on("bid", Emitter.Listener { args: Array<Any> ->
            Timber.e("$args")
            onData(args)
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