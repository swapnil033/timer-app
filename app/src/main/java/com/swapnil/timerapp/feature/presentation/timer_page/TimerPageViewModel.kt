package com.swapnil.timerapp.feature.presentation.timer_page

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnil.timerapp.feature.data.dataSource.PreferencesKeys
import com.swapnil.timerapp.feature.data.dataSource.dataStore
import com.swapnil.timerapp.feature.domain.models.TimeType
import com.swapnil.timerapp.feature.domain.models.TimerData
import com.swapnil.timerapp.feature.domain.models.getTwoDigits
import com.swapnil.timerapp.feature.domain.models.toTimerData
import com.swapnil.timerapp.feature.presentation.services.TimerService
import com.swapnil.timerapp.feature.presentation.services.toTime
import com.swapnil.timerapp.feature.presentation.util.AlarmToneUtil
import com.swapnil.timerapp.feature.presentation.util.TimerUtil
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerPageViewModel(
    private val context: Application,
) : ViewModel() {

    private var _state = MutableStateFlow(TimerPageState())
    val state = _state.asStateFlow()


    //private var timer = TimerUtil()
    private val ringtoneUtil = AlarmToneUtil(context)

    private var _event = Channel<String>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            context.dataStore.data.collect { preferences ->
                preferences[PreferencesKeys.END_TIME]?.let { endTime ->
                    if (endTime > System.currentTimeMillis()) {
                        startTimerUpdates(endTime)
                        startServiceIfNeeded()
                    }
                }
            }
        }
    }

    private fun startTimerUpdates(endTime: Long) {
        viewModelScope.launch {
            while (System.currentTimeMillis() < endTime) {
                val timer = endTime - System.currentTimeMillis()
                val formattedTime = timer.toTimerData().formattedTime

                _state.update {
                    it.copy(
                        isTimerRunning = true,
                        timer = formattedTime,
                    )
                }

                delay(1000)
            }
            _state.update {
                it.copy(
                    isTimerRunning = false,
                    timer = "",
                )
            }
            context.dataStore.edit { it.remove(PreferencesKeys.END_TIME) }
        }
    }

    private fun startServiceIfNeeded() {
        if (!isServiceRunning()) {
            context.startService(Intent(context, TimerService::class.java))
        }
    }

    private fun isServiceRunning(): Boolean {
        return (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == TimerService::class.java.name }
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

            is TimerPageAction.OnStart -> {

                val timeData = TimerData(
                    hour = getTime(TimeType.HOUR),
                    minute = getTime(TimeType.MINUTE),
                    second = getTime(TimeType.SECOND),
                )

                val endTime = System.currentTimeMillis() + timeData.toMilliseconds()

                viewModelScope.launch{
                    context.dataStore.edit { it[PreferencesKeys.END_TIME] = endTime }
                }

                _state.update {
                    it.copy(
                        isTimerRunning = true,
                    )
                }

                Intent(context, TimerService::class.java).also {
                    it.action = TimerService.Action.START.toString()
                    context.startService(it)
                }
                //timerStart()
            }

            is TimerPageAction.OnStop -> {
                _state.update { it.copy(isTimerRunning = false) }
                //timer.stop()
                Intent(context, TimerService::class.java).also {
                    it.action = TimerService.Action.STOP.toString()
                    context.stopService(it)
                }
            }
        }
    }

    /*private fun timerStart() {
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

    }*/

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