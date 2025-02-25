package com.swapnil.timerapp.feature.presentation.util.notificationUtil

data class NotificationData(
    val channel: NotificationChannelData,
    val title: String,
    val content: String
){
    companion object{
        val timerNotificationData = NotificationData(
            channel = NotificationChannelData.timerChannel,
            title = "Remaining Time",
            content = ""
        )
        val alertNotificationData = NotificationData(
            channel = NotificationChannelData.alarmChannel,
            title = "Let's Roll",
            content = "time to roll new character!"
        )
    }
}