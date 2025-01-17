package com.swapnil.timerapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class TimerApp: Application() {

    companion object{
        const val TIMER_NOTIFICATION_CHANNEL_ID = "timer_notification"
        const val TIMER_NOTIFICATION_CHANNEL_NAME = "Timer Notification"
    }

    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                TIMER_NOTIFICATION_CHANNEL_ID,
                TIMER_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}