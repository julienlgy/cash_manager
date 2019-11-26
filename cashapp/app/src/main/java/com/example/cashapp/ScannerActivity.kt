package com.example.cashapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.cashapp.controller.CameraController
import com.example.cashapp.controller.server.ServerController
import com.example.cashapp.model.Article
import com.example.cashapp.ui.scanner.ScannerFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * ScannerActivity # jlegay 2019
 * last modified : 22/10/2019
 */
class ScannerActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE = 1
    }

    private lateinit var okbutton : FloatingActionButton
    private lateinit var kobutton : FloatingActionButton
    private lateinit var article : Article

    private lateinit var camera : CameraController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanner_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ScannerFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart(){
        super.onStart()
        val view_finder : TextureView = findViewById(R.id.scan_preview)
        this.okbutton = findViewById(R.id.ok_button)
        this.kobutton = findViewById(R.id.ko_button)
        println("init camera")
        this.camera = CameraController(this,this, view_finder)
        println("end init")
        kobutton.setOnClickListener({
            Snackbar.make(kobutton, R.string.scan_not_valid, Snackbar.LENGTH_SHORT)
                .show();
        })
        okbutton.setOnClickListener({
            val resultIntent = Intent()
            resultIntent.putExtra("article", Article.stringify(article));
            setResult(0, resultIntent)
            finish()
        })

        camera.start()
        camera.result {
            if (it is String) {
                val product_id : TextView = findViewById(R.id.product_id)
                val product_desc : TextView = findViewById(R.id.product_desc)
                product_id.text = it;
                ServerController.getInstance().getArticle(it, fun(article: Article) {
                    runOnUiThread{
                        product_desc.text = article.description
                    }
                })
                setValid(true)
            }
        }
    }

    fun setValid(ok : Boolean) {
        okbutton.visibility = if (ok) View.VISIBLE else View.INVISIBLE
        kobutton.visibility = if (!ok) View.VISIBLE else View.INVISIBLE
    }


}
