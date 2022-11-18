package com.example.msystems

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("toastMessage")
        COUNT++
        Toast.makeText(context, COUNT.toString(), Toast.LENGTH_SHORT).show()
    }
}