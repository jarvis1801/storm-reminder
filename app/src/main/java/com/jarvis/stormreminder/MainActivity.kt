package com.jarvis.stormreminder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jarvis.stormreminder.service.ForegroundNotificationService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService()
        finish()
    }

    private fun startService() {
        val serviceIntent = Intent(this, ForegroundNotificationService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }
}