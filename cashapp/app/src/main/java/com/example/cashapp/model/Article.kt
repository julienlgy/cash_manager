package com.example.cashapp.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
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

    inner class DownloadImageTask(internal var bmImage: ImageView) :
        AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            if (urls.equals("noimg")) return null
            val urldisplay = urls[0]
            var mIcon11: Bitmap? = null
            try {
                val `in` = java.net.URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
            }

            return mIcon11
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result is Bitmap)
                bmImage.setImageBitmap(result)
        }
    }
}