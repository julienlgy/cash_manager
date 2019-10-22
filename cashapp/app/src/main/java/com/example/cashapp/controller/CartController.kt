package com.example.cashapp.controller

import com.example.cashapp.model.Article

class CartController () {

    private val mArticles: MutableList<Article> = mutableListOf()

    init {}

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

    fun add(article: Article) {
        mArticles.add(article)
    }

    fun remove(mId: String): Boolean {
        for (mArticle in mArticles) {

            if (mArticle.id == mId) {
                mArticles.remove(mArticle)
                return true
            }
        }

        return false
    }

    fun get(mId: String): String {
        var mNom = ""

        for (mArticle in mArticles) {

            /*if (mArticle.id == mId) {
                mNom = mArticles[mId.].nom
            }*/
        }

        return mNom
    }

    fun getAll() : MutableList<Article> {
        return mArticles
    }

    fun size() : Int {
        return mArticles.size
    }
}