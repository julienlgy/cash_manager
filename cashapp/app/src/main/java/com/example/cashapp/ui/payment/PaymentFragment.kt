package com.example.cashapp.ui.payment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashapp.R
import com.example.cashapp.controller.CartController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class PaymentFragment : Fragment() {

    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var root : View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentViewModel =
            ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_payment, container, false)

        getCart().close()
        return root
    }

    override fun onStart() {
        super.onStart()
        val button : Button = root.findViewById(R.id.cancel)
        Snackbar.make(button, R.string.payment_snackbar, Snackbar.LENGTH_LONG)
            .show();
        button.setOnClickListener{
            MaterialAlertDialogBuilder(context)
                .setTitle(R.string.warning)
                .setMessage(R.string.cancel_confirm)
                .setPositiveButton("Yes", {
                        dialog, wich ->
                    getCart().flush()
                })
                .show();
        }
    }

    fun getCart() : CartController {
        return CartController.getInstance()
    }
}