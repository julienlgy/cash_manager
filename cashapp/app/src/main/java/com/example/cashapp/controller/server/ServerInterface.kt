package com.example.cashapp.controller.server

import com.example.cashapp.model.Server
import kotlinx.coroutines.runBlocking

interface ServerInterface {
    var server : Server
    var connected : Boolean
    val listeners :MutableList<ServerListener>
    /**
     * Connection handler methods
     */
    fun connect(address : String, port : Int, password : String?)
    fun isConnected() : Boolean = connected
    fun getConnection() : Server? = server
    //fun launchPingSequence()

    /**
     * createRequest
     * @param command : The command need to be formated
     * @param args : Argument or not, string
     * @return formatted request.
     */
    fun createRequest(command : String, args : String?) : String
    // Send with callback
    fun send(request : String, callback : (String?) -> Unit)
    // Send without callback, don't care about the return statement.
    fun send(request : String)

    /**
     * Listeners basic methods
     */
    fun listenTo(listener : ServerListener) = listeners.add(listener)
    fun unListenTo(listener : ServerListener) = listeners.remove(listener)
    fun notifyConnected() { for(listener in listeners) listener.onServerConnected() }
    fun notifyDisconnected() {for(listener in listeners) listener.onServerDisconnect()}
    fun notifyResponse() {for(listener in listeners) listener.onServerResponse()}
}