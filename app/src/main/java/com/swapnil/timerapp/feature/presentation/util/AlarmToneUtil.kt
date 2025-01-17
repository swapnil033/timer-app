package com.swapnil.timerapp.feature.presentation.util

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.Looper

class AlarmToneUtil(
    private val context: Context
) {

    private lateinit var ringtone: Ringtone

    private val autoStopDurationInMilli = 2000L

    fun playAlarmTone() {
        // Get the default alarm tone
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        ringtone = RingtoneManager.getRingtone(context, alarmUri)

        // Play the alarm tone
        ringtone.play()

        // Stop the alarm tone after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            stopAlarmTone()
        }, autoStopDurationInMilli)
    }


    private fun stopAlarmTone() {
        if (this::ringtone.isInitialized && ringtone.isPlaying) {
            ringtone.stop()
        }
    }

}