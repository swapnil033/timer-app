package com.swapnil.timerapp.feature.data.repositories

import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore
import com.swapnil.timerapp.feature.domain.models.TimeType
import com.swapnil.timerapp.feature.domain.repositories.TimerRepository
import com.swapnil.timerapp.feature.presentation.timer_page.getTwoDigits

class TimerRepositoryImpl(
    private val dataStore: TimerDataStore
): TimerRepository {
    override fun saveTime(timeType: TimeType, value: String) {
        dataStore.saveTime(timeType, value)
    }

    override fun getTime(timeType: TimeType): String {
        return dataStore.getTime(timeType).getFormattedTime()
    }

    private fun String.getFormattedTime(): String {
        if (this.isEmpty())  "0".toLong().getTwoDigits()

        return this.toLong().getTwoDigits()

    }
}