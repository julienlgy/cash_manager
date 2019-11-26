package com.example.cashapp.controller.server

import com.example.cashapp.model.Article
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
        val infos : MutableMap<String, Any> = mutableMapOf()
        infos["password"] = password
        infos["deviceid"] = 1
        val reqPath = this.server!!.baseurl + ServerInterface.PATH.AUTH.url
        req(reqPath, ServerInterface.METHOD.POST, gson.toJson(infos),
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
                        this.callonServerResponse(json.asJsonObject.get("message").asString)
                    }
                } catch (e: Exception) {
                    println(e)
                    println(str)
                    this.callonServerResponse("Can't ear what server say.")
                }
            }
        )
    }

    fun getArticle(articleId : String, callback : (Article) -> Unit?) {
        if (isConnected()) {
            val reqStr = this.server!!.baseurl + ServerInterface.PATH.ARTICLE.url.replace("{id}", articleId)
            req(reqStr, ServerInterface.METHOD.GET, null,
                fun(ok : Boolean, str: String?) {
                    if (ok) {
                        val json = JsonParser().parse(str)
                        if (json.asJsonObject.get("status").asBoolean) {
                            val serializedArticle = json.asJsonObject.get("article").asString
                            callback(Article.parse(serializedArticle))
                        }
                    }
                    callback(Article("0","Produit introuvable", "Ce produit n'est pas répertorié dans la base de donnée", "", "0.00".toFloat()))
                }
            )
        } else {
            this.callonServerDisonnected()
        }
    }

    fun postArticle(article : Article) {
        if (isConnected()) {
            val reqStr = this.server!!.baseurl + ServerInterface.PATH.ARTICLE_A.url
            val infos : MutableMap<String, String> = mutableMapOf()
            infos["article"] = Article.stringify(article)
            req(reqStr, ServerInterface.METHOD.POST, gson.toJson(infos),
                fun(ok:Boolean, str:String?) {
                    val json = JsonParser().parse(str)
                    if (ok)
                        if (json.asJsonObject.get("status").asBoolean) {
                            println("POST ARTICLE OK")
                        } else {
                            println("¨POST ARTICLE KO")
                        }
                    else
                        println("POST ARTICLE An error occured")
                }
            )
        } else {
            callonServerDisonnected()
        }
    }

    override fun disconnect() {
        if (this.server is Server) {
            this.server = null;
        }
        this.callonServerDisonnected()
    }
}