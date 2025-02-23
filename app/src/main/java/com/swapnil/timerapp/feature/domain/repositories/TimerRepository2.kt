package com.swapnil.timerapp.feature.domain.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.swapnil.timerapp.feature.domain.models.TimeType
import kotlinx.coroutines.flow.Flow

interface TimerRepository2 {

    suspend fun getTime() : Long
    fun getTimeFlow() : Flow<Preferences>
    suspend fun saveTime(endTime: Long)
    suspend fun removeTime()
}