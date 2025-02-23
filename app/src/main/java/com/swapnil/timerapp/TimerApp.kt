package com.swapnil.timerapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.swapnil.timerapp.feature.presentation.util.notificationUtil.NotificationChannelData
import com.swapnil.timerapp.feature.presentation.util.notificationUtil.NotificationUtil

class TimerApp: Application() {

    override fun onCreate() {
        super.onCreate()

        val notificationUtil = NotificationUtil(this)

        notificationUtil.createNotificationChannel(NotificationChannelData.timerChannel)
        notificationUtil.createNotificationChannel(NotificationChannelData.alarmChannel)
    }

}