package com.swapnil.timerapp.feature.presentation.services

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.swapnil.timerapp.R
import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore2
import com.swapnil.timerapp.feature.data.repositories.TimerRepository2Impl
import com.swapnil.timerapp.feature.domain.repositories.TimerRepository2
import com.swapnil.timerapp.feature.presentation.util.notificationUtil.NotificationChannelData
import com.swapnil.timerapp.feature.presentation.util.notificationUtil.NotificationData.Companion.timerNotificationData
import com.swapnil.timerapp.feature.presentation.util.notificationUtil.NotificationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerService: Service() {

    private val notificationId = 1

    private lateinit var notificationManager : NotificationManager

    private lateinit var timerRepository: TimerRepository2

    private lateinit var notificationUtil : NotificationUtil

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val dataStore = TimerDataStore2(this)
        timerRepository = TimerRepository2Impl(dataStore)
        notificationUtil = NotificationUtil(application)
    }


    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when(intent?.action){
            Action.START.toString() -> start()
            Action.STOP.toString() -> stop()
        }

        return super.onStartCommand(intent, flags, startId)


    }

    private fun notification(content: String) : NotificationCompat.Builder{
        return NotificationCompat
            .Builder(this, NotificationChannelData.timerChannel.id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Remaining Time")
            .setContentText(content)
    }

    private fun stop(){
        serviceScope.cancel()
        stopSelf()
    }

    private fun start() {

        val notificationData = timerNotificationData.copy(content = "00:00:00")
        val notification = notificationUtil
            .buildNotification(this, notificationData)
            .build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(notificationId, notification)

            serviceScope.launch(Dispatchers.Main) {
                // Perform background work
                while (true){

                    val endTime = timerRepository.getTime()

                    if (endTime < System.currentTimeMillis()) {
                        stopSelf()
                        break
                    }

                    val remainingTime = endTime - System.currentTimeMillis()
                    //val time = "$hour : $minute : $second"
                    val time = remainingTime.toTime()
                    Log.d("SERVICE-TAG", "start: $time")

                    updateNotification(time)

                    delay(1000) // Wait for 1 second

                }
            }
        }

    }

    private fun updateNotification(content: String){

        /*if(content == "00 : 00 : 00") {
            notificationManager.cancel(notificationId)
            stop()
            return
        }*/

        Log.d("SERVICE-TIME-TAG", "updateNotification: $content")

        val notificationData = timerNotificationData.copy(content = content)
        val notification = notificationUtil
            .buildNotification(this, notificationData)
            .build()


        notification.flags = Notification.FLAG_ONGOING_EVENT
        notificationManager.notify(notificationId, notification)

    }

    enum class Action{
        START, STOP
    }
}

fun Long.toTime(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val remainingSeconds = totalSeconds % 3600
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}