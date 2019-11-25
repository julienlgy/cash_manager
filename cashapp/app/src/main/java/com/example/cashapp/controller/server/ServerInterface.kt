package com.example.cashapp.controller.server

import com.example.cashapp.model.Server
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.io.IOException

interface ServerInterface {

    var server: Server
    val okhttp : OkHttpClient

    /**
     * Event listener
     */
    val serverListener : MutableList<ServerListener>
    fun addListener(tclass : ServerListener) = serverListener.add(tclass)
    fun remListener(tclass : ServerListener) = serverListener.remove(tclass)
    fun callonServerResponse(args : String) {
        this.serverListener.forEach {
            it.onServerResponse(args)
        }
    }
    fun callonServerConnected() {
        this.serverListener.forEach {
            it.onServerConnected()
        }
    }
    fun callonServerDisonnected() {
        this.serverListener.forEach {
            it.onServerDisconnect()
        }
    }

    /**
     * Connect/Disconnect methods
     */
    fun connect(address: String, port: String)
    fun disconnect()

    /**
     * Request builder + CONSTANTS
     */
    enum class PATH(val url : String) {
        AUTH ( "/auth/" ),
        ARTICLE ( "/article/" )
    }

    enum class METHOD(val method : String) {
        GET ("GET"),
        POST ("POST")
    }

    fun req(path : PATH, method : METHOD, callback : (Boolean, String?) -> Unit?) {
        val str = server.baseurl + path.url
        run (str, callback)
    }

    private fun run(url : String, callback : (Boolean, String?) -> Unit?) {
        val request = Request.Builder()
            .url(url)
            .build()

        okhttp.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false, null)
            }
            override fun onResponse(call: Call, response: Response) {
                callback(true, response.body()?.string())
            }
        })
    }


}