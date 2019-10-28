package com.example.cashapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cashapp.R
import com.example.cashapp.ScannerActivity
import com.example.cashapp.controller.ArticleAdapter
import com.example.cashapp.controller.CartController
import com.example.cashapp.model.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*



class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listArticle : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val addbutton : FloatingActionButton = root.findViewById(R.id.addfloat);
        addbutton.setOnClickListener{
            val intent = Intent(context, ScannerActivity::class.java);
            startActivityForResult(intent, ScannerActivity.REQUEST_CODE);
        };
        this.listArticle = root.findViewById(R.id.list_article)
        return root
    }

    override fun onStart() {
        super.onStart()
        // toggling the correct layout.
        //getCart().add(Article("0", "test produit", "ceci est un test produit", "https://www.delcourt.fr/3073-large_default/Produit-nettoyant-vitres-DELCOURT-pro.jpg", "15.25".toFloat()))
        //getCart().add(Article("045616516", "test produit", "ceci est un test produit", "https://www.delcourt.fr/3073-large_default/Produit-nettoyant-vitres-DELCOURT-pro.jpg", "15.25".toFloat()))
        this.listArticle
        val adapter = ArticleAdapter(this.requireContext(), getCart().getAll())
        listArticle.layoutManager = adapter.getLayoutManager()
        listArticle.adapter = adapter
        //val adapter = ArrayAdapter(this.requireContext(), R.layout.article, R.id.art_id, getCart().getAll())
        //listView.adapter = adapter
        toggleLayout(getCart().size() !== 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("RESULT CALLED")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ScannerActivity.REQUEST_CODE) {
            if (resultCode == 0 && data is Intent) {
                println("RESULT OK")
                val str = data.getStringExtra("article")
                getCart().add(Article.parse(str))
                println(str)
            }
        }
    }


    fun getCart() : CartController {
        return CartController.getInstance()
    }

    fun toggleLayout(on : Boolean) {
        view_product.visibility = if (on) View.VISIBLE else View.INVISIBLE
        layout_no_products.visibility = if (!on) View.VISIBLE else View.INVISIBLE
    }
}