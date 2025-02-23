package com.swapnil.timerapp.feature.data.dataSource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.swapnil.timerapp.feature.domain.models.TimeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object PreferencesKeys {
    val END_TIME = longPreferencesKey("end_time")
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "timer_prefs")

class TimerDataStore2(
    private val context: Context
) {

    suspend fun getTime() : Long {
        return context.dataStore.data.map { it[PreferencesKeys.END_TIME] }.first() ?: 0
    }

    fun getTimeFlow() : Flow<Preferences> {
        return context.dataStore.data
    }

    suspend fun saveTime(endTime: Long){
        context.dataStore.edit { it[PreferencesKeys.END_TIME] = endTime }
    }

    suspend fun removeTime(){
        context.dataStore.edit { it.remove(PreferencesKeys.END_TIME) }
    }

}