package com.example.cashapp.controller.server

interface ServerListener {
    fun onServerConnected()
    fun onServerDisconnect()
    fun onServerResponse(args : String)
}