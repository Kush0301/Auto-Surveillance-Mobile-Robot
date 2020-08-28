package com.aayushf.watchdog

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebViewClient
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_drive.*
import kotlinx.android.synthetic.main.content_drive.*
import kotlinx.android.synthetic.main.content_live_feed.*
import com.google.firebase.database.DatabaseReference



class DriveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drive)
        setSupportActionBar(toolbar)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("auto")
        myRef.setValue("0")
        val live_feed_ref = database.getReference("live_feed")
        live_feed_ref.setValue("1")
        Thread(Runnable {
            Thread.sleep(15000)
            runOnUiThread {
                progress_drive.visibility = View.INVISIBLE
                live_web_view_drive_act.setWebViewClient(WebViewClient())
                live_web_view_drive_act.loadUrl("http://192.168.0.120:8000/")
                Log.d("AAYA", "AAYA")
            }
        }).start()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val db = FirebaseDatabase.getInstance()
        val directionref = db.getReference("direction")
        drive_right.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                directionref.setValue("l")
                Log.d("ACTION_DOWN", "ACTION_DOWN")
                drive_right.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorPrimaryDark)));
            }else if (event.action == MotionEvent.ACTION_UP){
                drive_right.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorAccent)));
                directionref.setValue("s")
            }
            true
        }
        drive_left.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                directionref.setValue("r")
                drive_left.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorPrimaryDark)));
            }else if (event.action == MotionEvent.ACTION_UP){
                drive_left.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorAccent)));
                directionref.setValue("s")
            }
            true
        }
        drive_fwd.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                directionref.setValue("b")
                drive_fwd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorPrimaryDark)));
            }else if (event.action == MotionEvent.ACTION_UP){
                drive_fwd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorAccent)));
                directionref.setValue("s")
            }
            true
        }
        drive_back.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                directionref.setValue("f")
                drive_back.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorPrimaryDark)));
            }else if (event.action == MotionEvent.ACTION_UP){
                drive_back.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorAccent)));
                directionref.setValue("s")
            }
            true
        }
    }

}
