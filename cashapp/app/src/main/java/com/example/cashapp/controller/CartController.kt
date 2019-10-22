package com.example.cashapp.controller

import com.example.cashapp.model.Article

class CartController () {

    private var mArticles: MutableList<Article> = mutableListOf()

    init {}

    companion object {

        private var instance: CartController ? = null

        @Synchronized
        fun getInstance(): CartController {
            if(instance == null) {
                instance = CartController()
            }
            return instance!!
        }

    }

    fun addArticle(article: Article) {
        mArticles.add(article)
    }

    fun removeArticle(mId: Int): Boolean {
        for (mArticle in mArticles) {

            if (mArticle.id == mId) {
                mArticles.remove(mArticle)
                return true
            }
        }

        return false
    }

    fun getArticleInList(mId: Int): String {
        var mNom = ""

        for (mArticle in mArticles) {

            if (mArticle.id == mId) {
                mNom = mArticles[mId].nom
            }
        }

        return mNom
    }
}