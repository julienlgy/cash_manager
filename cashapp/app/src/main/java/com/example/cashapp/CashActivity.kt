package com.example.cashapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cashapp.controller.CartController
import com.example.cashapp.controller.CartUpdated
import com.example.cashapp.model.Article

class CashActivity : AppCompatActivity(), CartUpdated {

    private lateinit var bn_first : MenuItem
    private lateinit var bn_scdn: MenuItem
    private lateinit var bn_last: MenuItem
    private lateinit var navView : BottomNavigationView
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        navView  = findViewById(R.id.nav_view)
        bn_first = navView.menu.get(0)
        bn_scdn = navView.menu.get(1)
        bn_last = navView.menu.get(2)

        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_payment
            )
        )
        //setupActionBarWithNavControllnav_viewer(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
        getCart().addListener(this)
    }

    override fun onCartUpdate() {
        bn_last.isEnabled = if (getCart().size() === 0) false else true
        bn_scdn.isEnabled = if (getCart().size() === 0) false else true
        if (getCart().size() != 0) navView.getOrCreateBadge(bn_scdn.itemId).number = getCart().size()
    }

    override fun onCartValidation() {
        bn_first.isEnabled = false
        bn_scdn.isEnabled = false
        navView.removeBadge(bn_scdn.itemId)
    }

    override fun onCartReopen() {
        bn_first.isEnabled = true
        bn_scdn.isEnabled = false
        bn_last.isEnabled = false
        navController.navigate(bn_first.itemId)
    }

    fun getCart() : CartController {
        return CartController.getInstance()
    }
}
