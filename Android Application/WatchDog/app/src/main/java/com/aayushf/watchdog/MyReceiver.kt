package com.aayushf.watchdog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, WatchDogBackground::class.java)
        context.startService(i)
        Toast.makeText(context, "BroADcAST rECIEVED", Toast.LENGTH_LONG).show()
    }
}

