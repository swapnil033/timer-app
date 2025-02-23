package com.swapnil.timerapp.feature.presentation.util.notificationUtil

import android.app.NotificationManager

data class NotificationChannelData(
    val id: String,
    val name: String,
    val description: String,
    val importance: Int
){
    companion object {

        val timerChannel = NotificationChannelData(
            id = "timer_channel",
            name = "Timer Channel",
            description = "Used for the increment counter notifications",
            importance = NotificationManager.IMPORTANCE_LOW,
        )

        val alarmChannel = NotificationChannelData(
            id = "alarm_channel",
            name = "Alarm Channel",
            description = "Used for the starting alarm when timer finishes",
            importance = NotificationManager.IMPORTANCE_MAX,
        )
    }
}