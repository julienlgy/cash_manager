package com.example.cashapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashapp.R
import com.example.cashapp.ScannerActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(this, Observer {
//            textView.text = it
  //      })
        val addbutton : FloatingActionButton = root.findViewById(R.id.addfloat);
        addbutton.setOnClickListener{
            val intent = Intent(context, ScannerActivity::class.java);
            startActivity(intent);
        };
        return root
    }
}