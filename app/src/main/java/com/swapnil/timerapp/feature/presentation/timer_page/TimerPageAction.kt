package com.swapnil.timerapp.feature.presentation.timer_page

sealed class TimerPageAction {
    data class OnHourChange(val value: String): TimerPageAction()
    data class OnMinuteChange(val value: String): TimerPageAction()
    data class OnSecondChange(val value: String): TimerPageAction()
    data object OnStart: TimerPageAction()
    data object OnStop: TimerPageAction()
}