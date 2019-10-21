package com.example.cashapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cashapp.controller.CartController
import com.example.cashapp.model.Article

class CashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val item2: MenuItem = navView.menu.get(2);
        item2.isEnabled = false;

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_payment
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // TEST - Dynamic Layout
        /*var textView = TextView(this)
        textView.setText("Article ")
        R.layout.fragment_dashboard.*/
        // TEST - MVC
        /*var mCart = CartController.getInstance()
        var myArticle = Article(0,"Pates", "Pates Italiennes", "", 2.02f)

        mCart.addArticle(myArticle)
        Log.d("REQUEST", mCart.getArticleInList(0))
        mCart.removeArticle(0)
        Log.d("REQUEST", mCart.getArticleInList(0))*/

    }
}
