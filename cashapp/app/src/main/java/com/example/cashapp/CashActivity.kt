package com.example.cashapp

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.icu.text.SimpleDateFormat
import android.nfc.*
import android.nfc.tech.*
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cashapp.controller.CartController
import com.example.cashapp.controller.CartUpdated
import android.view.Menu
import java.math.BigInteger
import java.util.*
import kotlin.experimental.and

class CashActivity : AppCompatActivity(), CartUpdated {

    private lateinit var bn_first : MenuItem
    private lateinit var bn_scdn: MenuItem
    private lateinit var bn_last: MenuItem
    private lateinit var navView : BottomNavigationView
    private lateinit var navController : NavController

    /* NFC READER */
    private var mTextView: TextView? = null
    private val techList = arrayOf(arrayOf<String>(NfcA::class.java.getName(), NfcB::class.java.getName(), NfcF::class.java.getName(), NfcV::class.java.getName(), IsoDep::class.java.getName(), MifareClassic::class.java.getName(), MifareUltralight::class.java.getName(), Ndef::class.java.getName()))
    /* ***** */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)


        /* NFC READER */
        mTextView = findViewById(R.id.textView_explanation) as TextView

        mTextView!!.text = "Price to pay : 30 €"
        /* ***** */

        navView  = findViewById(R.id.nav_view)
        bn_first = navView.menu.get(0)
        bn_scdn = navView.menu.get(1)
        bn_last = navView.menu.get(2)

        navController = findNavController(R.id.nav_host_fragment)
        /*val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_payment
            )
        )*/
        //setupActionBarWithNavControllnav_viewer(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
        getCart().addListener(this)


    }

    override fun onCartUpdate() {
        bn_last.isEnabled = if (getCart().size() == 0) false else true
        bn_scdn.isEnabled = if (getCart().size() == 0) false else true
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

    /* NFC READER */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "1")

        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val filter = IntentFilter()
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED)
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED)

        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter != null) {
            Log.d("TESTADAPTER", "YES")
        } else {
            Log.d("TESTADAPTER", "NO")
        }
        nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, arrayOf(filter), this.techList)
    }

    override fun onPause() {
        super.onPause()

        Log.d("onPause", "1")

        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("onNewIntent", "1")

        if(intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            Log.d("onNewIntent", "2")
            mTextView!!.text = "NFC Tag\n" + ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID))

            val tagN = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG)
            if (tagN != null) {
                Log.d("TAG", "Parcelable OK")
                val msgs: Array<NdefMessage>
                val empty = ByteArray(0)
                val id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
                val payload = dumpTagData(tagN).toByteArray()
                val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload)
                val msg = NdefMessage(arrayOf(record))
                msgs = arrayOf(msg)
            } else {
                Log.d("TAG", "Parcelable NULL")
            }

            val messages1 = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (messages1 != null) {
                Log.d("TAG", "Found " + messages1.size + " NDEF messages")
            } else {
                Log.d("TAG", "Not EXTRA NDEF MESSAGES")
            }

            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                Log.d("onNewIntent", "NfcAdapter.EXTRA_TAG")

                val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                if (messages != null) {
                    Log.d("TAG", "Found " + messages.size + " NDEF messages")
                }
            } else {
                Log.d("TAG", "Write to an unformatted tag not implemented")
            }
        }
    }

    private fun dumpTagData(p: Parcelable): String {
        val sb = StringBuilder()
        val tag = p as Tag
        val id = tag.id
        //sb.append("Tag ID (hex): ").append(getHex(id)).append("\n")
        sb.append("ID (dec): ").append(getDec(id)).append("\n")
        //sb.append("ID (reversed): ").append(getReversed(id)).append("\n")
        sb.append("Payment Done \n")

        //val prefix = "android.nfc.tech."
       /* sb.append("Technologies: ")
        for (tech in tag.techList) {
            sb.append(tech.substring(prefix.length))
            sb.append(", ")
        }*/
        sb.delete(sb.length - 2, sb.length)
        for (tech in tag.techList) {
            if (tech == MifareClassic::class.java.getName()) {
                sb.append('\n')
                val mifareTag = MifareClassic.get(tag)
                var type = "Unknown"
                when (mifareTag.type) {
                    MifareClassic.TYPE_CLASSIC -> type = "Classic"
                    MifareClassic.TYPE_PLUS -> type = "Plus"
                    MifareClassic.TYPE_PRO -> type = "Pro"
                }
                sb.append("Mifare Classic type: ")
                sb.append(type)
                sb.append('\n')

                sb.append("Mifare size: ")
                sb.append(mifareTag.size.toString() + " bytes")
                sb.append('\n')

                sb.append("Mifare sectors: ")
                sb.append(mifareTag.sectorCount)
                sb.append('\n')

                sb.append("Mifare blocks: ")
                sb.append(mifareTag.blockCount)
            }

            if (tech == MifareUltralight::class.java.getName()) {
                sb.append('\n')
                val mifareUlTag = MifareUltralight.get(tag)
                var type = "Unknown"
                when (mifareUlTag.type) {
                    MifareUltralight.TYPE_ULTRALIGHT -> type = "Ultralight"
                    MifareUltralight.TYPE_ULTRALIGHT_C -> type = "Ultralight C"
                }
                sb.append("Mifare Ultralight type: ")
                sb.append(type)
            }
        }
        Log.d("Datos: ", sb.toString())

        val TIME_FORMAT = SimpleDateFormat.getDateTimeInstance()
        val now = Date()

        mTextView!!.text = TIME_FORMAT.format(now) + '\n'.toString() + sb.toString()
        return sb.toString()
    }

    private fun getHex(bytes: ByteArray): String {
        val sb = java.lang.StringBuilder()
        for (i in bytes.indices.reversed()) {
            val b = bytes[i] and 0xff.toByte()
            if (b < 0x10)
                sb.append('0')
            sb.append(Integer.toHexString(b.toInt()))
            if (i > 0) {
                sb.append(" ")
            }
        }
        return sb.toString()
    }

    private fun getDec(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices) {
            val value = bytes[i] and 0xffL.toByte()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    private fun getReversed(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices.reversed()) {
            val value = bytes[i] and 0xffL.toByte()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    private fun ByteArrayToHexString(inarray: ByteArray): String {
        Log.d("ByteArrayToHexString", inarray.toString())

        var i:Int
        var j: Int
        var `in`: Int
        val hex = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        var out = ""

        j = 0
        while (j < inarray.size) {
            `in` = inarray[j].toInt() and 0xff
            i = `in` shr 4 and 0x0f
            out += hex[i]
            i = `in` and 0x0f
            out += hex[i]
            ++j
        }
        Log.d("ByteArrayToHexString", String.format("%0" + inarray.size * 2 + "X", BigInteger(1, inarray)))
        return out
    }
    /* ***** */
}