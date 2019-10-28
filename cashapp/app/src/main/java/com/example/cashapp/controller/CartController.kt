package com.example.cashapp.controller

import com.example.cashapp.model.Article

/**
 * CartController
 * Jean Bosc x Julien Legay
 * EPITECH 2019
 */
class CartController () {

    // STATIC METHODS
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

    // PRIVATE VALUES
    private val mArticles: MutableList<Article> = mutableListOf()
    private val updateListener : MutableList<CartUpdated>
    private var isOpened : Boolean = true

    init {
        updateListener = mutableListOf()
    }

    fun add(article: Article) {
        mArticles.add(article)
        notifyAdd()
    }

    fun remove(mId: String): Boolean {
        for (mArticle in mArticles)
            if (mArticle.id == mId) {
                mArticles.remove(mArticle)
                notifyAdd()
                return true
            }
        return false
    }

    fun get(mId: String): Article? {
        for (mArticle in mArticles)
            if (mArticle.id === mId)
                return mArticle
        return null
    }

    fun getAll() : MutableList<Article> { return mArticles }
    fun size() : Int {  return mArticles.size }

    fun getPrice() : Float {
        //
        return "0.0".toFloat()
    }

    fun close() : Boolean {
        if (!isOpened) return false
        isOpened = false
        notifyClose()
        return true
    }

    fun open() : Boolean {
        if (isOpened) return false
        isOpened = true
        notifyOpen()
        return true
    }
    fun flush() : Boolean {
        mArticles.removeAll(mArticles)
        return open()
    }

    /**
     * Listeners management
     */
    fun addListener(toAdd : CartUpdated) {
        updateListener.add(toAdd)
    }
    fun notifyAdd() {
        for(listen in updateListener) {
            listen.onCartUpdate()
        }
    }
    fun notifyClose() {
        for(listen in updateListener) {
            listen.onCartValidation()
        }
    }
    fun notifyOpen() {
        for(listen in updateListener) {
            listen.onCartReopen()
        }
    }
}

/**
 * CartUpdated # Jlegay 2019
 */
interface CartUpdated {
    fun onCartUpdate()
    fun onCartValidation()
    fun onCartReopen()
}