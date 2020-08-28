package com.aayushf.watchdog

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_live_feed.*
import kotlinx.android.synthetic.main.content_live_feed.*
import android.webkit.WebViewClient
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.content_drive.*

class LiveFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_feed)
        setSupportActionBar(toolbar)

        val db = FirebaseDatabase.getInstance()
        val live_feed_ref = db.getReference("live_feed")
        live_feed_ref.setValue("1")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Thread(Runnable {
            Thread.sleep(15000)
            runOnUiThread {
                progress_live_feed.visibility = View.INVISIBLE
                live_web_view.setWebViewClient(WebViewClient())
                live_web_view.loadUrl("http://192.168.0.120:8000/")
                Log.d("AAYA", "AAYA")
            }
        }).start()

    }

}
