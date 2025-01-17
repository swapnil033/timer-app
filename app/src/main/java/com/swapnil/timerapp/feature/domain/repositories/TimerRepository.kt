package com.swapnil.timerapp.feature.domain.repositories

import com.swapnil.timerapp.feature.domain.models.TimeType

interface TimerRepository {

    fun saveTime(timeType: TimeType, value: String)
    fun getTime(timeType: TimeType): String
}