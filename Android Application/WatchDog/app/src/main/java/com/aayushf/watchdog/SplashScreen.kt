package com.aayushf.watchdog

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.wbm3)
        val vv = findViewById<View>(R.id.splash_video) as VideoView
        vv.setVideoURI(uri)
        vv.start()
        vv.setOnCompletionListener {
            val i = Intent(this@SplashScreen, HomeActivity::class.java)
            startActivity(i)
        }

    }
}
