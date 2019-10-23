package com.example.cashapp.controller.server

import com.example.cashapp.controller.CartController
import com.example.cashapp.model.Server
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket


class ServerController : ServerInterface {
    companion object {

        private var instance: CartController? = null

        @Synchronized
        fun getInstance(): CartController {
            if (instance == null) {
                instance = CartController()
            }
            return instance!!
        }

    }

    override var server: Server
        get() = server
        set(value) { server = server}

    override var connected: Boolean
        get() = connected
        set(value) { connected = value}
    override val listeners: MutableList<ServerListener>
        get() = listeners

    override fun send(request: String) {
        if (!isConnected()) return
        val stream = DataOutputStream(server.socket.getOutputStream())
        stream.writeBytes(request + 'n')
    }

    override fun send(request: String, callback: (String?) -> Unit) = runBlocking {
        GlobalScope.launch(Dispatchers.IO) {
            val stream = DataOutputStream(server.socket.getOutputStream())
            val res = BufferedReader(InputStreamReader(server.socket.getInputStream()))
            stream.writeBytes(request + 'n')
            val response = res.readLine()
            withContext(Dispatchers.Main) {
                callback(response)
            }
        }
        Unit
    }

    override fun connect(address: String, port: Int, password: String?) {
        this.server = Server(Server.generateNewSessionID(), Socket(address, port), password)
        send("HELLO FROM ${server.sessionId}") {
            if (it is String && it.equals("REQUEST PASSWORD") && password is String) {
                send("PASSWORD ${password}") {
                    if (it.equals("OK")) {
                        notifyConnected()
                    } else if (it.equals("KO")) {
                        notifyDisconnected()
                    }
                }
            }
        }
    }

    override fun createRequest(command: String, args: String?): String = "COMMAND $command" + if (args is String) " WITH $args" else ""

    /**
     * Listeners management


    /*val sentence: String
    val modifiedSentence: String
    val inFromUser = BufferedReader(InputStreamReader(System.`in`))
    val clientSocket = Socket("localhost", 6789)
    val outToServer = DataOutputStream(clientSocket.getOutputStream())
    val inFromServer = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
    sentence = inFromUser.readLine()
    outToServer.writeBytes(sentence + 'n')
    modifiedSentence = inFromServer.readLine()
    println("FROM SERVER: $modifiedSentence")
    clientSocket.close()*/

    fun addListener(toAdd : CartUpdated) {
        updateListener.add(toAdd)
    }
    private val updateListener : MutableList<CartUpdated>*/
}