package com.example.cashapp.controller.server

import com.example.cashapp.CashActivity
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
        ARTICLE ( "article/{id}" ),
        ARTICLE_A ( "article/"),
    }

    enum class METHOD(val method : String) {
        GET ("GET"),
        POST ("POST"),
        PUT ("PUT"),
        DELETE ("DELETE"),
        HEAD ("HEAD")
    }

    fun req(path : String, method : METHOD, args : String?, callback : (Boolean, String?) -> Unit?) {
        run (path, method, args, callback)
    }

    fun isConnected() : Boolean = (this.server is Server && !this.server!!.token.equals("null"))

    private fun run(url : String, method: METHOD, args : String?, callback : (Boolean, String?) -> Unit?) = runBlocking {
        try {
            var requestB : Request.Builder = Request.Builder()
                .url(url)
            if (method == METHOD.POST) {
                if (args != null) {
                    val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), args)
                    requestB
                        .method("POST", body)
                }
            }
            if (server is Server && server!!.token is String)
                requestB.addHeader("Authorization", "Token "+server!!.token)
            val request : Request = requestB.build()
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    okhttp.newCall(request).enqueue( object: Callback {
                        override fun onResponse(call: Call?, response: Response) {
                            callback(response.isSuccessful, response.body()?.string())
                        }

                        override fun onFailure(call: Call?, e: IOException?) {
                            println("Request Failure.")
                        }
                    })
                } catch (e : Exception) {
                    println(request)
                    println(e)
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