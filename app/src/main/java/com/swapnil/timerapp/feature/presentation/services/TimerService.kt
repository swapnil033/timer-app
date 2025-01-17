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
import com.swapnil.timerapp.TimerApp
import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore
import com.swapnil.timerapp.feature.data.repositories.TimerRepositoryImpl
import com.swapnil.timerapp.feature.domain.models.TimeType
import com.swapnil.timerapp.feature.domain.repositories.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerService: Service() {

    private val notificationId = 1

    private lateinit var notificationManager : NotificationManager

    private lateinit var timerRepository: TimerRepository

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val dataStore = TimerDataStore(this)
        timerRepository = TimerRepositoryImpl(dataStore)
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
            .Builder(this, TimerApp.TIMER_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Timer Has Started")
            .setContentText(content)
    }

    private fun updateNotification(content: String){

        if(content == "00 : 00 : 00") {
            notificationManager.cancel(notificationId)
            stop()
            return
        }

        val notification = notification(content).build()
        notification.flags = Notification.FLAG_ONGOING_EVENT
        notificationManager.notify(notificationId, notification)

    }

    private fun stop(){
        serviceScope.cancel()
        stopSelf()
    }

    private fun start() {

        val notification = notification("00:00:00").build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(notificationId, notification)

            serviceScope.launch(Dispatchers.Main) {
                // Perform background work
                while (true){

                    val hour = timerRepository.getTime(TimeType.HOUR)
                    val minute = timerRepository.getTime(TimeType.MINUTE)
                    val second = timerRepository.getTime(TimeType.SECOND)

                    val time = "$hour : $minute : $second"
                    Log.d("SERVICE-TAG", "start: $time")

                    updateNotification(time)

                    delay(1000) // Wait for 1 second
                }
            }
        }

    }

    enum class Action{
        START, STOP
    }
}