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
    }
}