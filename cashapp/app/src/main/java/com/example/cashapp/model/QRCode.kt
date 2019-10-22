package com.example.cashapp.model

class QRCode {
    companion object {
        enum class TYPE {
            STRING, EMAIL, URL
        }
    }

    val type : TYPE
    val informations : String

    constructor(type : TYPE, informations : String) {
        this.type = type
        this.informations = informations
    }
}