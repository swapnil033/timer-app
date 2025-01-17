package com.swapnil.timerapp.feature.presentation.util

import android.os.CountDownTimer
import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore
import com.swapnil.timerapp.feature.domain.models.TimerData
import com.swapnil.timerapp.feature.presentation.timer_page.getTwoDigits
import kotlinx.coroutines.flow.update
import java.util.Timer
import kotlin.time.Duration

class TimerUtil {

    private var timer: CountDownTimer? = null

    fun start(
        duration: Long,
        onTick: (TimerData) -> Unit,
        onFinish: () -> Unit,
    ){
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(milliseconds: Long) {

                val hours = (milliseconds / (1000 * 60 * 60)).getTwoDigits()
                val minutes = ((milliseconds % (1000 * 60 * 60)) / (1000 * 60)).getTwoDigits()
                val seconds = ((milliseconds % (1000 * 60)) / 1000).getTwoDigits()
                val time = "$hours : $minutes : $seconds"

                val timerData = TimerData(
                    hour = hours,
                    minute = minutes,
                    second = seconds,
                    time = time,
                )

                onTick(timerData)
            }

            override fun onFinish() {
                onFinish()
            }
        }.start()
    }


    fun stop(){
        timer?.cancel()
    }
}