package com.swapnil.timerapp.feature.presentation.timer_page

data class TimerPageState(
    val hour: EditTextState = EditTextState(label = "Hour", value = ""),
    val minute: EditTextState = EditTextState(label = "Minute", value = ""),
    val second: EditTextState = EditTextState(label = "Second", value = ""),
    val timer: String = "00 : 00 : 00",
    val isTimerRunning: Boolean = false
)

data class EditTextState(
    val label : String,
    val value: String,
)
