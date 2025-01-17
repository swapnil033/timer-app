package com.swapnil.timerapp.feature.presentation.timer_page

import android.content.Context
import androidx.lifecycle.ViewModel
import com.swapnil.timerapp.feature.domain.models.TimeType
import com.swapnil.timerapp.feature.domain.repositories.TimerRepository
import com.swapnil.timerapp.feature.presentation.util.AlarmToneUtil
import com.swapnil.timerapp.feature.presentation.util.TimerUtil
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class TimerPageViewModel(
    context: Context,
    private val repository: TimerRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(TimerPageState())
    val state = _state.asStateFlow()


    private var timer = TimerUtil()
    private val ringtoneUtil = AlarmToneUtil(context)

    private var _event = Channel<String>()
    val event = _event.receiveAsFlow()

    private val _notificationContent = MutableStateFlow("Initial Notification Content")
    val notificationContent = _notificationContent.asStateFlow()

    fun updateNotificationContent(content: String) {
        _notificationContent.value = content
    }

    fun onAction(action: TimerPageAction) {
        when (action) {
            is TimerPageAction.OnHourChange -> {
                _state.update {
                    it.copy(
                        hour = it.hour.copy(value = action.value)
                    )
                }
            }

            is TimerPageAction.OnMinuteChange -> {
                _state.update {
                    it.copy(
                        minute = it.minute.copy(value = action.value)
                    )
                }
            }

            is TimerPageAction.OnSecondChange -> {
                _state.update {
                    it.copy(
                        second = it.second.copy(value = action.value)
                    )
                }
            }

            TimerPageAction.OnStart -> {
                val hour = getTime(TimeType.HOUR)
                val minute = getTime(TimeType.MINUTE)
                val second = getTime(TimeType.SECOND)

                repository.saveTime(TimeType.HOUR, hour)
                repository.saveTime(TimeType.MINUTE, minute)
                repository.saveTime(TimeType.SECOND, second)

                _state.update {
                    it.copy(
                        isTimerRunning = true,
                        timer = "$hour : $minute : $second",
                    )
                }

                timerStart()
            }

            TimerPageAction.OnStop -> {
                _state.update { it.copy(isTimerRunning = false) }
                timer.stop()
            }
        }
    }

    private fun timerStart() {
        val hour = getTime(TimeType.HOUR).toLong() * 60 * 60 * 1000
        val minute = getTime(TimeType.MINUTE).toLong() * 60 * 1000
        val second = getTime(TimeType.SECOND).toLong() * 1000

        val duration = hour + minute + second

        timer.start(
            duration = duration,
            onTick = { timeData ->
                repository.saveTime(TimeType.HOUR, timeData.hour)
                repository.saveTime(TimeType.MINUTE, timeData.minute)
                repository.saveTime(TimeType.SECOND, timeData.second)
                _state.update { it.copy(timer = timeData.time) }
            },
            onFinish = {
                _state.update { it.copy(isTimerRunning = false) }
                ringtoneUtil.playAlarmTone()
            },
        )

    }

    private fun getTime(timerType: TimeType): String {
        var time = when (timerType) {
            TimeType.HOUR -> state.value.hour.value
            TimeType.MINUTE -> state.value.minute.value
            TimeType.SECOND -> state.value.second.value
        }

        if (time.isEmpty()) time = "0"

        return time.toLong().getTwoDigits()

    }


}

fun Long.getTwoDigits(): String {
    return String.format("%02d", this)
}