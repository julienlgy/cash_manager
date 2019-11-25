package com.example.cashapp.controller.server

import com.example.cashapp.model.Server
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient


class ServerController(
    override val okhttp: OkHttpClient = OkHttpClient(),
    val gson : Gson = Gson()
) : ServerInterface{

    override var server: Server? = null

    override val serverListener: MutableList<ServerListener> = arrayListOf()

    companion object {
        private var instance: ServerController? = null

        @Synchronized
        fun getInstance(): ServerController {
            if (instance == null) {
                instance = ServerController()
            }
            return instance!!
        }
    }


    override fun connect(address: String, port: String, password : String) {
        this.server = Server("null", Server.buildUrl(address, port))
        val infos : MutableMap<String, String> = mutableMapOf()
        infos["password"] = password
        infos["deviceid"] = "notimplyet"

        req(ServerInterface.PATH.AUTH, ServerInterface.METHOD.GET, gson.toJson(infos),
            fun(status:Boolean, str : String?) {
                try {
                    val json = JsonParser().parse(str)
                    if (status) {
                        if (json.asJsonObject.get("status").asBoolean) {
                            this.server!!.token = json.asJsonObject.get("token").asString
                            this.callonServerConnected()
                        } else
                            this.callonServerResponse("Wrong password")
                    } else {
                        this.callonServerResponse(json.asJsonObject.get("msg").asString)
                    }
                } catch (e: Exception) {
                    this.callonServerResponse("Can't ear what server say.")
                }
            }
        )
    }

    override fun disconnect() {
        if (this.server is Server) {
            this.server = null;
        }
        this.callonServerDisonnected()
    }
}