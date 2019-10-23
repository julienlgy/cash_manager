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
class Server(val sessionId : String, val socket : Socket, var password : String? = null) {
    companion object {
        fun generateNewSessionID() : String {
            val date = Date()
            return Random(Timestamp(date.time).time).nextInt().toString()
        }
    }
}