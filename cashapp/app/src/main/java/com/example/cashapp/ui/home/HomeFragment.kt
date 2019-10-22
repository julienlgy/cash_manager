package com.example.cashapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashapp.R
import com.example.cashapp.ScannerActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listView : ListView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val layout_no_product : ConstraintLayout = root.findViewById(R.id.layout_no_products)
        val layout_product: ConstraintLayout = root.findViewById(R.id.layout_product)
        layout_product.visibility = View.INVISIBLE
        layout_no_product.visibility = View.VISIBLE
        val addbutton : FloatingActionButton = root.findViewById(R.id.addfloat);
        addbutton.setOnClickListener{
            val intent = Intent(context, ScannerActivity::class.java);
            startActivity(intent);
        };

        /*listView = root.findViewById<ListView>(R.id.recipe_list_view)
        val exampleList = arrayOfNulls<String>(5)
        exampleList[0] = "salut"
        exampleList[1] = "cava ?"
        exampleList[2] = "et toi ?"
        exampleList[3] ="Moi super"
        exampleList[4] = "Cool."

        val adapter = ArrayAdapter(this.requireContext(), R.layout.article, exampleList)
        listView.adapter = adapter*/

        return root
    }
}