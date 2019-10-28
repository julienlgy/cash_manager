package com.example.cashapp.controller

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class OpenFoodFactsAPIController {
    companion object {
        private val API = "https://fr.openfoodfacts.org/api/v0/product/{{id}}.json"
        private fun getUrl(id: String) : String {
            return OpenFoodFactsAPIController.API.replace("{{id}}", id)
        }
        fun factory(json : JSONArray) : Any {
            return Any()
        }
    }
    fun getArticleById(context : Context, id : String) : Any {
        val toCall = OpenFoodFactsAPIController.getUrl(id)
        val queue = Volley.newRequestQueue(context)

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, toCall,
            Response.Listener<String> { response ->
                try {
                    val parsed : JSONArray = JSONArray(response)
                    //return OpenFoodFactsAPIController.factory((parsed))
                } catch (e : Exception) {
                    println("An erro occured when parsing JSON from the API")
                }
            },
            Response.ErrorListener { println("An error occured when calling the API") })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
        return Any();
    }
}