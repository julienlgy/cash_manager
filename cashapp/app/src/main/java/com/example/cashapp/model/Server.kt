package com.example.cashapp.model

import java.net.Socket
import java.sql.Timestamp
import java.time.Instant
import java.util.*

/**
 * Server class
 * jlegay 2019
 * #epitech
 */
class Server(var token : String, var baseurl : String) {
    companion object {
        const val api_version = "v1"
        const val BASE_URL = "/api/"+api_version+"/"
        fun buildUrl(adress: String, port : String) : String {
            return "https://"+adress+":"+port+BASE_URL
        }
    }
}