package com.swapnil.timerapp.feature.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore2
import com.swapnil.timerapp.feature.domain.repositories.TimerRepository2
import kotlinx.coroutines.flow.Flow

class TimerRepository2Impl(
    private val dataStore: TimerDataStore2
): TimerRepository2 {
    override suspend fun getTime(): Long {
        return dataStore.getTime()
    }

    override fun getTimeFlow(): Flow<Preferences> {
        return dataStore.getTimeFlow()
    }

    override suspend fun saveTime(endTime: Long) {
        dataStore.saveTime(endTime)
    }

    override suspend fun removeTime() {
        dataStore.removeTime()
    }


}