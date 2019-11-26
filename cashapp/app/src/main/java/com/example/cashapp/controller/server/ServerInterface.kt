package com.example.cashapp.controller.server

import com.example.cashapp.model.Server
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.ConnectException

interface ServerInterface {

    var server: Server?
    val serverListener : MutableList<ServerListener>
    val okhttp : OkHttpClient

    /**
     * Event listener
     */
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
    fun connect(address: String, port: String, password : String)
    fun disconnect()

    /**
     * Request builder + CONSTANTS
     */
    enum class PATH(val url : String) {
        AUTH ( "auth/" ),
        ARTICLE ( "article/" )
    }

    enum class METHOD(val method : String) {
        GET ("GET"),
        POST ("POST")
    }

    fun req(path : PATH, method : METHOD, args : String?, callback : (Boolean, String?) -> Unit?) {
        val str = server!!.baseurl + path.url
        run (str, method, args, callback)
    }

    fun isConnected() : Boolean = (this.server is Server && !this.server!!.token.equals("null"))

    private fun run(url : String, method: METHOD, args : String?, callback : (Boolean, String?) -> Unit?) = runBlocking {
        try {
            var request : Request = Request.Builder()
                .url(url)
                .build()
            if (method === METHOD.POST) {
                if (args != null) {
                    val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), args)
                    request = Request.Builder()
                        .url(url)
                        .post(body)
                        .build()
                }
            }
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response: Response = okhttp.newCall(request).execute()
                    withContext(Dispatchers.Main) {
                        callback(response.isSuccessful, response.body()?.string())
                    }
                } catch (e : Exception) {
                    println("Connection error : " + url)
                    withContext(Dispatchers.Main) {
                        callback(false, "{\"status\":false,\"msg\":\"Can't connect to server\"}")
                    }
                }
            }
        } catch (e : Exception) {
            println("Illegal url " + url)
        }
    }


}