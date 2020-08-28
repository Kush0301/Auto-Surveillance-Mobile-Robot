package com.aayushf.watchdog

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase


class WatchDogBackground : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createchannel()
        Toast.makeText(this@WatchDogBackground, "Service has started", Toast.LENGTH_LONG).show()
        val database = FirebaseDatabase.getInstance()
        var times = 0
        val logs_ref = database.getReference("logs").child("log_images")
        logs_ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var builder = NotificationCompat.Builder(this@WatchDogBackground, "99")
                    .setSmallIcon(R.drawable.outline_notification_important_white_48)
                    .setContentTitle("INTRUDER DETECTED")
                    .setContentText("Please Check")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(
                        R.drawable.outline_notification_important_white_48,
                        "View Pictures",
                        PendingIntent.getActivity(
                            this@WatchDogBackground,
                            0,
                            Intent(this@WatchDogBackground, HomeActivity::class.java),
                            0
                        )
                    )
                times = times + 1
                if (times > 0) {
                    with(NotificationManagerCompat.from(this@WatchDogBackground)) {
                        // notificationId is a unique int for each notification that you must define
                        notify(90, builder.build())
                    }
                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun createchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alerts"
            val descriptionText = "Alert notifications, delivered when an intruder is detected"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("99", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
