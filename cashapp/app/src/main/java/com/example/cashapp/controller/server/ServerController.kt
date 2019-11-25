package com.example.cashapp.controller.server

import com.example.cashapp.model.Server
import com.example.cashapp.controller.server.ServerInterface
import com.google.gson.Gson
import okhttp3.OkHttpClient


class ServerController(
    override val okhttp: OkHttpClient = OkHttpClient(),
    val gson : Gson = Gson()
) : ServerInterface{
    override fun connect(address: String, port: String) {
        this.server = Server("null", Server.buildUrl(address, port))
        req(ServerInterface.PATH.AUTH, ServerInterface.METHOD.GET, fun(status:Boolean, str : String?) {
            val json = gson.toJson(str)
            print(json)
            if (status) {
                this.callonServerConnected()
            }
        })
    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var server: Server
        get() = server
        set(value) {}

    override val serverListener: MutableList<ServerListener>
        get() = serverListener
}