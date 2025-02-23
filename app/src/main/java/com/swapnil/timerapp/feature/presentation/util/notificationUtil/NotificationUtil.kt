package com.swapnil.timerapp.feature.presentation.util.notificationUtil

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.swapnil.timerapp.R

class NotificationUtil(
    private val application: Context
) {

    private var notificationManager: NotificationManager



    init {
        notificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun createNotificationChannel(channelData: NotificationChannelData){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelData.id,
                channelData.name,
                channelData.importance
            )
            channel.description = channelData.description

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun buildNotification(context: Context, data: NotificationData) : NotificationCompat.Builder{
        return NotificationCompat
            .Builder(context, data.channel.id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(data.title)
            .setContentText(data.content)
    }

}