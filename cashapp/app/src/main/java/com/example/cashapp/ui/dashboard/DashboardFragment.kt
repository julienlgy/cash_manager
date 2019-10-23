package com.example.cashapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cashapp.R
import com.example.cashapp.controller.ArticleAdapter
import com.example.cashapp.controller.CartController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var viewProduct : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewProduct = root.findViewById(R.id.view_product)
        return root
    }

    override fun onStart() {
        super.onStart()
        val adapter = ArticleAdapter(this.requireContext(), getCart().getAll())
        viewProduct.layoutManager = adapter.getLayoutManager()
        viewProduct.adapter = adapter
    }

    fun getCart() : CartController {
        return CartController.getInstance()
    }
}