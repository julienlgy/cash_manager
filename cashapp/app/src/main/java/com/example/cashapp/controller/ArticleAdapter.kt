package com.example.cashapp.controller

import androidx.recyclerview.widget.RecyclerView
import com.example.cashapp.model.Article
import com.example.cashapp.R
import android.content.Context
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ImageButton
import android.widget.PopupMenu


class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ViewHolder>{
    private val mContext : Context
    private val articleList : MutableList<Article>
    constructor(mContext : Context, articleList : MutableList<Article>) {
        this.mContext = mContext
        this.articleList = articleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.article, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList.get(position)
        //holder.id.text = article.id
        holder.nom.text = article.nom
        holder.price.text = article.prix.toString()
        article.DownloadImageTask(holder.img).execute(article.img)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val id: TextView
        val nom: TextView
        val img : ImageView
        //var thumbnail: ImageView
        val price: TextView
        val editButton : ImageButton

        init {
            //id = view.findViewById(R.id.art_id)
            nom = view.findViewById(R.id.art_nom)
            img = view.findViewById(R.id.art_img)
            //thumbnail = view.findViewById(R.id.thumbnail) as ImageView
            price = view.findViewById(R.id.art_prix)
            editButton = view.findViewById(R.id.edit)
            editButton.setOnClickListener({
                if (it is View) showMenu(it)
            })
        }

        fun showMenu(anchor: View): Boolean {
            val popup = PopupMenu(anchor.context, anchor)
            popup.getMenuInflater().inflate(R.menu.art_edit, popup.getMenu())
            popup.show()
            return true
        }
    }

    fun getLayoutManager() : LinearLayoutManager {
        return LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val lin = LinearLayoutManager(mContext)
        lin.orientation = RecyclerView.VERTICAL
        return lin
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}