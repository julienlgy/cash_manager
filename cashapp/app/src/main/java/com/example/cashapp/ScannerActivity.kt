package com.example.cashapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import com.example.cashapp.controller.CameraController
import com.example.cashapp.ui.scanner.ScannerFragment

class ScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanner_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ScannerFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        val view_finder : TextureView = findViewById(R.id.scan_preview)
        CameraController(this,this, view_finder).start()
    }

}
