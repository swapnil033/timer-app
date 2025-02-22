package com.swapnil.timerapp.feature.presentation.timer_page

import android.content.Context

sealed class TimerPageAction {
    data class OnHourChange(val value: String): TimerPageAction()
    data class OnMinuteChange(val value: String): TimerPageAction()
    data class OnSecondChange(val value: String): TimerPageAction()
    data class OnStart(val context: Context): TimerPageAction()
    data class OnStop(val context: Context): TimerPageAction()
}