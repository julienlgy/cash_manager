package com.example.cashapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.cashapp.controller.server.ServerController
import com.example.cashapp.controller.server.ServerListener
import com.example.cashapp.model.Server
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class LoginActivity : AppCompatActivity(), ServerListener {

    private lateinit var errormsg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ServerController.getInstance().addListener(this)
        val connect : Button = findViewById(R.id.connect)
        this.errormsg = findViewById(R.id.loginerror)
        connect.setOnClickListener {
            val addressEdit : EditText = findViewById(R.id.address)
            val portEdit : EditText = findViewById(R.id.port)
            val passEdit : EditText = findViewById(R.id.password)
            if (addressEdit.text.length < 5 || portEdit.text.length == 0 || passEdit.text.length < 2)
                this.errormsg.text = "You missed"
            else
                ServerController.getInstance().connect(addressEdit.text.toString(), portEdit.text.toString(), passEdit.text.toString())
        }

    }

    override fun onServerConnected() {
        this.runOnUiThread{
            ServerController.getInstance().remListener(this)
            finish()
        }
    }

    override fun onServerResponse(args: String) {
        this.runOnUiThread{
            if (errormsg is TextView) {
                errormsg.text = args
            }
        }
    }

    override fun onServerDisconnect() {
        //
    }
}
