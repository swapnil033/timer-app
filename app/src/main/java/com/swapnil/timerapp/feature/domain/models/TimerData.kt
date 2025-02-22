package com.swapnil.timerapp.feature.domain.models

data class TimerData(
    val hour: String = "00",
    val minute: String = "00",
    val second: String = "00",
){
    val time = "$hour:$minute:$second"

    val formattedTime = "$hour : $minute : $second"

    fun toMilliseconds(): Long {
        val hourInMilli = hour.toLong() * 60 * 60 * 1000
        val minuteInMilli = minute.toLong() * 60 * 1000
        val secondInMilli = second.toLong() * 1000

        return hourInMilli + minuteInMilli + secondInMilli
    }

}

fun Long.toTimerData() : TimerData{

    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val remainingSeconds = totalSeconds % 3600
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60

    return TimerData(
        hour = hours.getTwoDigits(),
        minute = minutes.getTwoDigits(),
        second = seconds.getTwoDigits()
    )
}

fun Long.getTwoDigits(): String {
    return String.format("%02d", this)
}
