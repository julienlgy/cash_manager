package com.example.cashapp.controller.server

interface ServerListener {
    fun onServerConnected() = null
    fun onServerDisconnect() = null
    fun onServerResponse(args : String) = null
}