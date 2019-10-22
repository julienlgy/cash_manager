package com.example.cashapp.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import io.grpc.internal.ReadableBuffers.openStream
import java.net.URL


class Article (
    val id: String,
    val nom: String,
    val description: String,
    val img: String,
    val prix: Float) {

    companion object {
        fun stringify(article : Article) : String {
            return "${article.id}#${article.nom}#${article.description}#${article.img}#${article.prix.toString()}"
        }

        fun parse(stringified : String) : Article {
            val astr = stringified.split('#')
            return Article(
                astr[0],
                astr[1],
                astr[2],
                astr[3],
                astr[4].toFloat()
            )
        }
    }
}