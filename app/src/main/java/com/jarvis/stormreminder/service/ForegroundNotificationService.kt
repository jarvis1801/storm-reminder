package com.jarvis.stormreminder.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jarvis.stormreminder.R
import com.jarvis.stormreminder.network.MasterApi


class ForegroundNotificationService : Service() {
    companion object {
        private const val CHANNEL_ID = "StormReminderChannel"
        private const val loopTime = 3600000L
    }

    private var handler: Handler = Handler()
    private var runnable: Runnable? = null
    private var isRunnableStarted = false

    private var title = "Initing..."
    private var content = "Initing..."

    private var apiService = MasterApi.create()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("chris", "onstart")
        createNotificationChannel()

        if (!isRunnableStarted) {
            isRunnableStarted = true
            initHandler()
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun initHandler() {
        runnable = Runnable {

            apiService.getDeatil(
                dataType = "fnd",
                language = "tc",
                onComplete = {
                    updateNotification()
                },
                onNext = { data ->
                    title =
                        "(${data.weatherForecast[0].week}) ${data.weatherForecast[0].forecastWind}"
                    content = data.generalSituation
                },
                onError = {
                    updateNotification()
                }
            )

            handler.postDelayed(runnable!!, loopTime)
        }


        handler.post(runnable!!)
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "StormReminder",
            NotificationManager.IMPORTANCE_LOW
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(serviceChannel)
    }

    private fun updateNotification() {
        val ns: String = Context.NOTIFICATION_SERVICE
        val nMgr = getSystemService(ns) as NotificationManager
        nMgr.cancel(1)

        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(content)
        bigText.setBigContentTitle(title)

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.baseline_toys_white_18)
            .setStyle(bigText)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()

        apiService.dispose()
    }
}